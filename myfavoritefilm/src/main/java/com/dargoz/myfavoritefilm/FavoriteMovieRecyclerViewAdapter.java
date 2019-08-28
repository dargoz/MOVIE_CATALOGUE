package com.dargoz.myfavoritefilm;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dargoz.myfavoritefilm.model.MovieItem;
import com.dargoz.myfavoritefilm.provider.Utils;

import java.util.ArrayList;

public class FavoriteMovieRecyclerViewAdapter extends RecyclerView.Adapter<FavoriteMovieRecyclerViewAdapter.FavoriteMovieViewHolder> {

    private ArrayList<MovieItem> moviesArrayList = new ArrayList<>();

    FavoriteMovieRecyclerViewAdapter( ){

    }

    void setFavoriteMovieData (ArrayList<MovieItem> movieList) { moviesArrayList = movieList; }

    @NonNull
    @Override
    public FavoriteMovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemRow = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_film_item,parent,false);
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
        private final TextView movieTitleText;
        private final TextView movieReleaseDateText;
        private final TextView genreText;
        private final TextView descText;
        private final TextView movieScore;
        private final TextView movieDuration;

        FavoriteMovieViewHolder(@NonNull View itemView) {
            super(itemView);
            movieTitleText = itemView.findViewById(R.id.favorite_item_title);
            genreText = itemView.findViewById(R.id.favorite_genre_text);
            descText = itemView.findViewById(R.id.favorite_desc_text);
            movieReleaseDateText = itemView.findViewById(R.id.favorite_release_date);
            movieScore = itemView.findViewById(R.id.favorite_item_score_text_view);
            movieDuration = itemView.findViewById(R.id.favorite_item_duration_text_view);
        }

        void bindData(final MovieItem movie){
            movieTitleText.setText(movie.getTitle());
            movieReleaseDateText.setText(Utils.formatDate(movie.getReleaseDate()));
            genreText.setText(movie.getGenre());
            descText.setText(movie.getDesc());
            movieScore.setText(String.valueOf(movie.getScore()));
            movieDuration.setText(movie.getRuntime());
        }
    }
}
