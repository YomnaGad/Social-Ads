package com.example.android.socialads.ui.main;

import com.example.android.socialads.data.model.Place;

import java.util.ArrayList;

/**
 * Created by Yomna on 5/12/2017.
 */

public interface MainBaseView {
    public void interestSuccess(ArrayList<Place> places);
    public void interestError(String e);
    public void interestComplete();
}
