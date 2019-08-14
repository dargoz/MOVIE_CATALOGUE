package com.dargoz.madesubmission.favorite.movie;

import android.content.Context;
import android.content.Intent;

import com.dargoz.madesubmission.detailmovielist.DetailMovieActivity;
import com.dargoz.madesubmission.main.movies.model.Movies;

public class FavoriteMoviePresenter implements FavoriteMovieContract.Presenter {
    private final Context context;
    private final FavoriteMovieContract.View mView;

    FavoriteMoviePresenter(FavoriteMovieContract.View mView, Context context){
        this.context = context;
        this.mView = mView;
        this.mView.setPresenter(this);
    }

    FavoriteMovieContract.View getView() {
        return mView;
    }

    @Override
    public void prepareData(FavoriteMovieViewModel favoriteMovieViewModel) {
        favoriteMovieViewModel.setMovie();
    }

    @Override
    public void navigateView(Movies movieData) {
        Intent intent = new Intent(context, DetailMovieActivity.class);
        intent.putExtra(DetailMovieActivity.EXTRA_MOVIE, movieData);
        context.startActivity(intent);
    }


}
