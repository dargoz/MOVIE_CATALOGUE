package com.dargoz.madesubmission.main.movies;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.dargoz.madesubmission.R;
import com.dargoz.madesubmission.Utils;
import com.dargoz.madesubmission.main.movies.model.Movies;

import java.util.ArrayList;

public class MoviesRecyclerViewAdapter extends RecyclerView.Adapter<MoviesRecyclerViewAdapter.MovieViewHolder> {
    private Context context;
    private MoviesPresenter moviesPresenter;
    private ArrayList<Movies> moviesArrayList = new ArrayList<>();

    MoviesRecyclerViewAdapter(Context context, MoviesPresenter moviesPresenter){
        this.context = context;
        this.moviesPresenter = moviesPresenter;
    }

    public void setMovieData(ArrayList<Movies> movieList){
        moviesArrayList = movieList;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemRow = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item_layout,parent,false);
        return new MovieViewHolder(itemRow);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder movieViewHolder, int position) {
        movieViewHolder.bindData(moviesArrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return moviesArrayList.size();
    }

    protected class MovieViewHolder extends RecyclerView.ViewHolder{
        private final ImageView imageView;
        private final TextView movieTitleText;
        private final TextView movieReleaseDateText;
        private final TextView movieDescText;
        private final Button movieDetailPageButton;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.movie_image_view);
            movieTitleText = itemView.findViewById(R.id.movie_title_text_view);
            movieReleaseDateText = itemView.findViewById(R.id.movie_release_date_text_view);
            movieDescText = itemView.findViewById(R.id.movie_desc_text_view);
            movieDetailPageButton = itemView.findViewById(R.id.detail_button);
        }

        void bindData(final Movies movie){
            Glide.with(context)
                    .load(movie.getImage())
                    .apply(new RequestOptions()
                            .override(100,300)
                            .transform(new RoundedCorners(Utils.convertDpToPixel(10,context))))
                    .into(imageView);
            movieTitleText.setText(movie.getTitle());
            movieReleaseDateText.setText(movie.getReleaseDate());
            movieDescText.setText(movie.getDesc());

            movieDetailPageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    moviesPresenter.navigateView(movie);
                }
            });
        }
    }
}
