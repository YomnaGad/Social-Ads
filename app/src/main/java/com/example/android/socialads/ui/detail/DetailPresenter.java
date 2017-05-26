package com.example.android.socialads.ui.detail;

import android.util.Log;

import com.example.android.socialads.data.DataManager;
import com.example.android.socialads.data.model.Response;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Yomna on 5/23/2017.
 */

public class DetailPresenter {
    private DetailBaseView view;
    private DataManager dataManager;
    private Subscription mSubscription;

    public DetailPresenter(DetailBaseView view, DataManager dataManager) {
        this.view = view;
        this.dataManager = dataManager;
    }
    public void getDetail(String placid, String apiKey) {
        mSubscription = dataManager.getDetail(placid, apiKey)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Response>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.v("Detail_presenter", e.getMessage());
                    }

                    @Override
                    public void onNext(Response response) {
                        Log.v("places_presenter", "onNext ");
                        if (response.result!=null){
                            Log.v("Detail_presenter", "if is true ");
                            view.detailSuccess(response.result);
                        }
                        else
                            Log.v("Detail_presenter", "length is zero ");
                    }
                });
    }
}
