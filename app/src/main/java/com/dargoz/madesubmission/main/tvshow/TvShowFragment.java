package com.dargoz.madesubmission.main.tvshow;


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
import com.dargoz.madesubmission.main.tvshow.model.TvShow;
import com.dargoz.madesubmission.main.tvshow.model.TvShowViewModel;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;


public class TvShowFragment extends Fragment implements TvShowContract.View, View.OnClickListener {
    private TvShowContract.Presenter presenter;
    private RecyclerView tvRecyclerView;
    private ShimmerFrameLayout shimmerFrameLayout;
    private Button reloadButton;

    private TvShowViewModel tvShowViewModel;

    public TvShowViewModel getTvShowViewModel() {
        return tvShowViewModel;
    }

    public TvShowContract.Presenter getPresenter() {
        return presenter;
    }

    public TvShowFragment() {
        presenter = new TvShowPresenter(this);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_tv_show, container, false);
        tvRecyclerView = root.findViewById(R.id.tv_recycler_view);
        shimmerFrameLayout = root.findViewById(R.id.shimmer_layout_tv);
        reloadButton = root.findViewById(R.id.tv_reload_button);

        shimmerFrameLayout.startShimmer();
        reloadButton.setOnClickListener(this);
        tvShowViewModel = ViewModelProviders.of(this).get(TvShowViewModel.class);
        tvShowViewModel.getTvShowList().observe(this, getTvShow);
        presenter.prepareData(tvShowViewModel);
        return root;
    }

    private final Observer<ArrayList<TvShow>> getTvShow = new Observer<ArrayList<TvShow>>() {
        @Override
        public void onChanged(@Nullable ArrayList<TvShow> tvShows) {
            if (tvShows != null){
                showTvList(tvShows);
            }
        }
    };

    @Override
    public void showTvList(ArrayList<TvShow> tvShowArrayList) {
        tvRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        TvRecyclerViewAdapter adapter = new TvRecyclerViewAdapter(getContext(), (TvShowPresenter) presenter);
        adapter.setTvData(tvShowArrayList);
        tvRecyclerView.setAdapter(adapter);

        if(presenter.onAllDataFinishLoaded()){
            shimmerFrameLayout.stopShimmer();
            shimmerFrameLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void showReloadButton(boolean state) {
        reloadButton.setVisibility(state ? View.VISIBLE : View.GONE);
    }

    @Override
    public void setPresenter(TvShowContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.tv_reload_button){
            presenter.prepareData(tvShowViewModel);
            showReloadButton(false);
        }
    }
}
