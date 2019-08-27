package com.dargoz.provider.db;

import android.database.Cursor;
import android.net.Uri;

import static com.dargoz.provider.FilmProvider.*;

import static com.dargoz.provider.db.MovieEntity.TABLE_NAME;


public final class DatabaseContract {
    private static final String SCHEME = "content";

    public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME)
            .authority(AUTHORITY)
            .appendPath(TABLE_NAME)
            .build();

}
    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }
}
