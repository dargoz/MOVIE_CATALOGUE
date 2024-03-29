package com.dargoz.madesubmission.favorite.movie;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.dargoz.madesubmission.utilities.Constant;
import com.dargoz.madesubmission.main.movies.model.Movies;
import com.dargoz.madesubmission.repository.movie.MovieEntity;
import com.dargoz.madesubmission.repository.movie.MovieDaoTask;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("WeakerAccess")
public class FavoriteMovieViewModel extends ViewModel {
    private final MutableLiveData<ArrayList<Movies>> movieList = new MutableLiveData<>();


    public void setMovie() {
        MovieDaoTask task = new MovieDaoTask();
        try{
            movieList.setValue(getFavoriteData(task.execute(Constant.GET_FAVORITE_MOVIES).get()));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private ArrayList<Movies> getFavoriteData(List<MovieEntity> movieEntities) {
        ArrayList<Movies> moviesArrayList = new ArrayList<>();
        for(MovieEntity movieEntity : movieEntities){
            Movies movie = new Movies();
            movie.setId(movieEntity.getId());
            movie.setTitle(movieEntity.getTitle());
            movie.setDesc(movieEntity.getDesc());
            movie.setReleaseDate(movieEntity.getReleaseDate());
            movie.setRuntime(movieEntity.getRuntime());
            movie.setScore(movieEntity.getScore());
            moviesArrayList.add(movie);
        }
        return moviesArrayList;
    }
    public LiveData<ArrayList<Movies>> getMovieList(){
        return movieList;
    }
}
