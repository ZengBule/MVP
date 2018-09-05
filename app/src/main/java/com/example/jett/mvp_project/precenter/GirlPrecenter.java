package com.example.jett.mvp_project.precenter;

import com.example.jett.mvp_project.bean.Girl;
import com.example.jett.mvp_project.model.IGirlModel;
import com.example.jett.mvp_project.model.IGirlModelImpl;
import com.example.jett.mvp_project.view.IGirlView;
import com.example.jett.mvp_project_core.net.Rx.databus.RegisterRxBus;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * 所有的业务逻辑都在表示层完成
 */

public class GirlPrecenter<T extends IGirlView> {
    //1.view层的引用
    WeakReference<T> mView;
    //2.model层的引用
    IGirlModel iGirlModel=new IGirlModelImpl();

    public GirlPrecenter(T view){
        this.mView=new WeakReference<T>(view);
        iGirlModel.loadGirlData();
    }
    @RegisterRxBus()
    public void getShowGirlsData(ArrayList<Girl> list){

        mView.get().showGirls(list);
    }



//    //3.执行UI逻辑操作
//    public void fetch(){
//        if(mView.get()!=null && iGirlModel!=null){
//            iGirlModel.loadGirl(new IGirlModel.GirlOnLoadListener() {
//                @Override
//                public void onComplete(List<Girl> list) {
//                    mView.get().showGirls(list);
//                }
//            });
//        }
//    }
}








