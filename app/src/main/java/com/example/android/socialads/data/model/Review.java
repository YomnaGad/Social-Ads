package com.example.android.socialads.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Yomna on 5/23/2017.
 */

public class Review {
    @SerializedName("text")
    public String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
