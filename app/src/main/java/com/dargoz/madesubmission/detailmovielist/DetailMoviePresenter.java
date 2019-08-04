package com.dargoz.madesubmission.detailmovielist;

import android.content.Intent;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.dargoz.madesubmission.Constant;
import com.dargoz.madesubmission.Utils;
import com.dargoz.madesubmission.main.movies.model.Movies;
import com.dargoz.madesubmission.main.tvshow.model.TvShow;
import com.dargoz.madesubmission.main.movies.model.Genre;

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
                        movie.getId(),
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
                            switch (category){
                                case Constant.URL_TV:
                                    TvShow tvShow = (TvShow) movie;
                                    JSONArray runtimeResponse =
                                            response.getJSONArray(Constant.TV_KEY_RUNTIME);
                                    movie.setRuntime(Utils.formatRuntime(
                                            runtimeResponse.getInt(0)));
                                    tvShow.setTotalEpisode(String.valueOf(
                                            response.getInt(Constant.KEY_TOTAL_EPISODE)
                                    ));
                                    mView.showFilmDetailsData(tvShow);
                                    break;
                                    default:
                                        movie.setRuntime(Utils.formatRuntime(
                                                response.getInt(Constant.MOVIES_KEY_RUNTIME)));
                                        mView.showFilmDetailsData(movie);
                                        break;
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

}
