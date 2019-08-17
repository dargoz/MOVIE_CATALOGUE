package com.dargoz.madesubmission.main.tvshow;

import android.content.Intent;
import android.support.v4.app.Fragment;

import com.dargoz.madesubmission.Constant;
import com.dargoz.madesubmission.detailmovielist.DetailMovieActivity;
import com.dargoz.madesubmission.main.tvshow.model.TvShow;
import com.dargoz.madesubmission.main.tvshow.model.TvShowViewModel;


public class TvShowPresenter implements TvShowContract.Presenter {
    private final TvShowContract.View mainView;
    private TvShowViewModel tvShowViewModel;

    TvShowPresenter(TvShowContract.View view){
        this.mainView = view;
        this.mainView.setPresenter(this);
    }

    @Override
    public void prepareData(TvShowViewModel tvShowViewModel) {
        this.tvShowViewModel = tvShowViewModel;
        String url = Constant.getUrlOf(
                Constant.URL_TYPE_DISCOVER,
                Constant.URL_TV,
                "",
                ((TvShowFragment)mainView).getContext());
        this.tvShowViewModel.setTvShow(mainView, url);
    }

    @Override
    public void navigateView(TvShow tvShowData) {
        Intent intent = new Intent(((Fragment)mainView).getContext(), DetailMovieActivity.class);
        intent.putExtra(DetailMovieActivity.EXTRA_TV_SHOWS, tvShowData);
        ((Fragment) mainView).startActivity(intent);
    }

    @Override
    public boolean onAllDataFinishLoaded() {
        return tvShowViewModel.getLoadedItemCounter() >= tvShowViewModel.getTotalItem();
    }
}
