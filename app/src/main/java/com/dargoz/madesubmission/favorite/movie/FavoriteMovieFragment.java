package com.dargoz.madesubmission.favorite.movie;


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
import com.dargoz.madesubmission.utilities.Utils;
import com.dargoz.madesubmission.main.movies.model.Movies;
import com.dargoz.madesubmission.repository.movie.MovieDaoTask;
import com.dargoz.madesubmission.repository.movie.MovieEntity;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteMovieFragment extends Fragment implements FavoriteMovieContract.View {
    private FavoriteMovieContract.Presenter mPresenter;
    private FavoriteMovieViewModel favoriteMovieViewModel;
    private View rootView;
    private RecyclerView favoriteRecyclerView;

    public FavoriteMovieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_favorite, container, false);
        initView();
        favoriteMovieViewModel = ViewModelProviders.of(this).get(FavoriteMovieViewModel.class);
        favoriteMovieViewModel.getMovieList().observe(this,getMovie);
        mPresenter.prepareData(favoriteMovieViewModel);
        return rootView;
    }

    private final Observer<ArrayList<Movies>> getMovie = new Observer<ArrayList<Movies>>() {
        @Override
        public void onChanged(@Nullable ArrayList<Movies> movies) {
            if(movies != null){
                showFavoriteData(movies);
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
    public void setPresenter(FavoriteMovieContract.Presenter presenter) {
        mPresenter = presenter;
    }


    @Override
    public void initView() {
        mPresenter = new FavoriteMoviePresenter(this, getContext());
        favoriteRecyclerView = rootView.findViewById(R.id.favorite_item_recycler_view);
    }

    @Override
    public void showFavoriteData(ArrayList<Movies> moviesArrayList) {
        favoriteRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        FavoriteMovieRecyclerViewAdapter adapter =
                new FavoriteMovieRecyclerViewAdapter(getContext(), (FavoriteMoviePresenter) mPresenter);
        adapter.setFavoriteMovieData(moviesArrayList);
        favoriteRecyclerView.setAdapter(adapter);
    }

    @Override
    public void showAlertDialog(final Movies movie) {
        final AlertDialog.Builder alertDialog =
                new AlertDialog.Builder(getContext())
                        .setTitle(getResources().getString(R.string.alert_delete_title))
                        .setMessage(getResources().getString(R.string.delete_confirmation_message))
                        .setPositiveButton(getResources().getString(R.string.yes_button_text),
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int id) {
                                        MovieEntity movieEntity =
                                                new MovieEntity(
                                                        movie.getId(),
                                                        movie.getTitle(),
                                                        movie.getDesc(),
                                                        movie.getGenres().toString(),
                                                        movie.getReleaseDate(),
                                                        movie.getStatus(),
                                                        movie.getRuntime(),
                                                        movie.getScore()
                                                );
                                        MovieDaoTask movieDaoTask = new MovieDaoTask();
                                        movieDaoTask.setMovieEntities(movieEntity);
                                        movieDaoTask.execute(Constant.DELETE_MOVIE);
                                        mPresenter.prepareData(favoriteMovieViewModel);
                                        Utils.updateWidget(getContext());
                                    }
                                })
                        .setNegativeButton(getResources().getString(R.string.no_button_text),
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int id) {
                                        dialogInterface.dismiss();
                                    }
                                });
        AlertDialog deleteAlert = alertDialog.create();
        deleteAlert.show();
    }
}
