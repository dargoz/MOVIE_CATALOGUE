package com.dargoz.madesubmission.main.tvshow;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dargoz.madesubmission.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TvShowFragment extends Fragment implements TvShowContract.View {
    private TvShowContract.Presenter mPresenter;
    private RecyclerView tvRecyclerView;
    public TvShowFragment() {
        // Required empty public constructor
        mPresenter = new TvShowPresenter(this);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_tv_show, container, false);
        tvRecyclerView = root.findViewById(R.id.tv_recycler_view);
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.prepareData();
    }

    @Override
    public void showTvList() {
        tvRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        TvRecyclerViewAdapter adapter = new TvRecyclerViewAdapter(getContext(), (TvShowPresenter) mPresenter);
        adapter.setTvData(mPresenter.addDataToList());
        tvRecyclerView.setAdapter(adapter);
    }

    @Override
    public void setPresenter(TvShowContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
