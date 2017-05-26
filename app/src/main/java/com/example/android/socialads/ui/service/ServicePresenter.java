package com.example.android.socialads.ui.service;

import android.util.Log;

import com.example.android.socialads.data.DataManager;
import com.example.android.socialads.data.model.Response;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Yomna on 5/11/2017.
 */

public class ServicePresenter {
    private DataManager dataManager;
    private Subscription mSubscription;
    private ServiceBaseView view;
    public ServicePresenter(ServiceBaseView view, DataManager dataManager) {
        this.view = view;
        this.dataManager = dataManager;
    }
    public void getOffers(String lat, String lon, String type){
        mSubscription = dataManager.getOffers(lat, lon, type)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Response>(){
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.v("background", e.getMessage());
                    }

                    @Override
                    public void onNext(Response response) {
                        Log.v("Service_presenter", "onNext ");
                        if (response.offers.size()!=0){
                            Log.v("offers", "if is true ");
                            Log.v("offers", response.offers.get(0).toString());
                            view.serviceSuccess(response.offers);
                        }
                        else
                            Log.v("service_presenter", "length is zero ");
                    }
                });
    }
}
