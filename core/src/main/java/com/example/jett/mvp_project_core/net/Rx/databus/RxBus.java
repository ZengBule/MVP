package com.example.jett.mvp_project_core.net.Rx.databus;

import java.lang.reflect.Method;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * 数据总线
 */
public class RxBus {
    private Set<Object> subscribers;
    /**
     * 注册
     */
    public synchronized void register(Object subscriber){
        subscribers.add(subscriber);
    }
    /**
     * 取消注册
     */
    public synchronized void unRegister(Object subscriber){
        subscribers.remove(subscriber);
    }



    private static volatile RxBus instance;
    private RxBus(){
        //读写分离的集合
        subscribers=new CopyOnWriteArraySet<>();
    }
    public static synchronized RxBus getInstance(){
        if(instance==null){
            synchronized (RxBus.class){
                if(instance==null){
                    instance=new RxBus();
                }
            }
        }
        return instance;
    }

    /**
     * 把处理过程包装起来
     *
     */
    public void chainProcess(Function function){
        Observable.just("")
                .subscribeOn(Schedulers.io())
                .map(function)//在这里进行网络访问
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer() {
                    @Override
                    public void accept(Object data) throws Exception {
                        //data会传送到总线上
                        if(data==null){
                            return;
                        }
                        send(data);//把数据送到P层
                    }
                });

    }
    public void send(Object data){
        for(Object subscriber:subscribers){
            //扫描注解,将数据发送到注册的对象标记方法的位置
            //subscriber表示层
            callMethodByAnnotion(subscriber,data);
        }
    }

    /**
     * @param subscriber
     * @param data
     */
    private void callMethodByAnnotion(Object target, Object data) {
        //1.得到presenter中写的所有的方法
        Method[] methodArray = target.getClass().getDeclaredMethods();
        for(int i=0;i<methodArray.length;i++){
            try {
                if (methodArray[i].getAnnotation(RegisterRxBus.class) != null) {
                    //如果哪个方法上用了我们写的注解
                    //把数据传上去,再执行这个方法
                    Class paramType = methodArray[i].getParameterTypes()[0];
                    if (data.getClass().getName().equals(paramType.getName())) {
                        //执行
                        methodArray[i].invoke(target, new Object[]{data});
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }

}









