package com.dargoz.myfavoritefilm.db;

import android.database.Cursor;
import android.net.Uri;

import static com.dargoz.myfavoritefilm.Constant.TABLE_NAME;

public class DatabaseContract {
    private static final String SCHEME = "content";
    private static final String AUTHORITY = "com.dargoz.madesubmission";

    public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME)
            .authority(AUTHORITY)
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
