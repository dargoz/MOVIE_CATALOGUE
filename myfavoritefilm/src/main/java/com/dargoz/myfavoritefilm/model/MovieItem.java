package com.dargoz.myfavoritefilm.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.dargoz.myfavoritefilm.db.DatabaseContract;

import static com.dargoz.myfavoritefilm.Constant.COLUMN_DESC;
import static com.dargoz.myfavoritefilm.Constant.COLUMN_GENRE;
import static com.dargoz.myfavoritefilm.Constant.COLUMN_ID;
import static com.dargoz.myfavoritefilm.Constant.COLUMN_RELEASE_DATE;
import static com.dargoz.myfavoritefilm.Constant.COLUMN_RUNTIME;
import static com.dargoz.myfavoritefilm.Constant.COLUMN_SCORE;
import static com.dargoz.myfavoritefilm.Constant.COLUMN_STATUS;
import static com.dargoz.myfavoritefilm.Constant.COLUMN_TITLE;

public class MovieItem implements Parcelable {
    private long id;
    private final String title;
    private final String desc;
    private final String genre;
    private final String releaseDate;
    private final String status;
    private final String runtime;
    private final double score;

    public long getId() {
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

    public void setId(long id){
        this.id = id;
    }

    public MovieItem(long id, String title, String desc, String genre,
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

    public MovieItem(ContentValues values){
        this.id = values.containsKey(COLUMN_ID) ? values.getAsLong(COLUMN_ID) : 0;
        this.title = values.containsKey(COLUMN_TITLE) ? values.getAsString(COLUMN_TITLE) : "";
        this.desc =values.containsKey(COLUMN_DESC) ? values.getAsString(COLUMN_DESC) : "";
        this.genre =values.containsKey(COLUMN_GENRE) ? values.getAsString(COLUMN_GENRE) : "";
        this.releaseDate =values.containsKey(COLUMN_RELEASE_DATE) ? values.getAsString(COLUMN_RELEASE_DATE) : "";
        this.status =values.containsKey(COLUMN_STATUS) ? values.getAsString(COLUMN_STATUS) : "";
        this.runtime =values.containsKey(COLUMN_RUNTIME) ? values.getAsString(COLUMN_TITLE) : "";
        this.score =values.containsKey(COLUMN_SCORE) ? values.getAsDouble(COLUMN_SCORE) : 0.0;
    }

    public MovieItem(Cursor cursor){
        this.id = DatabaseContract.getColumnLong(cursor, COLUMN_ID);
        this.title = DatabaseContract.getColumnString(cursor, COLUMN_TITLE);
        this.desc = DatabaseContract.getColumnString(cursor, COLUMN_DESC);
        this.genre = DatabaseContract.getColumnString(cursor, COLUMN_GENRE);
        this.releaseDate = DatabaseContract.getColumnString(cursor, COLUMN_RELEASE_DATE);
        this.status = DatabaseContract.getColumnString(cursor, COLUMN_STATUS);
        this.runtime = DatabaseContract.getColumnString(cursor, COLUMN_RUNTIME);
        this.score = DatabaseContract.getColumnInt(cursor, COLUMN_SCORE);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.title);
        dest.writeString(this.desc);
        dest.writeString(this.genre);
        dest.writeString(this.releaseDate);
        dest.writeString(this.status);
        dest.writeString(this.runtime);
        dest.writeDouble(this.score);
    }

    private MovieItem(Parcel in) {
        this.id = in.readLong();
        this.title = in.readString();
        this.desc = in.readString();
        this.genre = in.readString();
        this.releaseDate = in.readString();
        this.status = in.readString();
        this.runtime = in.readString();
        this.score = in.readDouble();
    }

    public static final Parcelable.Creator<MovieItem> CREATOR = new Parcelable.Creator<MovieItem>() {
        @Override
        public MovieItem createFromParcel(Parcel source) {
            return new MovieItem(source);
        }

        @Override
        public MovieItem[] newArray(int size) {
            return new MovieItem[size];
        }
    };
}
