package com.example.jett.mvp_project;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;


import com.example.jett.mvp_project.widgit.ViewPagerIndicator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by apple on 16/10/30.
 * 自定义 view pager 指示器
 */
public class ViewPagerIndicatorActivity extends AppCompatActivity {

    private String TAG="ViewPagerIndicatorActivity";
    private ViewPager mViewPager;
    private ViewPagerIndicator mViewPagerIndicator;
    private List<String> mTitles= Arrays.asList("短信1","短信1","短信1");
    private List<Fragment> mContents=new ArrayList<Fragment>();

    private FragmentPagerAdapter mAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager_indicator);
        initDatas();
        initViews();
        mViewPager.setAdapter(mAdapter);
    }
    private void initDatas(){
        for (String title:mTitles){
            VPSimpleFragment fragment=VPSimpleFragment.newInstance(title);
            mContents.add(fragment);
        }
        mAdapter=new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mContents.get(position);
            }

            @Override
            public int getCount() {
                return mContents.size();
            }
        };
    }

    private void initViews(){
        mViewPager=(ViewPager) findViewById(R.id.id_viewpager);

        mViewPagerIndicator=(ViewPagerIndicator)findViewById(R.id.id_indicator);
        mViewPagerIndicator.setVisibleTabCount(3);
        mViewPagerIndicator.setTabItemTitles(mTitles);
        mViewPagerIndicator.setViewPager(mViewPager);


    }
}
