package com.dargoz.madesubmission.main.tvshow;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANRequest;
import com.androidnetworking.common.ANResponse;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.BitmapRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.dargoz.madesubmission.Constant;
import com.dargoz.madesubmission.R;
import com.dargoz.madesubmission.Utils;
import com.dargoz.madesubmission.detailmovielist.DetailMovieActivity;
import com.dargoz.madesubmission.main.movies.model.Movies;
import com.dargoz.madesubmission.main.tvshow.model.TvShow;
import com.dargoz.madesubmission.repository.FilmImageRepository;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TvShowPresenter implements TvShowContract.Presenter {
    private final TvShowContract.View mainView;
    private ArrayList<TvShow> tvShowsList = new ArrayList<>();

    TvShowPresenter(TvShowContract.View view){
        this.mainView = view;
        this.mainView.setPresenter(this);
    }

    @Override
    public void prepareData() {
        tvShowsList.clear();
        String url =  Constant.getUrlOf(
                Constant.URL_TYPE_DISCOVER,
                Constant.URL_TV,
                0,
                ((TvShowFragment)mainView).getContext());
        AndroidNetworking.get(url)
                .setTag("test")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray results =  response.getJSONArray("results");
                            Log.v("DRG","response : " + results);
                            for(int i=0; i<results.length();i++) {
                                JSONObject movieObject = results.getJSONObject(i);
                                final TvShow tvShowItem = new TvShow(movieObject);
                                AndroidNetworking.get(
                                        Utils.getObjectImageUrl(
                                                Constant.IMAGE_URL,
                                                Constant.IMAGE_SIZE_W500,
                                                tvShowItem.getImagePath()
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
                                                tvShowItem.setImageId(image.getGenerationId());
                                                tvShowsList.add(tvShowItem);
                                                mainView.showTvList();
                                            }

                                            @Override
                                            public void onError(ANError anError) {
                                                Log.w("DRG","Error getting Image : " + anError);
                                            }
                                        });



                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.v("DRG","Android Tv Network Finish Loading . . . ");
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.w("DRG","ANError : " + anError);
                    }
                });

    }

    @Override
    public ArrayList<TvShow> addDataToList() {
        return tvShowsList;
    }

    @Override
    public void navigateView(TvShow tvShowData) {
        Intent intent = new Intent(((Fragment)mainView).getContext(), DetailMovieActivity.class);
        intent.putExtra(DetailMovieActivity.EXTRA_TV_SHOWS, tvShowData);
        ((Fragment) mainView).startActivity(intent);
    }
}
