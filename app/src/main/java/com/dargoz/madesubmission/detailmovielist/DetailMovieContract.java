package com.dargoz.madesubmission.detailmovielist;

import android.content.Intent;

import com.dargoz.madesubmission.BasePresenter;
import com.dargoz.madesubmission.BaseView;
import com.dargoz.madesubmission.main.movies.model.Movies;
import com.dargoz.madesubmission.main.tvshow.model.TvShow;

public interface DetailMovieContract {
    interface View extends BaseView<Presenter>{
        void showMovieDetailInfo();
        void showFilmDetailsData(Object filmData);
    }

    interface Presenter extends BasePresenter{
        void prepareFilmDetails(final Movies movie, String category);
        Movies retrieveIntentMovieData(Intent intent);
        TvShow retrieveIntentTvShowData(Intent intent);
    }
}
