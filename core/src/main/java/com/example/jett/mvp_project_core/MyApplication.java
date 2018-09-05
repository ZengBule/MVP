package com.example.jett.mvp_project_core;

import android.app.Application;

import com.example.jett.mvp_project_core.app.ProjectInit;

/**
 * Created by jett on 2018/6/4.
 */

public class MyApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        ProjectInit.init(this)
                .withApiHost("http://192.168.100.41:80/")
                .configure();

    }
}
