package com.dargoz.madesubmission.repository.movie;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.database.Cursor;

import java.util.List;

import static com.dargoz.madesubmission.utilities.Constant.*;

@Dao
public interface MovieDao {
    @Query("SELECT * FROM movies")
    List<MovieEntity> getAll();

    @Delete
    void delete(MovieEntity movies);

    @Query("SELECT * FROM movies WHERE id = :id")
    MovieEntity find(long id);

    @Query("SELECT * FROM movies")
    Cursor selectAll();

    @Insert
    long insertAll(MovieEntity movies);

    @Query("DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = :id")
    int deleteById(long id);

    @Query("SELECT * FROM movies WHERE id = :id")
    Cursor findById(long id);

    @Update
    int update(MovieEntity movieEntity);
}
