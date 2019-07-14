package com.dargoz.madesubmission;

import android.content.Context;
import android.support.annotation.NonNull;

public class Constant {
    public static final String API_KEY = "043a5f6ab7599c142a42ec784d0aaf08";
    public static final String IMAGE_SIZE_W500 = "w500";
    public static final String IMAGE_URL = "https://image.tmdb.org/t/p/";

    public static final String URL_TYPE_DISCOVER = "discover";
    public static final String URL_TYPE_DETAIL = "detail";
    public static final String URL_MOVIES = "movie";
    public static final String URL_TV = "tv";

    public static final String KEY_OVERVIEW = "overview";
    public static final String MOVIES_KEY_TITLE = "title";
    public static final String KEY_TOTAL_EPISODE = "number_of_episodes";
    public static final String MOVIES_KEY_RUNTIME = "runtime";
    public static final String TV_KEY_RUNTIME = "episode_run_time";

    @NonNull
    public static String getUrlOf(String type, String category , int movieId, Context context){
        return type.equals(URL_TYPE_DISCOVER) ?
                "https://api.themoviedb.org/3/discover/"+ category +
                        "?language=" + context.getResources().getString(R.string.default_language) +
                        "&api_key=" + Constant.API_KEY :
                "https://api.themoviedb.org/3/"+ category + "/" + movieId +
                        "?language=" + context.getResources().getString(R.string.default_language) +
                        "&api_key=" + Constant.API_KEY;
    }
}
