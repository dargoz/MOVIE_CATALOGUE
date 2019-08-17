package com.dargoz.madesubmission.detailmovielist;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.dargoz.madesubmission.Constant;
import com.dargoz.madesubmission.Utils;
import com.dargoz.madesubmission.main.movies.model.Movies;
import com.dargoz.madesubmission.main.tvshow.model.TvShow;
import com.dargoz.madesubmission.main.movies.model.Genre;
import com.dargoz.madesubmission.repository.movie.MovieDaoTask;
import com.dargoz.madesubmission.repository.movie.MovieEntity;
import com.dargoz.madesubmission.repository.tvshow.TvDaoTask;
import com.dargoz.madesubmission.repository.tvshow.TvShowEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class DetailMoviePresenter implements DetailMovieContract.Presenter {
    private final DetailMovieContract.View mView;

    DetailMoviePresenter(DetailMovieContract.View view){
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void prepareFilmDetails(final Movies movie, final String category) {
        String DETAIL_MOVIE_URL =
                Constant.getUrlOf(
                        Constant.URL_TYPE_DETAIL,
                        category,
                        String.valueOf(movie.getId()),
                        (DetailMovieActivity)mView);
        AndroidNetworking.get(DETAIL_MOVIE_URL)
                .setTag("filmDetail")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray genresListResponse = response.getJSONArray("genres");
                            ArrayList<Genre> genresList = new ArrayList<>();
                            for(int idx = 0 ; idx < genresListResponse.length(); idx++){
                                JSONObject genreObject = genresListResponse.getJSONObject(idx);
                                Genre genre = new Genre(genreObject);
                                genresList.add(genre);
                            }
                            movie.setGenres(genresList);
                            movie.setStatus(response.getString("status"));
                            if (Constant.URL_TV.equals(category)) {
                                TvShow tvShow = (TvShow) movie;
                                JSONArray runtimeResponse =
                                        response.getJSONArray(Constant.TV_KEY_RUNTIME);
                                movie.setRuntime(Utils.formatRuntime(
                                        runtimeResponse.getInt(0)));
                                tvShow.setTotalEpisode(String.valueOf(
                                        response.getInt(Constant.KEY_TOTAL_EPISODE)
                                ));
                                mView.showFilmDetailsData(tvShow);
                            } else {
                                movie.setRuntime(Utils.formatRuntime(
                                        response.getInt(Constant.MOVIES_KEY_RUNTIME)));
                                mView.showFilmDetailsData(movie);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });

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
    public void addToTvFavorite(TvShow tvShow, Movies movieData) {
        TvDaoTask tvDaoTask = new TvDaoTask();
        Bitmap image = Utils.getImageBitmap(tvShow);
        Utils.saveToInternalStorage( (Context)mView, image, String.valueOf(tvShow.getId()));
        if (movieData != null) {
            final TvShowEntity tvShowEntity = new TvShowEntity(
                    tvShow.getId(),
                    tvShow.getTitle(),
                    tvShow.getDesc(),
                    movieData.getGenres().toString(),
                    tvShow.getReleaseDate(),
                    movieData.getStatus(),
                    movieData.getRuntime(),
                    tvShow.getTotalEpisode(),
                    movieData.getScore()
            );
            try {
                tvDaoTask.setTvShowEntities(tvShowEntity);
                tvDaoTask.execute(Constant.INSERT_ALL_TV_SHOW);
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
            mView.showToastMessage(Constant.SUCCESS_INSERT);
            mView.updateButtonImageState(true);
        } else {
            mView.showToastMessage(Constant.FAILED_INSERT);
        }
    }

    @Override
    public void addToMovieFavorite(Movies movie, Movies movieData) {
        MovieDaoTask task = new MovieDaoTask();
        Bitmap image = Utils.getImageBitmap(movie);
        Utils.saveToInternalStorage( (Context)mView, image, String.valueOf(movie.getId()));
        if (movieData != null) {
            final MovieEntity movieEntity = new MovieEntity(
                    movie.getId(),
                    movie.getTitle(),
                    movie.getDesc(),
                    movieData.getGenres().toString(),
                    movie.getReleaseDate(),
                    movieData.getStatus(),
                    movieData.getRuntime(),
                    movieData.getScore()
            );
            try {
                task.setMovieEntities(movieEntity);
                task.execute(Constant.INSERT_ALL_MOVIES);
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
            mView.showToastMessage(Constant.SUCCESS_INSERT);
            mView.updateButtonImageState(true);
        } else {
            mView.showToastMessage(Constant.FAILED_INSERT);
        }
    }

    @Override
    public void removeFromTvFavorite(TvShow tvShow, Movies movieData) {
        TvDaoTask task = new TvDaoTask();

        if (tvShow != null && movieData != null) {
            final TvShowEntity tvShowEntity = new TvShowEntity(
                    tvShow.getId(),
                    tvShow.getTitle(),
                    tvShow.getDesc(),
                    movieData.getGenres().toString(),
                    tvShow.getReleaseDate(),
                    movieData.getStatus(),
                    movieData.getRuntime(),
                    tvShow.getTotalEpisode(),
                    movieData.getScore()
            );
            try {
                task.setTvShowEntities(tvShowEntity);
                task.execute(Constant.DELETE_TV_SHOW);
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
            mView.showToastMessage(Constant.SUCCESS_DELETE);
            mView.updateButtonImageState(false);
        } else
            mView.showToastMessage(Constant.FAILED_DELETE);

    }

    @Override
    public void removeFromMovieFavorite(Movies movie, Movies movieData) {
        MovieDaoTask task = new MovieDaoTask();

        if (movie != null && movieData != null) {
            final MovieEntity movieEntity = new MovieEntity(
                    movie.getId(),
                    movie.getTitle(),
                    movie.getDesc(),
                    movieData.getGenres().toString(),
                    movie.getReleaseDate(),
                    movieData.getStatus(),
                    movieData.getRuntime(),
                    movieData.getScore()
            );
            try {
                task.setMovieEntities(movieEntity);
                task.execute(Constant.DELETE_MOVIE);
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
            mView.showToastMessage(Constant.SUCCESS_DELETE);
            mView.updateButtonImageState(false);
        } else
            mView.showToastMessage(Constant.FAILED_DELETE);

    }

}
