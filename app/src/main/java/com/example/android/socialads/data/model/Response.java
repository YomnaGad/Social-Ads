package com.example.android.socialads.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Yomna on 5/13/2017.
 */

public class Response {
    @SerializedName("results")
    public ArrayList<Place> results;
    @SerializedName("results_offers")
    public ArrayList<Offer> offers;
    @SerializedName("result")
    public ResultDetail result;
}
