package com.dargoz.madesubmission.favorite;

import android.util.Log;

import com.dargoz.madesubmission.main.movies.model.Movies;
import com.dargoz.madesubmission.repository.MovieEntity;

import java.util.ArrayList;
import java.util.List;

public class FavoritePresenter implements FavoriteContract.Presenter {
    private FavoriteContract.View mView;
    private List<MovieEntity> movieEntities = new ArrayList<>();

    FavoritePresenter(FavoriteContract.View mView){
        this.mView = mView;
        this.mView.setPresenter(this);
    }

    @Override
    public void prepareData(FavoriteViewModel favoriteViewModel) {
        favoriteViewModel.setMovie(mView);
    }

    @Override
    public ArrayList<Movies> getFavoriteData() {
        ArrayList<Movies> moviesArrayList = new ArrayList<>();
        for(MovieEntity movieEntity : movieEntities){
            Log.i("DRG","movies entity : " + movieEntity.getTitle());
            Movies movie = new Movies();
            movie.setTitle(movieEntity.getTitle());
            movie.setReleaseDate(movieEntity.getReleaseDate());
            movie.setRuntime(movieEntity.getRuntime());
        }
        return moviesArrayList;
    }
}
