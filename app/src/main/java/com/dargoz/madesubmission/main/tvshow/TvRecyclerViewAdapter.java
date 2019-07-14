package com.dargoz.madesubmission.main.tvshow;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.dargoz.madesubmission.R;
import com.dargoz.madesubmission.Utils;
import com.dargoz.madesubmission.main.tvshow.model.TvShow;

import java.util.ArrayList;

public class TvRecyclerViewAdapter extends RecyclerView.Adapter<TvRecyclerViewAdapter.TvViewHolder> {
    private final Context context;
    private final TvShowPresenter tvShowPresenter;
    private ArrayList<TvShow> tvShowsList = new ArrayList<>();

    TvRecyclerViewAdapter(Context context, TvShowPresenter tvShowPresenter){
        this.context = context;
        this.tvShowPresenter = tvShowPresenter;
    }

    public void setTvData(ArrayList<TvShow> list){
        tvShowsList = list;
    }

    @NonNull
    @Override
    public TvViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View itemRow = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item_layout,parent,false);
        return new TvViewHolder(itemRow);
    }

    @Override
    public void onBindViewHolder(@NonNull TvViewHolder tvViewHolder, int position) {
        tvViewHolder.bindData(tvShowsList.get(position));
    }

    @Override
    public int getItemCount() {
        return tvShowsList.size();
    }

    protected class TvViewHolder extends RecyclerView.ViewHolder{
        private final ImageView imageView;
        private final TextView movieTitleText;
        private final TextView movieReleaseDateText;
        private final FrameLayout imageContainer;
        private final TextView scoreText;

        TvViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.movie_image_view);
            movieTitleText = itemView.findViewById(R.id.movie_title_text_view);
            movieReleaseDateText = itemView.findViewById(R.id.movie_release_date_text_view);
            imageContainer = itemView.findViewById(R.id.movie_image_container);
            scoreText = itemView.findViewById(R.id.rating_text_view);
        }

        @SuppressLint("DefaultLocale")
        void bindData(final TvShow tvShow){
            Glide.with(context)
                    .load(Utils.getImageBitmap(tvShow))
                    .apply(new RequestOptions()
                            .override(context
                                            .getResources()
                                            .getDimensionPixelSize(R.dimen.main_poster_width),
                                    context
                                            .getResources()
                                            .getDimensionPixelSize(R.dimen.main_poster_height))
                            .transform(new RoundedCorners(context
                                    .getResources()
                                    .getDimensionPixelSize(R.dimen.poster_corner))))
                    .into(imageView);
            movieTitleText.setText(tvShow.getTitle());
            movieReleaseDateText.setText(Utils.formatDate(tvShow.getReleaseDate()));
            scoreText.setText(String.format("%.1f", tvShow.getScore()));

            imageContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    tvShowPresenter.navigateView(tvShow);
                }
            });
        }
    }
}
