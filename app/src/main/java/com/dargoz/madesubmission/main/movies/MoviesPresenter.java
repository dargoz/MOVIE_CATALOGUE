package com.dargoz.madesubmission.main.movies;

import android.content.Context;
import android.content.Intent;

import com.dargoz.madesubmission.Constant;
import com.dargoz.madesubmission.detailmovielist.DetailMovieActivity;
import com.dargoz.madesubmission.main.movies.model.Movies;
import com.dargoz.madesubmission.main.movies.model.MoviesViewModel;


public class MoviesPresenter implements MoviesContract.Presenter {
    private final Context context;
    private final MoviesContract.View mView;
    private MoviesViewModel moviesViewModel;

    MoviesPresenter(MoviesContract.View mainView, Context context){
        this.context = context;
        this.mView = mainView;
        this.mView.setPresenter(this);
    }

    @Override
    public void prepareData(MoviesViewModel moviesViewModel) {
        this.moviesViewModel = moviesViewModel;
        String url = Constant.getUrlOf(
                Constant.URL_TYPE_DISCOVER,
                Constant.URL_MOVIES,
                "",
                ((MoviesFragment)mView).getContext());
        this.moviesViewModel.setMovie(mView, url);
    }

    @Override
    public void navigateView(Movies movieData) {
        Intent intent = new Intent(context, DetailMovieActivity.class);
        intent.putExtra(DetailMovieActivity.EXTRA_MOVIE, movieData);
        context.startActivity(intent);
    }

    @Override
    public boolean onAllDataFinishLoaded() {
        return moviesViewModel.getLoadedItemCounter() >= moviesViewModel.getTotalItem();
    }
}
