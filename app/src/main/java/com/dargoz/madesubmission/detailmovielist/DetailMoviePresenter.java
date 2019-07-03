package com.dargoz.madesubmission.detailmovielist;

import android.content.Intent;

import com.dargoz.madesubmission.main.movies.model.Movies;


public class DetailMoviePresenter implements DetailMovieContract.Presenter {
    private final DetailMovieContract.View mView;

    DetailMoviePresenter(DetailMovieContract.View view){
        this.mView = view;
        this.mView.setPresenter(this);
    }

    @Override
    public Movies retrieveIntentMovieData(Intent intent) {
        return intent.getParcelableExtra(DetailMovieActivity.EXTRA_MOVIE);
    }
}
