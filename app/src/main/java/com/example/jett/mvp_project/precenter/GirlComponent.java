package com.example.jett.mvp_project.precenter;

import com.example.jett.mvp_project.MainActivity;
import com.example.jett.mvp_project.dagger.GirlPrecenterModule;

import dagger.Component;

/**
 * Created by jett on 2018/6/8.
 */
@Component(modules = GirlPrecenterModule.class)
public interface GirlComponent {
    void inject(MainActivity mainActivity);
}
