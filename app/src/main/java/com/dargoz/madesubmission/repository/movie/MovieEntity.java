package com.dargoz.madesubmission.repository.movie;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.content.ContentValues;

@Entity(tableName = "movies")
public class MovieEntity {

    @PrimaryKey
    @ColumnInfo(name = "id")
    private final int id;

    @ColumnInfo(name = "title")
    private final String title;

    @ColumnInfo(name = "desc")
    private final String desc;

    @ColumnInfo(name = "genre")
    private final String genre;

    @ColumnInfo(name = "release_date")
    private final String releaseDate;

    @ColumnInfo(name = "status")
    private final String status;

    @ColumnInfo(name = "runtime")
    private final String runtime;

    @ColumnInfo(name = "score")
    private final double score;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }

    public String getGenre() {
        return genre;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getStatus() {
        return status;
    }

    public String getRuntime() {
        return runtime;
    }

    public double getScore() {
        return score;
    }

    public MovieEntity(int id, String title, String desc, String genre,
                       String releaseDate, String status, String runtime,
                       double score
                       ) {
        this.id = id;
        this.title = title;
        this.desc = desc;
        this.genre = genre;
        this.releaseDate = releaseDate;
        this.status = status;
        this.runtime = runtime;
        this.score = score;
    }
}
