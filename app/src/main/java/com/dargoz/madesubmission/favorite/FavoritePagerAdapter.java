package com.dargoz.madesubmission.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.dargoz.madesubmission.favorite.FavoriteFragment;
import com.dargoz.madesubmission.main.tvshow.TvShowFragment;

public class FavoritePagerAdapter extends FragmentStatePagerAdapter {
    private final int pageCount;
    public FavoritePagerAdapter(FragmentManager fm, int pageCount) {
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
                return new FavoriteFragment();
            case 1:
                return new TvShowFragment();
            default:
                return null;
        }

    }
}
