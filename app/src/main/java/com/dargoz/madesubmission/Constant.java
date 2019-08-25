package com.dargoz.madesubmission;

import android.content.Context;
import android.support.annotation.NonNull;


public class Constant {
    private static final String API_KEY = "043a5f6ab7599c142a42ec784d0aaf08";
    public static final String IMAGE_SIZE_W500 = "w500";
    public static final String IMAGE_URL = "https://image.tmdb.org/t/p/";
    public static String LOCAL_IMAGE_FILE_PATH = "imageDir";

    public static final String URL_TYPE_DISCOVER = "discover";
    public static final String URL_TYPE_SEARCH = "search";
    public static final String URL_TYPE_DETAIL = "detail";
    public static final String URL_TYPE_NEW_RELEASE = "release";
    public static final String URL_MOVIES = "movie";
    public static final String URL_TV = "tv";

    public static final String KEY_TOTAL_EPISODE = "number_of_episodes";
    public static final String MOVIES_KEY_RUNTIME = "runtime";
    public static final String TV_KEY_RUNTIME = "episode_run_time";

    public static final String GET_FAVORITE_MOVIES = "get favorite movie list";
    public static final String INSERT_ALL_MOVIES = "insert movie";
    public static final String DELETE_MOVIE = "Delete movie";
    public static final String FIND_MOVIE = "find movie by id";

    public static final String GET_FAVORITE_TV_SHOW = "get favorite tv show list";
    public static final String INSERT_ALL_TV_SHOW = "insert tv show";
    public static final String DELETE_TV_SHOW = "Delete tv show";
    public static final String FIND_TV_SHOW = "find tv show by id";

    public static final String INSERT = "insert";
    public static final String SUCCESS_INSERT = "success insert";
    public static final String FAILED_INSERT = "failed insert";
    public static final String SUCCESS_DELETE = "success delete";
    public static final String FAILED_DELETE = "failed delete";

    @NonNull
    public static String getUrlOf(@NonNull String type, String category , String keyword, Context context){
        switch (type){
            case URL_TYPE_DISCOVER :
                return "https://api.themoviedb.org/3/discover/"+ category
                        + "?language=" + context.getResources().getString(R.string.default_language)
                        + "&api_key=" + API_KEY;
            case URL_TYPE_SEARCH :
                return "https://api.themoviedb.org/3/search/"+ category
                        + "?api_key=" + API_KEY
                        + "&language=" + context.getResources().getString(R.string.default_language)
                        + "&query=" + keyword;
            case URL_TYPE_NEW_RELEASE:
                return "https://api.themoviedb.org/3/discover/movie"
                        + "?api_key=" + API_KEY
                        + "&primary_release_date.gte=" + keyword
                        + "&primary_release_date.lte=" + keyword;
            default:
                return "https://api.themoviedb.org/3/"+ category + "/" + keyword +
                        "?language=" + context.getResources().getString(R.string.default_language) +
                        "&api_key=" + Constant.API_KEY;
        }
    }

}
