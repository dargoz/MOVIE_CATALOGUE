package com.dargoz.madesubmission;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;

import com.dargoz.madesubmission.main.movies.model.Movies;
import com.dargoz.madesubmission.repository.FilmImageRepository;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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

    public static String saveToInternalStorage( Context context, Bitmap bitmapImage, String filename){
        ContextWrapper cw = new ContextWrapper(context);

        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File myPath = new File(directory,filename);

        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(myPath );
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(fileOutputStream != null)
                    fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
    }
}
