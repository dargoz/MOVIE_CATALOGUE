package com.dargoz.madesubmission.main.movies;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;

import com.dargoz.madesubmission.R;
import com.dargoz.madesubmission.detailmovielist.DetailMovieActivity;
import com.dargoz.madesubmission.main.movies.model.Movies;

import java.util.ArrayList;

public class MoviesPresenter implements MoviesContract.Presenter {
    private final Context context;

    private String[] dataMovieTitle;
    private String[] dataMovieReleaseDate;
    private String[] dataMovieDesc;
    private String[] dataMovieGenre;
    private String[] dataMovieStatus;
    private String[] dataMovieScore;
    private String[] dataMovieRuntime;
    private TypedArray dataPoster;

    MoviesPresenter(MoviesContract.View mainView, Context context){
        this.context = context;
        mainView.setPresenter(this);
    }

    @Override
    public void prepareData() {
        dataMovieTitle = context
                .getResources().getStringArray(R.array.movie_title);
        dataMovieReleaseDate = context
                .getResources().getStringArray(R.array.movie_release_date);
        dataMovieDesc = context
                .getResources().getStringArray(R.array.movie_desc);
        dataMovieGenre = context
                .getResources().getStringArray(R.array.movie_genre);
        dataMovieStatus =context
                .getResources().getStringArray(R.array.movie_release_status);
        dataMovieScore = context
                .getResources().getStringArray(R.array.movie_score);
        dataMovieRuntime = context
                .getResources().getStringArray(R.array.movie_runtime);
        dataPoster = context
                .getResources().obtainTypedArray(R.array.movie_image);
    }

    @Override
    public ArrayList<Movies> addDataToList() {
        ArrayList<Movies> list = new ArrayList<>();
        for(int idx = 0 ; idx < dataMovieTitle.length; idx++){
            Movies movie = new Movies();
            movie.setTitle(dataMovieTitle[idx]);
            movie.setReleaseDate(dataMovieReleaseDate[idx]);
            movie.setDesc(dataMovieDesc[idx]);
            movie.setGenres(dataMovieGenre[idx]);
            movie.setStatus(dataMovieStatus[idx]);
            movie.setScore(Double.parseDouble(dataMovieScore[idx]));
            movie.setRuntime(dataMovieRuntime[idx]);
            movie.setImage(dataPoster.getResourceId(idx,-1));
            list.add(movie);
        }
        return list;
    }

    @Override
    public void navigateView(Movies movieData) {
        Intent intent = new Intent(context, DetailMovieActivity.class);
        intent.putExtra(DetailMovieActivity.EXTRA_MOVIE, movieData);
        context.startActivity(intent);
    }
}
