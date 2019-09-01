package com.dargoz.myfavoritefilm.provider;

import android.database.Cursor;

public interface LoadFilmCallback {
    void postExecute(Cursor filmCursor);
}
