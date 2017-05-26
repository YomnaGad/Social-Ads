package com.example.android.socialads.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Yomna on 5/22/2017.
 */

public class Offer {
    @SerializedName("category__category")
    String category;
    @SerializedName("name")
    String name;
    @SerializedName("offer")
    String offer;
    @SerializedName("place_id")
    public String place_id;

    public String getPlace_id() {
        return place_id;
    }

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getOffer() {
        return offer;
    }

    public void setOffer(String offer) {
        this.offer = offer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
