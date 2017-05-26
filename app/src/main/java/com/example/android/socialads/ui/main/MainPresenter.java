package com.example.android.socialads.ui.main;

import android.util.Log;

import com.example.android.socialads.data.DataManager;
import com.example.android.socialads.data.model.Place;
import com.example.android.socialads.data.model.Response;

import java.util.ArrayList;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Yomna on 5/12/2017.
 */

public class MainPresenter {
    private MainBaseView view;
    private DataManager dataManager;
    private Subscription mSubscription;
    private ArrayList<Place> places;
    public MainPresenter(MainBaseView view, DataManager dataManager) {
        this.view = view;
        this.dataManager = dataManager;
    }
    public void getPlaces(String location, String radius , String type, String key){
        Log.v("places_presenter", key);
        mSubscription = dataManager.getPlaces(location, radius, type, key)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Response>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.v("places_presenter", e.getMessage());
                    }

                    @Override
                    public void onNext(Response response) {
                        Log.v("places_presenter", "onNext ");
                        if (response.results.size()!=0){
                            Log.v("places_presenter", "if is true ");
                            view.interestSuccess(response.results);
                        }
                        else
                            Log.v("places_presenter", "length is zero ");
                    }
                });
    }
}
