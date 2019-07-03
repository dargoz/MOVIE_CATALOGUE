package com.dargoz.madesubmission.detailmovielist;

import android.content.Intent;

import com.dargoz.madesubmission.BasePresenter;
import com.dargoz.madesubmission.BaseView;
import com.dargoz.madesubmission.main.movies.model.Movies;

public interface DetailMovieContract {
    interface View extends BaseView<Presenter>{
        void showMovieDetailInfo();
    }

    interface Presenter extends BasePresenter{
        Movies retrieveIntentMovieData(Intent intent);
    }
}
