package com.dargoz.madesubmission.repository;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.dargoz.madesubmission.repository.movie.MovieEntity;
import com.dargoz.madesubmission.repository.movie.MovieDao;
import com.dargoz.madesubmission.repository.tvshow.TvShowDao;
import com.dargoz.madesubmission.repository.tvshow.TvShowEntity;

@Database(entities = {MovieEntity.class, TvShowEntity.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract MovieDao movieDao();
    public abstract TvShowDao tvShowDao();
}
