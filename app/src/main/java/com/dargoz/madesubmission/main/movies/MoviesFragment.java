package com.dargoz.madesubmission.main.movies;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.dargoz.madesubmission.R;
import com.dargoz.madesubmission.main.movies.model.Movies;
import com.dargoz.madesubmission.main.movies.model.MoviesViewModel;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;


public class MoviesFragment extends Fragment implements MoviesContract.View, View.OnClickListener {
    private MoviesContract.Presenter presenter;
    private RecyclerView moviesRecyclerView;
    private ShimmerFrameLayout shimmerFrameLayout;
    private Button reloadButton;

    private MoviesViewModel moviesViewModel;

    public MoviesViewModel getMoviesViewModel() {
        return moviesViewModel;
    }

    public MoviesContract.Presenter getPresenter() {
        return presenter;
    }

    public MoviesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_movie, container, false);
        moviesRecyclerView = root.findViewById(R.id.movie_recycler_view);
        shimmerFrameLayout = root.findViewById(R.id.shimmer_layout_movie);
        reloadButton = root.findViewById(R.id.movie_reload_button);

        reloadButton.setOnClickListener(this);
        shimmerFrameLayout.startShimmerAnimation();

        presenter = new MoviesPresenter(this, getContext());
        moviesViewModel = ViewModelProviders.of(this).get(MoviesViewModel.class);
        moviesViewModel.getMovieList().observe(this,getMovie);
        presenter.prepareData(moviesViewModel);
        return root;
    }

    private final Observer<ArrayList<Movies>> getMovie = new Observer<ArrayList<Movies>>() {
        @Override
        public void onChanged(@Nullable ArrayList<Movies> movies) {
            if(movies != null){
                showMovieList(movies);
            }
        }
    };

    @Override
    public void onPause() {
        super.onPause();
        shimmerFrameLayout.stopShimmerAnimation();
    }

    @Override
    public void showMovieList(ArrayList<Movies> moviesArrayList) {
        moviesRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        MoviesRecyclerViewAdapter moviesRecyclerViewAdapter =
                new MoviesRecyclerViewAdapter(getContext(), (MoviesPresenter) presenter);
        moviesRecyclerViewAdapter.setMovieData(moviesArrayList);

        moviesRecyclerView.setAdapter(moviesRecyclerViewAdapter);
        if(presenter.onAllDataFinishLoaded()) {
            shimmerFrameLayout.stopShimmerAnimation();
            shimmerFrameLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void showReloadButton(boolean state) {
        reloadButton.setVisibility(state ? View.VISIBLE : View.GONE);
    }

    @Override
    public void setPresenter(MoviesContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.movie_reload_button){
            presenter.prepareData(moviesViewModel);
            showReloadButton(false);
        }
    }
}
