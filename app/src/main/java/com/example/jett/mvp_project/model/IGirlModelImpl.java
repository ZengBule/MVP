package com.example.jett.mvp_project.model;

import com.example.jett.mvp_project.R;
import com.example.jett.mvp_project.bean.Girl;
import com.example.jett.mvp_project_core.net.Rx.databus.RxBus;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * Created by jett on 2018/6/4.
 */

public class IGirlModelImpl implements IGirlModel{

    @Override
    public void loadGirlData() {
        RxBus.getInstance().chainProcess(new Function() {

            @Override
            public Object apply(@NonNull Object o) throws Exception {
                //在这里面进行网络操作
                List<Girl> data = new ArrayList<Girl>();
                data.add(new Girl(R.drawable.f1, "*****", "123潮流女个性字母印花无袖露脐上衣"));
                data.add(new Girl(R.drawable.f2, "231412星", "迷依诗诺夏天新款韩版女装性感夜店欧美风字母印花牛仔露脐背心上衣"));
                data.add(new Girl(R.drawable.f3, "五颗星", "迷依诗诺春夏欧美新款性感女装单排扣牛仔背心露脐上衣"));
                data.add(new Girl(R.drawable.f4, "三颗星", "美莉丹 新款欧美单排扣牛仔背心露脐上衣"));
                data.add(new Girl(R.drawable.f5, "五颗星", "夏季新款韩版时尚个性字母无袖露脐上衣"));
                data.add(new Girl(R.drawable.f6, "三颗星", "新款欧美单排扣牛仔背心露脐上衣"));
                data.add(new Girl(R.drawable.f7, "四颗星", "夏装新款下摆波浪边挂脖露肩"));
                data.add(new Girl(R.drawable.f8, "五颗星", "夏天新款欧美时尚潮流休闲百"));
                data.add(new Girl(R.drawable.f9, "四颗星", "迷依诗诺夏季新款小香风甜美性感夜"));
                data.add(new Girl(R.drawable.f10, "三颗星", "安巴克夏季新款韩版时尚套"));

                return data;
            }
        });
    }
}









