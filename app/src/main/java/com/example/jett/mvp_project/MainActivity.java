package com.example.jett.mvp_project;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import com.example.jett.mvp_project.adapter.GirlAdapter;
import com.example.jett.mvp_project.bean.Girl;
import com.example.jett.mvp_project.dagger.GirlPrecenterModule;
import com.example.jett.mvp_project.precenter.DaggerGirlComponent;
import com.example.jett.mvp_project.precenter.GirlPrecenter;
import com.example.jett.mvp_project.view.IGirlView;
import com.example.jett.mvp_project_core.net.Rx.databus.RxBus;

import java.util.List;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements IGirlView{
    ListView list;

    @Inject
    GirlPrecenter precenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list= (ListView) findViewById(R.id.listview);
//        precenter=new GirlPrecenter(this);
//        new GirlPrecenter(this).fetch();
        DaggerGirlComponent.builder().
                girlPrecenterModule(new GirlPrecenterModule(this))
                .build().inject(this);
        RxBus.getInstance().register(precenter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getInstance().unRegister(precenter);
    }

    @Override
    public void showGirls(List<Girl> girls) {
        //model层的数据在girls中返回了
        list.setAdapter(new GirlAdapter(this,girls));

    }


}
