package com.dargoz.madesubmission.main.movies.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Movies implements Parcelable {
    protected int id;
    protected String title;
    protected String desc;
    private ArrayList<Genre> genres = new ArrayList<>();
    protected String releaseDate;
    private String status;
    private String runtime;
    protected double score;
    protected String imagePath;
    private int imageId;

    public Movies(){

    }

    public Movies(@NonNull JSONObject movieOject){
        try {
            this.id = movieOject.getInt("id");
            this.title = movieOject.getString("title");
            this.desc = movieOject.getString("overview");
            this.releaseDate = movieOject.getString("release_date");
            this.score = movieOject.getDouble("vote_average");
            this.imagePath = movieOject.getString("poster_path");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public int getId() {
        return id;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public String getImagePath() {
        return imagePath;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setGenres(ArrayList<Genre> genres) {
        this.genres = genres;
    }

    public ArrayList<Genre> getGenres() {
        return genres;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.desc);
        dest.writeList(this.genres);
        dest.writeString(this.releaseDate);
        dest.writeString(this.status);
        dest.writeString(this.runtime);
        dest.writeDouble(this.score);
        dest.writeString(this.imagePath);
        dest.writeInt(this.imageId);
    }

    protected Movies(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.desc = in.readString();
        this.genres = new ArrayList<>();
        in.readList(this.genres, Genre.class.getClassLoader());
        this.releaseDate = in.readString();
        this.status = in.readString();
        this.runtime = in.readString();
        this.score = in.readDouble();
        this.imagePath = in.readString();
        this.imageId = in.readInt();
    }

    public static final Parcelable.Creator<Movies> CREATOR = new Parcelable.Creator<Movies>() {
        @Override
        public Movies createFromParcel(Parcel source) {
            return new Movies(source);
        }

        @Override
        public Movies[] newArray(int size) {
            return new Movies[size];
        }
    };
}
