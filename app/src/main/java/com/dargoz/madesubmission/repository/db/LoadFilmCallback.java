package com.dargoz.madesubmission.repository.db;

import android.database.Cursor;

public interface LoadFilmCallback {
    void preExecute();

    void postExecute(Cursor notes);
}
