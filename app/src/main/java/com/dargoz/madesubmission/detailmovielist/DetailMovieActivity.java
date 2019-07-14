package com.dargoz.madesubmission.detailmovielist;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.dargoz.madesubmission.Constant;
import com.dargoz.madesubmission.R;
import com.dargoz.madesubmission.Utils;
import com.dargoz.madesubmission.customview.GenreTextView;
import com.dargoz.madesubmission.main.movies.model.Movies;
import com.dargoz.madesubmission.main.tvshow.model.TvShow;
import com.dargoz.madesubmission.main.movies.model.Genre;

import java.util.ArrayList;

public class DetailMovieActivity extends AppCompatActivity implements DetailMovieContract.View {

    public static final String EXTRA_MOVIE = "movie";
    public static final String EXTRA_TV_SHOWS = "tv";
    private DetailMovieContract.Presenter mPresenter;

    private ImageView moviePoster;
    private TextView episodeText;
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
        episodeText = findViewById(R.id.episode_text_view);
        titleText = findViewById(R.id.detail_title_text_view);
        descText = findViewById(R.id.desc_detail_text_view);
        genreGridView = findViewById(R.id.genre_container_view);
        statusReleaseText = findViewById(R.id.status_text_view);
        scoreText = findViewById(R.id.score_text_view);
        runtimeText = findViewById(R.id.runtime_text_view);
        AndroidNetworking.initialize(this);
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
        TvShow tvShow = mPresenter.retrieveIntentTvShowData(getIntent());
        String category = Constant.URL_MOVIES;
        if(tvShow == null) episodeText.setVisibility(View.GONE);
        else {
            movie = tvShow;
            category = Constant.URL_TV;
            episodeText.setText(String.format("Tv Shows | %s", tvShow.getTotalEpisode()));
        }

        mPresenter.prepareFilmDetails(movie,category);
        moviePoster.setImageBitmap(Utils.getImageBitmap(movie));
        titleText.setText(movie.getTitle());
        descText.setText(movie.getDesc());
        scoreText.setText(String.format("%.1f", movie.getScore()));

    }

    @Override
    public void showFilmDetailsData(Object filmData) {
        if(filmData instanceof TvShow){
            episodeText.setText(String.format("Tv Shows | %s Episode",
                    ((TvShow) filmData).getTotalEpisode()));
        }
        statusReleaseText.setText(((Movies)filmData).getStatus());
        runtimeText.setText(((Movies)filmData).getRuntime());
        showGenreList(((Movies)filmData).getGenres());

    }

    @Override
    public void setPresenter(DetailMovieContract.Presenter presenter) {
        mPresenter = presenter;
    }

    private void showGenreList(ArrayList<Genre> genreList){
        genreGridView.removeAllViews();
        LinearLayout row = new LinearLayout(this);
        for(int idx = 0; idx < genreList.size(); idx++){
            Log.i("DRG","genre "+ idx +": " + genreList.get(idx).getName());
            if (idx % 3 == 0){
                row = new LinearLayout(this);
                LinearLayout.LayoutParams layoutParams =
                        new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT);
                row.setLayoutParams(layoutParams);
            }
            GenreTextView genreText = new GenreTextView(this);
            genreText.setText(genreList.get(idx).getName());
            row.addView(genreText);
            if (idx % 3 == 0){
                genreGridView.addView(row);
            }
        }
    }
}
