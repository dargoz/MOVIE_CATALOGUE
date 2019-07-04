package com.dargoz.madesubmission.main.movies;

import android.content.Intent;
import android.content.res.TypedArray;
import android.support.v4.app.Fragment;

import com.dargoz.madesubmission.R;
import com.dargoz.madesubmission.detailmovielist.DetailMovieActivity;
import com.dargoz.madesubmission.main.movies.model.Movies;

import java.util.ArrayList;
import java.util.Objects;

public class MoviesPresenter implements MoviesContract.Presenter {
    private final MoviesContract.View mainView;

    private String[] dataMovieTitle;
    private String[] dataMovieReleaseDate;
    private String[] dataMovieDesc;
    private String[] dataMovieGenre;
    private String[] dataMovieStatus;
    private String[] dataMovieScore;
    private String[] dataMovieRuntime;
    private TypedArray dataPoster;

    MoviesPresenter(MoviesContract.View mainView){
        this.mainView = mainView;
        this.mainView.setPresenter(this);
    }

    @Override
    public void prepareData() {
        dataMovieTitle = ((Fragment)mainView).getResources().getStringArray(R.array.movie_title);
        dataMovieReleaseDate = (Objects.requireNonNull(((Fragment) mainView).getContext()))
                .getResources().getStringArray(R.array.movie_release_date);
        dataMovieDesc = (Objects.requireNonNull(((Fragment) mainView).getContext()))
                .getResources().getStringArray(R.array.movie_desc);
        dataMovieGenre = (Objects.requireNonNull(((Fragment) mainView).getContext()))
                .getResources().getStringArray(R.array.movie_genre);
        dataMovieStatus =(Objects.requireNonNull(((Fragment) mainView).getContext()))
                .getResources().getStringArray(R.array.movie_release_status);
        dataMovieScore = (Objects.requireNonNull(((Fragment) mainView).getContext()))
                .getResources().getStringArray(R.array.movie_score);
        dataMovieRuntime = (Objects.requireNonNull(((Fragment) mainView).getContext()))
                .getResources().getStringArray(R.array.movie_runtime);
        dataPoster = (Objects.requireNonNull(((Fragment) mainView).getContext()))
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
        Intent intent = new Intent(((Fragment)mainView).getContext(), DetailMovieActivity.class);
        intent.putExtra(DetailMovieActivity.EXTRA_MOVIE, movieData);
        ((Fragment)mainView).startActivity(intent);
    }
}
