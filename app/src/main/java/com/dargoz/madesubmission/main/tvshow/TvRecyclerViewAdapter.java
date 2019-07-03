package com.dargoz.madesubmission.main.tvshow;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

public class TvRecyclerViewAdapter extends RecyclerView.Adapter<TvRecyclerViewAdapter.TvViewHolder> {

    @NonNull
    @Override
    public TvViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull TvViewHolder tvViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    protected class TvViewHolder extends RecyclerView.ViewHolder{

        public TvViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
