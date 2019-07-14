package com.dargoz.madesubmission.main.movies;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANRequest;
import com.androidnetworking.common.ANResponse;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.BitmapRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.dargoz.madesubmission.Constant;
import com.dargoz.madesubmission.Utils;
import com.dargoz.madesubmission.detailmovielist.DetailMovieActivity;
import com.dargoz.madesubmission.main.movies.model.Movies;
import com.dargoz.madesubmission.repository.FilmImageRepository;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MoviesPresenter implements MoviesContract.Presenter {
    private final Context context;
    private final ArrayList<Movies> moviesItemList = new ArrayList<>();
    private final MoviesContract.View mView;

    MoviesPresenter(MoviesContract.View mainView, Context context){
        this.context = context;
        this.mView = mainView;
        this.mView.setPresenter(this);
    }

    @Override
    public void prepareData() {
        moviesItemList.clear();
        String url =  "https://api.themoviedb.org/3/discover/movie?api_key=" +
                Constant.API_KEY + "&language=en-US";
        AndroidNetworking.get(url)
                .setTag("movies")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray results =  response.getJSONArray("results");
                            Log.i("DRG","response : " + results);
                            for(int i=0; i<results.length();i++) {
                                JSONObject movieObject = results.getJSONObject(i);
                                final Movies moviesItem = new Movies(movieObject);
                                AndroidNetworking.get(
                                        Utils.getObjectImageUrl(
                                                Constant.IMAGE_URL,
                                                Constant.IMAGE_SIZE_W500,
                                                moviesItem.getImagePath()
                                        ))
                                        .setTag("imageRequestTag")
                                        .setPriority(Priority.HIGH)
                                        .setBitmapConfig(Bitmap.Config.ARGB_8888)
                                        .build()
                                        .getAsBitmap(new BitmapRequestListener() {
                                            @Override
                                            public void onResponse(Bitmap response) {
                                                Log.d("DRG","success : ");
                                                Bitmap image = response;
                                                FilmImageRepository.imageList.add(image);
                                                moviesItem.setImageId(image.getGenerationId());
                                                moviesItemList.add(moviesItem);
                                                mView.showMovieList();
                                            }

                                            @Override
                                            public void onError(ANError anError) {
                                                Log.w("DRG","Error getting Image : " + anError);
                                            }
                                        });

                            }

                            Log.d("DRG","Android Network Finish Loading . . . ");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.w("DRG","ANError : " + anError);
                    }
                });

    }

    @Override
    public ArrayList<Movies> addDataToList() {
        Log.i("DRG","addDataToList Called");
        return moviesItemList;
    }

    @Override
    public void navigateView(Movies movieData) {
        Intent intent = new Intent(context, DetailMovieActivity.class);
        intent.putExtra(DetailMovieActivity.EXTRA_MOVIE, movieData);
        context.startActivity(intent);
    }
}
