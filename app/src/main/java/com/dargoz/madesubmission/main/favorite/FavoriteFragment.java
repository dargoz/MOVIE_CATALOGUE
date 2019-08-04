package com.dargoz.madesubmission.main.favorite;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dargoz.madesubmission.R;
import com.dargoz.madesubmission.main.movies.model.Movies;
import com.dargoz.madesubmission.main.movies.model.MoviesViewModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment implements FavoriteContract.View {
    private FavoriteContract.Presenter mPresenter;
    private View rootView;
    private RecyclerView favoriteRecyclerView;
    private FavoriteViewModel favoriteViewModel;

    public FavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_favorite, container, false);
        initView();
        favoriteViewModel = ViewModelProviders.of(this).get(FavoriteViewModel.class);
        favoriteViewModel.getMovieList().observe(this,getMovie);
        mPresenter.prepareData(favoriteViewModel);
        return rootView;
    }

    private final Observer<ArrayList<Movies>> getMovie = new Observer<ArrayList<Movies>>() {
        @Override
        public void onChanged(@Nullable ArrayList<Movies> movies) {
            if(movies != null){
                Log.i("DRG","get movie observe : " + movies.toString());
                showFavoriteData(movies);
            }
        }
    };

    @Override
    public void setPresenter(FavoriteContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void initView() {
        mPresenter = new FavoritePresenter(this);
        favoriteRecyclerView = rootView.findViewById(R.id.favorite_item_recycler_view);
    }

    @Override
    public void showFavoriteData(ArrayList<Movies> moviesArrayList) {
        favoriteRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        FavoriteMovieRecyclerViewAdapter adapter =
                new FavoriteMovieRecyclerViewAdapter(getContext(), (FavoritePresenter) mPresenter);
        adapter.setFavoriteMovieData(moviesArrayList);
        favoriteRecyclerView.setAdapter(adapter);
    }
}
