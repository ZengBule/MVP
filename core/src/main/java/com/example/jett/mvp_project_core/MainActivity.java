package com.example.jett.mvp_project_core;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.jett.mvp_project_core.net.Rx.RxRestClient;

import java.util.HashMap;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    HashMap params=new HashMap();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);

    }

    public void click(View view){
        String url="/login/info";
        params.put("username","jett");
        params.put("password","123");
        RxRestClient.create()
                .url(url)
                .params(params)
                .build()
                .get()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull String s) {
                        //响应结果
                        Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

//        params.put("username","jett");
//        params.put("password","123");
//        RestClient.create()
//                .url("/login/info")
//                .params(params)
//                .success(new ISuccess() {
//                    @Override
//                    public void onSuccess(String responce) {
//                        Toast.makeText(MainActivity.this, responce.toString(), Toast.LENGTH_SHORT).show();
//                    }
//                }).build()
//                .get();
        //上传  //http://dengpaoedu.com:8080/fileuploadanddownload/uploadServlet.
                //http://192.168.100.41:80/upload
//        params.put("filename","abc.txt");
//        RestClient.create()
//                .params(params)
//                .url("fileuploadanddownload/uploadServlet")
//                .file("/sdcard/test.txt")
//                .success(new ISuccess() {
//                    @Override
//                    public void onSuccess(String responce) {
//                        Toast.makeText(MainActivity.this, responce, Toast.LENGTH_SHORT).show();
//                    }
//                }).failure(new IFailure() {
//            @Override
//            public void onFailure() {
//                Toast.makeText(MainActivity.this, "N", Toast.LENGTH_SHORT).show();
//            }
//        }).error(new IError() {
//            @Override
//            public void onError(int code, String msg) {
//                Toast.makeText(MainActivity.this, "E", Toast.LENGTH_SHORT).show();
//            }
//        })
//                .build().upload();
        //测试下载  http://dengpaoedu.com:8080/examples/test.zip
//        RestClient.create()
//                .params(params)
//                .url("/examples/test.zip")
//                .dir("/sdcard")
//                .extension("zip")
//                .success(new ISuccess() {
//                    @Override
//                    public void onSuccess(String responce) {
//                        Toast.makeText(MainActivity.this, "下载成功", Toast.LENGTH_SHORT).show();
//                    }
//                }).build().download();


    }
}
