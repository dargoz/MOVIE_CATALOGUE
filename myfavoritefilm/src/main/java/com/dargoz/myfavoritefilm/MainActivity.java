package com.dargoz.myfavoritefilm;

import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.dargoz.myfavoritefilm.provider.DataAsync;
import com.dargoz.myfavoritefilm.provider.DataObserver;
import com.dargoz.myfavoritefilm.provider.LoadFilmCallback;

import static com.dargoz.myfavoritefilm.Constant.*;
import static com.dargoz.myfavoritefilm.Constant.THREAD_NAME;
import static com.dargoz.myfavoritefilm.db.DatabaseContract.CONTENT_URI;

public class MainActivity extends AppCompatActivity implements LoadFilmCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        HandlerThread handlerThread = new HandlerThread(THREAD_NAME);
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());
        DataObserver myObserver = new DataObserver(handler, this);
        getContentResolver().registerContentObserver(CONTENT_URI, true, myObserver);
        new DataAsync(this, this).execute();
    }

    @Override
    public void postExecute(Cursor filmCursor) {
        while(filmCursor.moveToNext()){
            int id = filmCursor.getInt(filmCursor.getColumnIndexOrThrow(COLUMN_ID));
            String title = filmCursor.getString(filmCursor.getColumnIndexOrThrow(COLUMN_TITLE));
            Log.i("DRG","filmCursor : " + title);
            String description = filmCursor.getString(filmCursor.getColumnIndexOrThrow(COLUMN_DESC));
            String date = filmCursor.getString(filmCursor.getColumnIndexOrThrow(COLUMN_RELEASE_DATE));
        }
    }
}
