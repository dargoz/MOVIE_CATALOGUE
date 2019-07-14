package com.dargoz.madesubmission.detailmovielist;

import android.content.Intent;

import com.dargoz.madesubmission.BasePresenter;
import com.dargoz.madesubmission.BaseView;
import com.dargoz.madesubmission.main.movies.model.Movies;
import com.dargoz.madesubmission.main.tvshow.model.TvShow;

public interface DetailMovieContract {
    interface View extends BaseView<Presenter>{
        void showMovieDetailInfo();
        void showMovieDetailsData(Movies movie);
    }

    interface Presenter extends BasePresenter{
        void prepareMovieDetails(final Movies movie);
        Movies retrieveIntentMovieData(Intent intent);
        TvShow retrieveIntentTvShowData(Intent intent);
    }
}
