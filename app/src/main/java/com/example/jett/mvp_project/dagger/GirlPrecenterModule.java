package com.example.jett.mvp_project.dagger;

import com.example.jett.mvp_project.MainActivity;
import com.example.jett.mvp_project.precenter.GirlPrecenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by jett on 2018/6/8.
 */
@Module
public class GirlPrecenterModule {
    MainActivity mainActivity;
    public GirlPrecenterModule(MainActivity mainActivity){
        this.mainActivity=mainActivity;
    }
    @Provides
    public GirlPrecenter provideGirlPrecenter(){
        return new GirlPrecenter(mainActivity);
    }
}
