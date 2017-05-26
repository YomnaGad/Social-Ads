package com.example.android.socialads.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Yomna on 5/23/2017.
 */

public class ResultDetail {
    @SerializedName("name")
    public String name;
    @SerializedName("rating")
    public float rating;
    @SerializedName("reviews")
    public ArrayList<Review> reviews;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Review> getReviews() {
        return reviews;
    }

    public void setReviews(ArrayList<Review> reviews) {
        this.reviews = reviews;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}
