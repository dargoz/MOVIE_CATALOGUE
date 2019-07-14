package com.dargoz.madesubmission;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.WindowManager;

import com.dargoz.madesubmission.main.movies.model.Movies;
import com.dargoz.madesubmission.repository.FilmImageRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Utils {
    public static int convertDpToPixel(float dp, Context context) {
        return (int) (dp * context.getResources().getDisplayMetrics().density);
    }

    public static String getObjectImageUrl(String url, String imageSize, String path){
        return  url + imageSize + path;
    }

    public static Bitmap getImageBitmap(Movies movie){
        Bitmap getImage = null;
        for(Bitmap image : FilmImageRepository.imageList){
            if(image.getGenerationId() == movie.getImageId()){
                getImage = image;
                break;
            }
        }
        return getImage;
    }

    public static String formatRuntime(int runtime){
        return String.format(Locale.getDefault(),"%dh %dm",runtime/60,runtime%60);
    }

    public static String formatDate(String dateString){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault());
        try {
            SimpleDateFormat newDateFormat = new SimpleDateFormat("dd MMM yyyy",Locale.getDefault());
            Date date = dateFormat.parse(dateString);
            return newDateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateString;
    }
}
