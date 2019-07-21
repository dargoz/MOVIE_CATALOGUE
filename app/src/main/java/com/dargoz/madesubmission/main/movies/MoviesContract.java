package com.dargoz.madesubmission.main.movies;


import com.dargoz.madesubmission.BasePresenter;
import com.dargoz.madesubmission.BaseView;
import com.dargoz.madesubmission.main.movies.model.Movies;
import com.dargoz.madesubmission.main.movies.model.MoviesViewModel;

import java.util.ArrayList;

public interface MoviesContract {

    interface View extends BaseView<Presenter> {
        void showMovieList(ArrayList<Movies> moviesArrayList);
        void showReloadButton(boolean state);
    }

    interface Presenter extends BasePresenter {
        void prepareData(MoviesViewModel moviesViewModel);
        void navigateView(Movies movieData);
        boolean onAllDataFinishLoaded();
    }
}
