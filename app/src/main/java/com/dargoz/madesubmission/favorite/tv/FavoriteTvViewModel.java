package com.dargoz.madesubmission.favorite.tv;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.dargoz.madesubmission.Constant;
import com.dargoz.madesubmission.main.tvshow.model.TvShow;
import com.dargoz.madesubmission.repository.tvshow.TvDaoTask;
import com.dargoz.madesubmission.repository.tvshow.TvShowEntity;

import java.util.ArrayList;
import java.util.List;

public class FavoriteTvViewModel extends ViewModel {
    private final MutableLiveData<ArrayList<TvShow>> tvShowList = new MutableLiveData<>();


    public void setTvShow() {
        TvDaoTask task = new TvDaoTask();
        try{
            tvShowList.setValue(getFavoriteData(task.execute(Constant.GET_FAVORITE_TV_SHOW).get()));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private ArrayList<TvShow> getFavoriteData(List<TvShowEntity> tvEntities) {
        ArrayList<TvShow> tvsArrayList = new ArrayList<>();
        for(TvShowEntity tvEntity : tvEntities){
            TvShow tvShow = new TvShow();
            tvShow.setId(tvEntity.getId());
            tvShow.setTitle(tvEntity.getTitle());
            tvShow.setDesc(tvEntity.getDesc());
            tvShow.setReleaseDate(tvEntity.getReleaseDate());
            tvShow.setRuntime(tvEntity.getRuntime());
            tvShow.setTotalEpisode(tvEntity.getTotalEpisode());
            tvShow.setScore(tvEntity.getScore());
            tvsArrayList.add(tvShow);
        }
        return tvsArrayList;
    }
    public LiveData<ArrayList<TvShow>> getTvShowList(){
        return tvShowList;
    }
}
