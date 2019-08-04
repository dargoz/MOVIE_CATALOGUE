package com.dargoz.madesubmission.main.favorite;

import android.content.Context;
import android.graphics.Movie;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dargoz.madesubmission.R;
import com.dargoz.madesubmission.Utils;
import com.dargoz.madesubmission.main.movies.MoviesRecyclerViewAdapter;
import com.dargoz.madesubmission.main.movies.model.Movies;

import java.util.ArrayList;

public class FavoriteMovieRecyclerViewAdapter extends RecyclerView.Adapter<FavoriteMovieRecyclerViewAdapter.FavoriteMovieViewHolder> {
    private final Context context;
    private final FavoritePresenter favoritePresenter;
    private ArrayList<Movies> moviesArrayList = new ArrayList<>();

    FavoriteMovieRecyclerViewAdapter(Context context, FavoritePresenter favoritePresenter){
        this.context = context;
        this.favoritePresenter = favoritePresenter;
    }

    public void setFavoriteMovieData (ArrayList<Movies> movieList) { moviesArrayList = movieList; }

    @NonNull
    @Override
    public FavoriteMovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemRow = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_item_layout,parent,false);
        return new FavoriteMovieViewHolder(itemRow);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteMovieViewHolder favoriteMovieViewHolder, int position) {
        favoriteMovieViewHolder.bindData(moviesArrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return moviesArrayList.size();
    }

    class FavoriteMovieViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageView;
        private final TextView movieTitleText;
        private final TextView movieReleaseDateText;
        private final TextView movieScore;
        private final TextView movieDuration;

        FavoriteMovieViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.favorite_item_image);
            movieTitleText = itemView.findViewById(R.id.favorite_item_title);
            movieReleaseDateText = itemView.findViewById(R.id.favorite_release_date);
            movieScore = itemView.findViewById(R.id.favorite_item_score_text_view);
            movieDuration = itemView.findViewById(R.id.favorite_item_duration_text_view);
        }

        void bindData(Movies movie){
            imageView.setImageBitmap(Utils.getImageBitmap(movie));
            movieTitleText.setText(movie.getTitle());
            movieReleaseDateText.setText(Utils.formatDate(movie.getReleaseDate()));
            movieScore.setText(String.valueOf(movie.getScore()));
            movieDuration.setText(movie.getRuntime());
        }
    }
}
