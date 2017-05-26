package com.example.android.socialads.ui.service;

import com.example.android.socialads.data.model.Offer;

import java.util.ArrayList;

/**
 * Created by Yomna on 5/11/2017.
 */

public interface ServiceBaseView {
    public void serviceSuccess(ArrayList<Offer> offers);
    public void serviceError(String e);
    public void serviceComplete();
}
