package com.dargoz.madesubmission.repository.tvshow;

import android.os.AsyncTask;

import com.dargoz.madesubmission.Constant;
import com.dargoz.madesubmission.MainActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TvDaoTask extends AsyncTask<String,Void, List<TvShowEntity>> {
    private int id;
    private List<TvShowEntity> tvShowEntities = new ArrayList<>();

    public void setId(int id ){
        this.id = id;
    }

    public void setTvShowEntities(TvShowEntity... tvShowEntities){
        this.tvShowEntities.clear();
        Collections.addAll(this.tvShowEntities, tvShowEntities);
    }
    @Override
    protected List<TvShowEntity> doInBackground(String... strings) {
        for(String param : strings){
            switch (param){
                case Constant.GET_FAVORITE_TV_SHOW:
                    tvShowEntities = MainActivity.getDatabase().tvShowDao().getAll();
                    break;
                case Constant.INSERT_ALL_TV_SHOW:
                    for (TvShowEntity tvShowEntity : tvShowEntities)
                        MainActivity.getDatabase().tvShowDao().insertAll(tvShowEntity);
                    break;
                case Constant.DELETE_TV_SHOW:
                    MainActivity.getDatabase().tvShowDao().delete(tvShowEntities.get(0));
                    break;
                case Constant.FIND_TV_SHOW:
                    try {
                        tvShowEntities.clear();
                        tvShowEntities.add(MainActivity.getDatabase().tvShowDao().find(id));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }

        return tvShowEntities;
    }

}
