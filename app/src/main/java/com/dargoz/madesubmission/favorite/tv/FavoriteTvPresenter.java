package com.dargoz.madesubmission.favorite.tv;

import android.content.Context;
import android.content.Intent;

import com.dargoz.madesubmission.detailmovielist.DetailMovieActivity;
import com.dargoz.madesubmission.main.tvshow.model.TvShow;

public class FavoriteTvPresenter implements FavoriteTvContract.Presenter {
    private final FavoriteTvContract.View mView;
    private final Context context;

    FavoriteTvPresenter(FavoriteTvContract.View mView, Context context){
        this.mView = mView;
        this.mView.setPresenter(this);
        this.context = context;
    }

    FavoriteTvContract.View getView() {
        return mView;
    }

    @Override
    public void prepareData(FavoriteTvViewModel favoriteTvViewModel) {
        favoriteTvViewModel.setTvShow();
    }

    @Override
    public void navigateView(TvShow tvData) {
        Intent intent = new Intent(context, DetailMovieActivity.class);
        intent.putExtra(DetailMovieActivity.EXTRA_TV_SHOWS, tvData);
        context.startActivity(intent);
    }
}
