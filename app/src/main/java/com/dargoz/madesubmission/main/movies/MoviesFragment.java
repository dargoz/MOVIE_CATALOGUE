package com.dargoz.madesubmission.main.movies;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dargoz.madesubmission.R;
import com.dargoz.madesubmission.main.movies.model.Movies;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoviesFragment extends Fragment implements MoviesContract.View {
    private MoviesContract.Presenter mPresenter;
    private RecyclerView moviesRecyclerView;

    public MoviesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_movie, container, false);
        moviesRecyclerView = root.findViewById(R.id.movie_recycler_view);
        mPresenter = new MoviesPresenter(this, getContext());
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.prepareData();
    }

    @Override
    public void showMovieList() {
        moviesRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        MoviesRecyclerViewAdapter moviesRecyclerViewAdapter =
                new MoviesRecyclerViewAdapter(getContext(), (MoviesPresenter) mPresenter);
        moviesRecyclerViewAdapter.setMovieData(mPresenter.addDataToList());

        moviesRecyclerView.setAdapter(moviesRecyclerViewAdapter);
    }

    @Override
    public void setPresenter(MoviesContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
