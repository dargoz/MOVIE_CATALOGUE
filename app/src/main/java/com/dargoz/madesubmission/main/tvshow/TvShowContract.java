package com.dargoz.madesubmission.main.tvshow;

import com.dargoz.madesubmission.BasePresenter;
import com.dargoz.madesubmission.BaseView;
import com.dargoz.madesubmission.main.tvshow.model.TvShow;

import java.util.ArrayList;

public interface TvShowContract {
    interface View extends BaseView<Presenter>{
        void showTvList();
    }

    interface Presenter extends BasePresenter{
        void prepareData();
        ArrayList<TvShow> addDataToList();
        void navigateView(TvShow tvShowData);
    }
}
