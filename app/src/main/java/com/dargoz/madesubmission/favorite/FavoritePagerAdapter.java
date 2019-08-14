package com.dargoz.madesubmission.favorite;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.dargoz.madesubmission.favorite.movie.FavoriteMovieFragment;
import com.dargoz.madesubmission.favorite.tv.FavoriteTvFragment;

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
                return new FavoriteMovieFragment();
            case 1:
                return new FavoriteTvFragment();
            default:
                return null;
        }

    }
}
