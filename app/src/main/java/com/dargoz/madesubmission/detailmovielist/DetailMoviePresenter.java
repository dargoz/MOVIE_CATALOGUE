package com.dargoz.madesubmission.detailmovielist;

import android.content.Intent;
import android.util.Log;

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
    DetailMovieContract.View mView;
    DetailMoviePresenter(DetailMovieContract.View view){
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void prepareMovieDetails(final Movies movie) {
        String DETAIL_MOVIE_URL =
                "https://api.themoviedb.org/3/movie/"+
                        movie.getId() + "?language=en-US&api_key=" + Constant.API_KEY;
        Log.i("DRG","url : " + DETAIL_MOVIE_URL);

        AndroidNetworking.get(DETAIL_MOVIE_URL)
                .setTag("test")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("DRG","response : " + response);
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
                            movie.setRuntime(Utils.formatRuntime(response.getInt("runtime")));
                            mView.showMovieDetailsData(movie);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.w("DRG","Error fetch detail : " + anError);
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
