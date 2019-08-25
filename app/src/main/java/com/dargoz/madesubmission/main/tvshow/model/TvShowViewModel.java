package com.dargoz.madesubmission.main.tvshow.model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.graphics.Bitmap;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.BitmapRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.dargoz.madesubmission.Constant;
import com.dargoz.madesubmission.Utils;
import com.dargoz.madesubmission.main.tvshow.TvShowContract;
import com.dargoz.madesubmission.repository.FilmImageRepository;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TvShowViewModel extends ViewModel {
    private final MutableLiveData<ArrayList<TvShow>> tvShowList = new MutableLiveData<>();
    private final ArrayList<TvShow> tvShowItemList = new ArrayList<>();
    private int totalItem = 0;
    private int loadedItemCounter = 0;

    public int getTotalItem() {
        return totalItem;
    }

    public int getLoadedItemCounter() {
        return loadedItemCounter;
    }

    public void setTvShow(final TvShowContract.View mainView, String url){
        tvShowItemList.clear();
        AndroidNetworking.get(url)
                .setTag("tv")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray results =  response.getJSONArray("results");
                            totalItem = results.length();
                            for(int i=0; i<totalItem ;i++) {
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
                                                loadedItemCounter++;
                                                FilmImageRepository.imageList.add(response);
                                                tvShowItem.setImageId(response.getGenerationId());
                                                tvShowItemList.add(tvShowItem);
                                                tvShowList.setValue(tvShowItemList);
                                            }

                                            @Override
                                            public void onError(ANError anError) {
                                                loadedItemCounter++;
                                            }
                                        });

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        mainView.showReloadButton(true);
                    }
                });
    }

    public LiveData<ArrayList<TvShow>> getTvShowList(){
        return tvShowList;
    }
}
