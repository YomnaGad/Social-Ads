package com.example.android.socialads.ui.offers;

import android.util.Log;

import com.example.android.socialads.data.DataManager;
import com.example.android.socialads.data.model.Offer;
import com.example.android.socialads.data.model.Response;

import java.util.ArrayList;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Yomna on 5/23/2017.
 */

public class OfferPresenter {
    private OfferBaseView view;
    private DataManager dataManager;
    private Subscription mSubscription;
    private ArrayList<Offer> offers;
    public OfferPresenter(OfferBaseView view, DataManager dataManager) {
        this.view = view;
        this.dataManager = dataManager;
    }
    public void getAllOffer(String lat, String lon){
        mSubscription = dataManager.getAllOffers(lat, lon)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Response>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.v("offers_presenter", e.getMessage());
                    }

                    @Override
                    public void onNext(Response response) {
                        Log.v("offers_presenter", "onNext ");
                        if (response.offers.size()!=0){
                            Log.v("places_presenter", "if is true ");
                            view.offerSuccess(response.offers);
                        }
                        else
                            Log.v("places_presenter", "length is zero ");
                    }

                });
    }
}
