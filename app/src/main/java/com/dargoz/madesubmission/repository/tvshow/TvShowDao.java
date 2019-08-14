package com.dargoz.madesubmission.repository.tvshow;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface TvShowDao {
    @Query("SELECT * FROM tvShow")
    List<TvShowEntity> getAll();

    @Insert
    void insertAll(TvShowEntity... tvShowEntities);

    @Delete
    void delete(TvShowEntity tvShowEntity);

    @Query("SELECT * FROM tvShow WHERE id = :id")
    TvShowEntity find(int id);

}
