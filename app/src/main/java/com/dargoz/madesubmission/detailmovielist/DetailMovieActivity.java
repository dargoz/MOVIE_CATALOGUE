package com.dargoz.madesubmission.detailmovielist;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.dargoz.madesubmission.Constant;
import com.dargoz.madesubmission.R;
import com.dargoz.madesubmission.Utils;
import com.dargoz.madesubmission.customview.GenreTextView;
import com.dargoz.madesubmission.main.movies.model.Genre;
import com.dargoz.madesubmission.main.movies.model.Movies;
import com.dargoz.madesubmission.main.tvshow.model.TvShow;
import com.dargoz.madesubmission.repository.movie.MovieDaoTask;
import com.dargoz.madesubmission.repository.movie.MovieEntity;
import com.dargoz.madesubmission.repository.tvshow.TvDaoTask;
import com.dargoz.madesubmission.repository.tvshow.TvShowEntity;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;
import java.util.List;

public class DetailMovieActivity extends AppCompatActivity implements DetailMovieContract.View, View.OnClickListener {

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
    private ImageView favoriteButton;

    private ShimmerFrameLayout shimmerLayout;
    private ScrollView scrollView;

    private Movies movieData;
    private Movies movie;
    private TvShow tvShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);
        setTitle(R.string.detail_film_title);
        AndroidNetworking.initialize(this);
        mPresenter = new DetailMoviePresenter(this);
        initView();

    }
    @Override
    public void initView() {
        movie = mPresenter.retrieveIntentMovieData(getIntent());
        tvShow = mPresenter.retrieveIntentTvShowData(getIntent());
        moviePoster = findViewById(R.id.movie_detail_image);
        episodeText = findViewById(R.id.episode_text_view);
        titleText = findViewById(R.id.detail_title_text_view);
        descText = findViewById(R.id.desc_detail_text_view);
        genreGridView = findViewById(R.id.genre_container_view);
        statusReleaseText = findViewById(R.id.status_text_view);
        scoreText = findViewById(R.id.score_text_view);
        runtimeText = findViewById(R.id.runtime_text_view);
        shimmerLayout = findViewById(R.id.shimmer_layout_detail_page);
        scrollView = findViewById(R.id.detail_page_scroll_view);
        favoriteButton = findViewById(R.id.favorite_button);
        favoriteButton.setOnClickListener(this);
        if (tvShow == null) {
            favoriteButton.setImageResource(checkMovieOnFavoriteList(movie) ?
                    R.drawable.baseline_favorite_white_36 : R.drawable.baseline_favorite_border_white_36);
        }else
            favoriteButton.setImageResource(checkTvOnFavoriteList(tvShow) ?
                    R.drawable.baseline_favorite_white_36 : R.drawable.baseline_favorite_border_white_36);
    }


    private boolean checkMovieOnFavoriteList(Movies movie) {
        MovieDaoTask task = new MovieDaoTask();
        try {
            int findId = 0;
            task.setId(movie.getId());
            List<MovieEntity> findEntity = task.execute(Constant.FIND_MOVIE).get();
            if (!findEntity.isEmpty())
                findId = findEntity.get(0).getId();

            return movie.getId() == findId;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean checkTvOnFavoriteList(TvShow tvShow) {
        TvDaoTask task = new TvDaoTask();
        try {
            int findId = 0;
            task.setId(tvShow.getId());
            List<TvShowEntity> findTvEntity = task.execute(Constant.FIND_TV_SHOW).get();
            if (!findTvEntity.isEmpty())
                findId = findTvEntity.get(0).getId();

            return tvShow.getId() == findId;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        showLoading(true);
        showMovieDetailInfo();
    }


    @SuppressLint("DefaultLocale")
    @Override
    public void showMovieDetailInfo() {
        Movies movie = mPresenter.retrieveIntentMovieData(getIntent());
        TvShow tvShow = mPresenter.retrieveIntentTvShowData(getIntent());
        String category = Constant.URL_MOVIES;
        if (tvShow == null) episodeText.setVisibility(View.GONE);
        else {
            movie = tvShow;
            category = Constant.URL_TV;
            episodeText.setText(String.format("Tv Shows | %s", tvShow.getTotalEpisode()));
        }

        mPresenter.prepareFilmDetails(movie, category);
        Bitmap image = Utils.loadImageFromStorage(
                getDir(Constant.LOCAL_IMAGE_FILE_PATH, Context.MODE_PRIVATE).getPath(),
                String.valueOf(movie.getId())
        );
        moviePoster.setImageBitmap(image != null ? image : Utils.getImageBitmap(movie));
        titleText.setText(movie.getTitle());
        descText.setText(movie.getDesc());
        scoreText.setText(String.format("%.1f", movie.getScore()));

    }

    @Override
    public void showFilmDetailsData(Object filmData) {
        if (filmData instanceof TvShow) {
            episodeText.setText(String.format("Tv Shows | %s Episode",
                    ((TvShow) filmData).getTotalEpisode()));
        }
        statusReleaseText.setText(((Movies) filmData).getStatus());
        runtimeText.setText(((Movies) filmData).getRuntime());
        showGenreList(((Movies) filmData).getGenres());
        showLoading(false);
        movieData = (Movies) filmData;
    }

    @Override
    public void showLoading(boolean state) {
        if (state) {
            shimmerLayout.startShimmerAnimation();
            scrollView.setVisibility(View.GONE);
            shimmerLayout.setVisibility(View.VISIBLE);
        } else {
            scrollView.setVisibility(View.VISIBLE);
            shimmerLayout.stopShimmerAnimation();
            shimmerLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void showToastMessage(String message) {
        message = message.contains(Constant.INSERT) ?
                message.equals(Constant.SUCCESS_INSERT) ?
                        getResources().getString(R.string.success_add_to_favorite_text) :
                        getResources().getString(R.string.error_add_favorite_text)
                : message.equals(Constant.SUCCESS_DELETE) ?
                getResources().getString(R.string.success_remove_from_favorite_text) :
                getResources().getString(R.string.error_remove_from_favorite_text);
        Toast.makeText(
                this,
                message,
                Toast.LENGTH_LONG
        ).show();
    }

    @Override
    public void updateButtonImageState(boolean flag) {
        favoriteButton.setImageResource(flag ?
                R.drawable.baseline_favorite_white_36 :
                R.drawable.baseline_favorite_border_white_36
        );
    }

    @Override
    public void setPresenter(DetailMovieContract.Presenter presenter) {
        mPresenter = presenter;
    }

    private void showGenreList(ArrayList<Genre> genreList) {
        genreGridView.removeAllViews();
        LinearLayout row = new LinearLayout(this);
        for (int idx = 0; idx < genreList.size(); idx++) {
            if (idx % 3 == 0) {
                row = new LinearLayout(this);
                LinearLayout.LayoutParams layoutParams =
                        new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT);
                row.setLayoutParams(layoutParams);
            }
            GenreTextView genreText = new GenreTextView(this);
            genreText.setText(genreList.get(idx).getName());
            row.addView(genreText);
            if (idx % 3 == 0) {
                genreGridView.addView(row);
            }
        }
    }

    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.favorite_button){
            if (tvShow == null){
                if(checkMovieOnFavoriteList(movie)){
                    mPresenter.removeFromMovieFavorite(movie,movieData);
                }else{
                   mPresenter.addToMovieFavorite(movie,movieData);
                }
            }
            else {
                if (checkTvOnFavoriteList(tvShow)) {
                    mPresenter.removeFromTvFavorite(tvShow,movieData);
                }else{
                    mPresenter.addToTvFavorite(tvShow,movieData);
                }
            }

        }
    }
}
