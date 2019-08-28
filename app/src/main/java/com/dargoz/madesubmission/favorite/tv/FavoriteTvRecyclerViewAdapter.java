package com.dargoz.madesubmission.favorite.tv;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dargoz.madesubmission.utilities.Constant;
import com.dargoz.madesubmission.R;
import com.dargoz.madesubmission.utilities.Utils;
import com.dargoz.madesubmission.main.tvshow.model.TvShow;

import java.util.ArrayList;

public class FavoriteTvRecyclerViewAdapter extends RecyclerView.Adapter<FavoriteTvRecyclerViewAdapter.FavoriteTvViewHolder> {
    private final Context context;
    private final FavoriteTvPresenter favoriteTvPresenter;
    private ArrayList<TvShow> tvArrayList = new ArrayList<>();

    FavoriteTvRecyclerViewAdapter(Context context, FavoriteTvPresenter favoriteTvPresenter) {
        this.context = context;
        this.favoriteTvPresenter = favoriteTvPresenter;
    }

    void setFavoriteTvData(ArrayList<TvShow> tvList) {
        tvArrayList = tvList;
    }

    @NonNull
    @Override
    public FavoriteTvRecyclerViewAdapter.FavoriteTvViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemRow = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_item_layout, parent, false);
        return new FavoriteTvRecyclerViewAdapter.FavoriteTvViewHolder(itemRow);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteTvRecyclerViewAdapter.FavoriteTvViewHolder favoriteMovieViewHolder, int position) {
        favoriteMovieViewHolder.bindData(tvArrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return tvArrayList.size();
    }

    class FavoriteTvViewHolder extends RecyclerView.ViewHolder {
        private final ConstraintLayout rowLayout;
        private final ImageView imageView;
        private final TextView tvShowTitleText;
        private final TextView tvShowReleaseDateText;
        private final TextView tvShowScore;
        private final TextView tvShowDuration;

        FavoriteTvViewHolder(@NonNull View itemView) {
            super(itemView);
            rowLayout = itemView.findViewById(R.id.favorite_item_layout);
            imageView = itemView.findViewById(R.id.favorite_item_image);
            tvShowTitleText = itemView.findViewById(R.id.favorite_item_title);
            tvShowReleaseDateText = itemView.findViewById(R.id.favorite_release_date);
            tvShowScore = itemView.findViewById(R.id.favorite_item_score_text_view);
            tvShowDuration = itemView.findViewById(R.id.favorite_item_duration_text_view);
        }

        void bindData(final TvShow tvShow) {
            Bitmap image = Utils.loadImageFromStorage(
                    context.getDir(Constant.LOCAL_IMAGE_FILE_PATH, Context.MODE_PRIVATE).getPath(),
                    String.valueOf(tvShow.getId())
            );
            imageView.setImageBitmap(image != null ? image : Utils.getImageBitmap(tvShow));
            tvShowTitleText.setText(tvShow.getTitle());
            tvShowReleaseDateText.setText(Utils.formatDate(tvShow.getReleaseDate()));
            tvShowScore.setText(String.valueOf(tvShow.getScore()));
            tvShowDuration.setText(tvShow.getRuntime());
            rowLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    favoriteTvPresenter.navigateView(tvShow);
                }
            });
            rowLayout.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    favoriteTvPresenter.getView().showAlertDialog(tvShow);
                    return false;
                }
            });
        }
    }
}
