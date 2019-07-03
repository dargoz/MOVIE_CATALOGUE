package com.dargoz.madesubmission.main;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;

import com.dargoz.madesubmission.main.movies.MoviesFragment;

public class MainPagerAdapter extends FragmentStatePagerAdapter {
    private int pageCount;
    public MainPagerAdapter(FragmentManager fm, int pageCount) {
        super(fm);
        this.pageCount = pageCount;
    }

    @Override
    public int getCount() {
        return pageCount;
    }

    @Override
    public Fragment getItem(int i) {
        return new MoviesFragment();
    }
}
