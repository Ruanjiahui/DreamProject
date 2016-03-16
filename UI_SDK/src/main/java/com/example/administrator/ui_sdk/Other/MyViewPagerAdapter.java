package com.example.administrator.ui_sdk.Other;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/1/7.
 */
public class MyViewPagerAdapter extends FragmentStatePagerAdapter {

    private ArrayList<Fragment> list = null;
    private boolean loop = false;

    private int length = 0;

    public MyViewPagerAdapter(FragmentManager fm, ArrayList<Fragment> list, boolean loop) {
        super(fm);
        this.list = list;
        this.loop = loop;

        if (loop) {
            length = Integer.MAX_VALUE;
        } else {
            length = list.size();
        }
    }

    //获取对象的数目
    @Override
    public int getCount() {
        return list.size();
    }

//    //这个方法用于判断是否由对象生成界面
//    @Override
//    public boolean isViewFromObject(View view, Object object) {
//        return true;
//    }
//
    //ViewGroup中移出当前View
//    @Override
//    public void destroyItem(ViewGroup container, int position, Object object) {
//        super.destroyItem(container, position, object);
//    }

    @Override
    public Fragment getItem(int position) {
        if (position > list.size() - 1) {
            position = position % list.size();
        }
        return list.get(position);
    }

    //PagerAdapter适配器选择哪个对象放在当前的ViewPager中
    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        return super.instantiateItem(container, position);
    }
}
