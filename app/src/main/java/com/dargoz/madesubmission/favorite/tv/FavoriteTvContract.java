package com.dargoz.madesubmission.favorite.tv;

import com.dargoz.madesubmission.BasePresenter;
import com.dargoz.madesubmission.BaseView;
import com.dargoz.madesubmission.main.tvshow.model.TvShow;

import java.util.ArrayList;

public interface FavoriteTvContract {
    interface View extends BaseView<Presenter> {
        void initView();
        void showFavoriteData(ArrayList<TvShow> tvShowArrayList);
        void showAlertDialog(TvShow tvShow);
    }

    interface Presenter extends BasePresenter {
        void prepareData(FavoriteTvViewModel favoriteTvViewModel);
        void navigateView(TvShow tvData);
    }
}
