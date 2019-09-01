package com.dargoz.myfavoritefilm;

import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.dargoz.myfavoritefilm.model.MovieItem;
import com.dargoz.myfavoritefilm.provider.DataAsync;
import com.dargoz.myfavoritefilm.provider.DataObserver;
import com.dargoz.myfavoritefilm.provider.LoadFilmCallback;

import java.util.ArrayList;

import static com.dargoz.myfavoritefilm.Constant.THREAD_NAME;
import static com.dargoz.myfavoritefilm.db.DatabaseContract.CONTENT_URI;

public class MainActivity extends AppCompatActivity implements LoadFilmCallback {
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycler_view);
        HandlerThread handlerThread = new HandlerThread(THREAD_NAME);
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());
        DataObserver myObserver = new DataObserver(handler, this);
        getContentResolver().registerContentObserver(CONTENT_URI, true, myObserver);
        new DataAsync(this, this).execute();
    }

    @Override
    public void postExecute(Cursor filmCursor) {
        ArrayList<MovieItem> movieItemArrayList = new ArrayList<>();
        while(filmCursor.moveToNext()){
            MovieItem movieItem = new MovieItem(filmCursor);
            Log.i("DRG","filmCursor : " + movieItem.getTitle());
            movieItemArrayList.add(movieItem);
        }
        FavoriteMovieRecyclerViewAdapter adapter = new FavoriteMovieRecyclerViewAdapter();
        adapter.setFavoriteMovieData(movieItemArrayList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}
