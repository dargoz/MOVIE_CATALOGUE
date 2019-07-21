package com.dargoz.madesubmission.main.tvshow;

import com.dargoz.madesubmission.BasePresenter;
import com.dargoz.madesubmission.BaseView;
import com.dargoz.madesubmission.main.tvshow.model.TvShow;
import com.dargoz.madesubmission.main.tvshow.model.TvShowViewModel;

import java.util.ArrayList;

public interface TvShowContract {
    interface View extends BaseView<Presenter>{
        void showTvList(ArrayList<TvShow> tvShowArrayList);
        void showReloadButton(boolean state);
    }

    interface Presenter extends BasePresenter{
        void prepareData(TvShowViewModel tvShowViewModel);
        void navigateView(TvShow tvShowData);
        boolean onAllDataFinishLoaded();
    }
}
