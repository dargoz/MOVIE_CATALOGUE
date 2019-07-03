package com.dargoz.madesubmission.main;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;

import com.dargoz.madesubmission.main.movies.MoviesFragment;
import com.dargoz.madesubmission.main.tvshow.TvShowFragment;

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
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new MoviesFragment();
            case 1:
                return new TvShowFragment();
                default:
                    return null;
        }

    }
}
