package com.dargoz.madesubmission.repository.movie;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;


import java.util.List;

@Dao
public interface MovieDao {
    @Query("SELECT * FROM movies")
    List<MovieEntity> getAll();

    @Insert
    void insertAll(MovieEntity... movies);

    @Delete
    void delete(MovieEntity movies);

    @Query("SELECT * FROM movies WHERE id = :id")
    MovieEntity find(int id);
}
