package com.dargoz.madesubmission.main.tvshow.model;

import android.os.Parcel;

import com.dargoz.madesubmission.main.movies.model.Movies;

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

    public TvShow() {
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
