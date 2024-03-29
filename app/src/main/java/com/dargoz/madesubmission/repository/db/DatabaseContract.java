package com.dargoz.madesubmission.repository.db;

import android.database.Cursor;
import android.net.Uri;

import com.dargoz.madesubmission.repository.provider.FilmProvider;

import static com.dargoz.madesubmission.utilities.Constant.TABLE_NAME;


public final class DatabaseContract {
    private static final String SCHEME = "content";

    public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME)
            .authority(FilmProvider.AUTHORITY)
            .appendPath(TABLE_NAME)
            .build();

    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }

    public static long getColumnLong(Cursor cursor, String columnName) {
        return cursor.getLong(cursor.getColumnIndex(columnName));
    }
}
