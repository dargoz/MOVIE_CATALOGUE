package com.dargoz.madesubmission.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Binder;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.dargoz.madesubmission.Constant;
import com.dargoz.madesubmission.MainActivity;
import com.dargoz.madesubmission.R;
import com.dargoz.madesubmission.Utils;
import com.dargoz.madesubmission.repository.movie.MovieEntity;

import java.util.ArrayList;
import java.util.List;

public class FavoriteStackRemoteViewFactory implements RemoteViewsService.RemoteViewsFactory {
    private final List<Bitmap> mWidgetItems = new ArrayList<>();
    private final Context mContext;

    FavoriteStackRemoteViewFactory(Context context) {
        mContext = context;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

        final long identityToken = Binder.clearCallingIdentity();

        //query DB
        List<MovieEntity> movieEntities = MainActivity.getDatabase().movieDao().getAll();
        for(MovieEntity movieEntity : movieEntities){
            mWidgetItems.add(
                    Utils.loadImageFromStorage(
                            mContext.getDir(Constant.LOCAL_IMAGE_FILE_PATH, Context.MODE_PRIVATE)
                                    .getPath(),
                            String.valueOf(movieEntity.getId())
                    )
            );
        }
        Binder.restoreCallingIdentity(identityToken);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return mWidgetItems.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.film_widget_item);
        rv.setImageViewBitmap(R.id.image_widget, mWidgetItems.get(position));

        Bundle extras = new Bundle();
        extras.putInt(FavoriteFilmWidget.EXTRA_ITEM, position);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);

        rv.setOnClickFillInIntent(R.id.image_widget, fillInIntent);
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
