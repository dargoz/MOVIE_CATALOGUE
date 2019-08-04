package com.dargoz.madesubmission.repository;

import android.os.AsyncTask;
import android.util.Log;

import com.dargoz.madesubmission.BasePresenter;
import com.dargoz.madesubmission.Constant;
import com.dargoz.madesubmission.main.MainActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DaoTask extends AsyncTask<String,Void,List<MovieEntity>> {

    private List<MovieEntity> movieEntities = new ArrayList<>();

    public List<MovieEntity> getMovieEntities() {
        return movieEntities;
    }

    public void setMovieEntities(MovieEntity... movieEntities) {
        this.movieEntities.clear();
        Collections.addAll(this.movieEntities, movieEntities);
    }

    @Override
    protected List<MovieEntity> doInBackground(String... strings) {
        for(String param : strings){
            if(param.equals(Constant.GET_FAVORITE_MOVIES)){
                movieEntities = MainActivity.getDatabase().movieDao().getAll();
                for(MovieEntity movieEntity : movieEntities)
                    Log.i("DRG", "movie title : " + movieEntity.getTitle());
            }else if(param.equals(Constant.INSERT_ALL_MOVIES)){
                for(MovieEntity movieEntity : movieEntities)
                    MainActivity.getDatabase().movieDao().insertAll(movieEntity);
            }
        }
        return movieEntities;
    }

}
