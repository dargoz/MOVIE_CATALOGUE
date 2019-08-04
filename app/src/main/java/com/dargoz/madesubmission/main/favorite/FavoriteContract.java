package com.dargoz.madesubmission.main.favorite;

import com.dargoz.madesubmission.BasePresenter;
import com.dargoz.madesubmission.BaseView;
import com.dargoz.madesubmission.main.movies.model.Movies;

import java.util.ArrayList;

public interface FavoriteContract {

    interface View extends BaseView<Presenter>{
        void initView();
        void showFavoriteData(ArrayList<Movies> moviesArrayList);
    }

    interface Presenter extends BasePresenter{
        void prepareData(FavoriteViewModel favoriteViewModel);
        ArrayList<Movies> getFavoriteData();
    }
}
