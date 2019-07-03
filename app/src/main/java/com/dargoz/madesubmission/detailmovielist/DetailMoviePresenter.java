package com.dargoz.madesubmission.detailmovielist;

import android.content.Intent;

import com.dargoz.madesubmission.main.movies.model.Movies;

import java.util.ArrayList;


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

    @Override
    public ArrayList<String> getListGenre(String genres) {
        ArrayList<String> genreList = new ArrayList<>();
        String[] genreItems = genres.split("[,]");
        for(String genreItem : genreItems){
            genreList.add(genreItem.trim());
        }
        return genreList;
    }
}
