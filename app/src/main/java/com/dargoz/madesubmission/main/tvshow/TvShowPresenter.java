package com.dargoz.madesubmission.main.tvshow;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.support.v4.app.Fragment;

import com.dargoz.madesubmission.R;
import com.dargoz.madesubmission.detailmovielist.DetailMovieActivity;
import com.dargoz.madesubmission.main.tvshow.model.TvShow;

import java.util.ArrayList;

public class TvShowPresenter implements TvShowContract.Presenter {
    private TvShowContract.View mainView;

    private String[] dataTvShowTitle;
    private String[] dataTvShowReleaseDate;
    private String[] dataTvShowDesc;
    private String[] dataTvShowGenre;
    private String[] dataTvShowStatus;
    private String[] dataTvShowScore;
    private String[] dataTvShowRuntime;
    private String[] dataTvShowTotalEps;
    private TypedArray dataPoster;

    TvShowPresenter(TvShowContract.View view){
        this.mainView = view;
        this.mainView.setPresenter(this);
    }

    @Override
    public void prepareData() {
        dataTvShowTitle = ((Fragment)mainView).getResources().getStringArray(R.array.tv_title);
        dataTvShowReleaseDate = ((Fragment)mainView).getResources().getStringArray(R.array.tv_release_date);
        dataTvShowDesc = ((Fragment)mainView).getResources().getStringArray(R.array.tv_desc);
        dataTvShowGenre = ((Fragment)mainView).getResources().getStringArray(R.array.tv_genre);
        dataTvShowStatus = ((Fragment)mainView).getResources().getStringArray(R.array.tv_release_status);
        dataTvShowScore = ((Fragment)mainView).getResources().getStringArray(R.array.tv_score);
        dataTvShowRuntime = ((Fragment)mainView).getResources().getStringArray(R.array.tv_runtime);
        dataTvShowTotalEps = ((Fragment)mainView).getResources().getStringArray(R.array.tv_total_episode);
        dataPoster = ((Fragment)mainView).getResources().obtainTypedArray(R.array.tv_image);
    }

    @Override
    public ArrayList<TvShow> addDataToList() {
        ArrayList<TvShow> tvShowsList = new ArrayList<>();
        for(int idx = 0; idx < dataTvShowTitle.length ; idx ++){
            TvShow tvShow = new TvShow();
            tvShow.setTitle(dataTvShowTitle[idx]);
            tvShow.setReleaseDate(dataTvShowReleaseDate[idx]);
            tvShow.setDesc(dataTvShowDesc[idx]);
            tvShow.setGenres(dataTvShowGenre[idx]);
            tvShow.setStatus(dataTvShowStatus[idx]);
            tvShow.setScore(Double.parseDouble(dataTvShowScore[idx]));
            tvShow.setRuntime(dataTvShowRuntime[idx]);
            tvShow.setImage(dataPoster.getResourceId(idx,-1));
            tvShowsList.add(tvShow);
        }
        return tvShowsList;
    }

    @Override
    public void navigateView(TvShow tvShowData) {
        Intent intent = new Intent(((Fragment)mainView).getContext(), DetailMovieActivity.class);
        intent.putExtra(DetailMovieActivity.EXTRA_MOVIE, tvShowData);
        ((Fragment) mainView).startActivity(intent);
    }
}
