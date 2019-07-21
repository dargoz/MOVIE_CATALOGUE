package com.dargoz.madesubmission.main.tvshow.model;

import android.os.Parcel;

import com.dargoz.madesubmission.main.movies.model.Movies;

import org.json.JSONException;
import org.json.JSONObject;

public class TvShow extends Movies {
    private String totalEpisode;

    public String getTotalEpisode() {
        return totalEpisode;
    }

    public void setTotalEpisode(String totalEpisode) {
        this.totalEpisode = totalEpisode;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.totalEpisode);
    }

    public TvShow(JSONObject tvObject){
        try {
            id = tvObject.getInt("id");
            title = tvObject.getString("name");
            desc = tvObject.getString("overview");
            releaseDate = tvObject.getString("first_air_date");
            score = tvObject.getDouble("vote_average");
            imagePath = tvObject.getString("poster_path");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    protected TvShow(Parcel in) {
        super(in);
        this.totalEpisode = in.readString();
    }

    public static final Creator<TvShow> CREATOR = new Creator<TvShow>() {
        @Override
        public TvShow createFromParcel(Parcel source) {
            return new TvShow(source);
        }

        @Override
        public TvShow[] newArray(int size) {
            return new TvShow[size];
        }
    };
}
