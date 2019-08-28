package com.dargoz.madesubmission.favorite.tv;

import android.app.AlertDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dargoz.madesubmission.utilities.Constant;

import com.dargoz.madesubmission.R;
import com.dargoz.madesubmission.main.tvshow.model.TvShow;
import com.dargoz.madesubmission.repository.tvshow.TvDaoTask;
import com.dargoz.madesubmission.repository.tvshow.TvShowEntity;

import java.util.ArrayList;

public class FavoriteTvFragment extends Fragment implements FavoriteTvContract.View {
    private View rootView;
    private FavoriteTvContract.Presenter mPresenter;
    private FavoriteTvViewModel favoriteTvViewModel;
    private RecyclerView favoriteRecyclerView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_favorite, container, false);
        initView();
        favoriteTvViewModel = ViewModelProviders.of(this).get(FavoriteTvViewModel.class);
        favoriteTvViewModel.getTvShowList().observe(this,getTvShow);
        mPresenter.prepareData(favoriteTvViewModel);
        return rootView;
    }

    private final Observer<ArrayList<TvShow>> getTvShow = new Observer<ArrayList<TvShow>>() {
        @Override
        public void onChanged(@Nullable ArrayList<TvShow> tvShows) {
            if(tvShows != null){
                showFavoriteData(tvShows);
            }else{
                Toast.makeText(
                        getContext(),
                        getResources().getString(R.string.message_empty_favorite_list),
                        Toast.LENGTH_LONG
                ).show();
            }
        }
    };

    @Override
    public void initView() {
        mPresenter = new FavoriteTvPresenter(this, getContext());
        favoriteRecyclerView = rootView.findViewById(R.id.favorite_item_recycler_view);
    }

    @Override
    public void showFavoriteData(ArrayList<TvShow> tvShowArrayList) {
        favoriteRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        FavoriteTvRecyclerViewAdapter adapter =
                new FavoriteTvRecyclerViewAdapter(getContext(), (FavoriteTvPresenter) mPresenter);
        adapter.setFavoriteTvData(tvShowArrayList);
        favoriteRecyclerView.setAdapter(adapter);
    }

    @Override
    public void showAlertDialog(final TvShow tvShow) {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        alertDialog.setTitle(getResources().getString(R.string.alert_delete_title));
        alertDialog.setMessage(getResources().getString(R.string.delete_confirmation_message));
        alertDialog.setPositiveButton(getResources().getString(R.string.yes_button_text),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int id) {
                        TvShowEntity tvShowEntity =
                                new TvShowEntity(
                                        tvShow.getId(),
                                        tvShow.getTitle(),
                                        tvShow.getDesc(),
                                        tvShow.getGenres().toString(),
                                        tvShow.getReleaseDate(),
                                        tvShow.getStatus(),
                                        tvShow.getRuntime(),
                                        tvShow.getTotalEpisode(),
                                        tvShow.getScore()
                                );
                        TvDaoTask tvDaoTask = new TvDaoTask();
                        tvDaoTask.setTvShowEntities(tvShowEntity);
                        tvDaoTask.execute(Constant.DELETE_TV_SHOW);
                        mPresenter.prepareData(favoriteTvViewModel);
                    }
                });
        alertDialog.setNegativeButton(getResources().getString(R.string.no_button_text),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int id) {
                        dialogInterface.dismiss();
                    }
                });
        AlertDialog deleteAlert = alertDialog.create();
        deleteAlert.show();
    }


    @Override
    public void setPresenter(FavoriteTvContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
