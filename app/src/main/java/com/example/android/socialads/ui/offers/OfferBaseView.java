package com.example.android.socialads.ui.offers;

import com.example.android.socialads.data.model.Offer;

import java.util.ArrayList;

/**
 * Created by Yomna on 5/23/2017.
 */

public interface OfferBaseView {
    public void offerSuccess(ArrayList<Offer> offers);
    public void offerError(String e);
    public void offerComplete();
}
