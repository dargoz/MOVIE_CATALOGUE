package com.dargoz.madesubmission.main.movies;


import com.dargoz.madesubmission.BasePresenter;
import com.dargoz.madesubmission.BaseView;
import com.dargoz.madesubmission.main.movies.model.Movies;

import java.util.ArrayList;

public interface MoviesContract {

    interface View extends BaseView<Presenter> {
        void showMovieList();
    }

    interface Presenter extends BasePresenter {
        void prepareData();
        ArrayList<Movies> addDataToList();
        void navigateView(Movies movieData);
    }
}
