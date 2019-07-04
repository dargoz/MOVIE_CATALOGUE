package com.dargoz.madesubmission.detailmovielist;

import android.content.Intent;

import com.dargoz.madesubmission.main.movies.model.Movies;
import com.dargoz.madesubmission.main.tvshow.model.TvShow;

import java.util.ArrayList;


public class DetailMoviePresenter implements DetailMovieContract.Presenter {

    DetailMoviePresenter(DetailMovieContract.View view){
        DetailMovieContract.View mView = view;
        mView.setPresenter(this);
    }

    @Override
    public Movies retrieveIntentMovieData(Intent intent) {
        return intent.getParcelableExtra(DetailMovieActivity.EXTRA_MOVIE);
    }

    @Override
    public TvShow retrieveIntentTvShowData(Intent intent) {
        return intent.getParcelableExtra(DetailMovieActivity.EXTRA_TV_SHOWS);
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
