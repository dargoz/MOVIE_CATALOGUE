package com.dargoz.madesubmission.detailmovielist;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dargoz.madesubmission.R;
import com.dargoz.madesubmission.customview.GenreTextView;
import com.dargoz.madesubmission.main.movies.model.Movies;

import java.util.ArrayList;

public class DetailMovieActivity extends AppCompatActivity implements DetailMovieContract.View {
    public static final String EXTRA_MOVIE = "movie";
    private DetailMovieContract.Presenter mPresenter;

    private ImageView moviePoster;
    private TextView titleText;
    private TextView descText;
    private LinearLayout genreGridView;
    private TextView statusReleaseText;
    private TextView scoreText;
    private TextView runtimeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        moviePoster = findViewById(R.id.movie_detail_image);
        titleText = findViewById(R.id.detail_title_text_view);
        descText = findViewById(R.id.desc_detail_text_view);
        genreGridView = findViewById(R.id.genre_grid_view);
        statusReleaseText = findViewById(R.id.status_text_view);
        scoreText = findViewById(R.id.score_text_view);
        runtimeText = findViewById(R.id.runtime_text_view);
        mPresenter = new DetailMoviePresenter(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        showMovieDetailInfo();
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void showMovieDetailInfo() {
        Movies movie = mPresenter.retrieveIntentMovieData(getIntent());

        moviePoster.setImageResource(movie.getImage());
        titleText.setText(movie.getTitle());
        descText.setText(movie.getDesc());

        ArrayList<String> genreList = mPresenter.getListGenre(movie.getGenres());
        for(String genre : genreList){
            GenreTextView genreText = new GenreTextView(this);
            genreText.setText(genre);
            genreGridView.addView(genreText);
        }

        statusReleaseText.setText(movie.getStatus());
        scoreText.setText(String.format("%.1f", movie.getScore()));
        runtimeText.setText(movie.getRuntime());

    }

    @Override
    public void setPresenter(DetailMovieContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
