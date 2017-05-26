package com.example.android.socialads.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Yomna on 5/13/2017.
 */

public class Place {
    @SerializedName("name")
    public String name;

    @SerializedName("place_id")
    public String place_id;

    @SerializedName("rating")
    public float rating ;

    @SerializedName("vicinity")
    public String vicinity;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getPlace_id() {
        return place_id;
    }

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }

    public String getVicinity() {
        return vicinity;
    }

    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }
}
