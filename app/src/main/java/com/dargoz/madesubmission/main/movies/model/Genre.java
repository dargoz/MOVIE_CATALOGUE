package com.dargoz.madesubmission.main.movies.model;

import org.json.JSONException;
import org.json.JSONObject;

public class Genre {
    private int id;
    private String name;

    public Genre(JSONObject genreObject){
        try {
            this.id = genreObject.getInt("id");
            this.name = genreObject.getString("name");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public String getName() {
        return name;
    }
}
