package com.dargoz.madesubmission.favorite.movie;

import com.dargoz.madesubmission.BasePresenter;
import com.dargoz.madesubmission.BaseView;
import com.dargoz.madesubmission.main.movies.model.Movies;

import java.util.ArrayList;

public interface FavoriteMovieContract {

    interface View extends BaseView<Presenter>{
        void initView();
        void showFavoriteData(ArrayList<Movies> moviesArrayList);
        void showAlertDialog(Movies movie);
    }

    interface Presenter extends BasePresenter{
        void prepareData(FavoriteMovieViewModel favoriteMovieViewModel);
        void navigateView(Movies movieData);
    }
}
