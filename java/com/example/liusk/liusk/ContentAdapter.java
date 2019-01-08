package com.example.liusk.liusk;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class ContentAdapter extends PagerAdapter{

    private List<View> views;

    public ContentAdapter(List<View> views){
        this.views = views;
    }

    @Override
    public int getCount() {
        return views.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    public Object instantiateItem(ViewGroup container, int position){
        View view = views.get(position);
        container.addView(view);
        return view;
    }

    public void destroyItem(ViewGroup container, int position, Object object){
        container.removeView(views.get(position));
    }
}
