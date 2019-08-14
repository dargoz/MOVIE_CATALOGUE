package com.dargoz.madesubmission.favorite;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.dargoz.madesubmission.Constant;
import com.dargoz.madesubmission.main.movies.model.Movies;
import com.dargoz.madesubmission.repository.DaoTask;
import com.dargoz.madesubmission.repository.MovieEntity;

import java.util.ArrayList;
import java.util.List;

public class FavoriteViewModel extends ViewModel {
    private final MutableLiveData<ArrayList<Movies>> movieList = new MutableLiveData<>();


    public void setMovie(FavoriteContract.View mView) {
        DaoTask task = new DaoTask();
        try{
            movieList.setValue(getFavoriteData(task.execute(Constant.GET_FAVORITE_MOVIES).get()));
        }catch (Exception e){
            Log.w("DRG","DAO Exception : " + e);
        }
    }

    private ArrayList<Movies> getFavoriteData(List<MovieEntity> movieEntities) {
        ArrayList<Movies> moviesArrayList = new ArrayList<>();
        for(MovieEntity movieEntity : movieEntities){
            Log.i("DRG","movies entity : " + movieEntity.getTitle());
            Movies movie = new Movies();
            movie.setTitle(movieEntity.getTitle());
            movie.setReleaseDate(movieEntity.getReleaseDate());
            movie.setRuntime(movieEntity.getRuntime());
            moviesArrayList.add(movie);
        }
        return moviesArrayList;
    }
    public LiveData<ArrayList<Movies>> getMovieList(){
        return movieList;
    }
}
