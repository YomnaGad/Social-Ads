package com.example.android.socialads.data;

import android.content.Context;
import android.util.Log;

import com.example.android.socialads.data.local.DatabaseHelper;
import com.example.android.socialads.data.local.PreferencesHelper;
import com.example.android.socialads.data.model.Response;
import com.example.android.socialads.data.remote.BackendService;
import com.example.android.socialads.data.remote.Service;
import com.example.android.socialads.utils.GlobalEntities;

import rx.Observable;

/**
 * Created by Yomna on 5/11/2017.
 */

public class DataManager {
    private static DataManager dataManager;
    private final DatabaseHelper mDatabaseHelper;
    private final PreferencesHelper mPreferencesHelper;
    private final Service mService;
    private final BackendService backendService;
    private final Context mContext;

    private DataManager(Context context,BackendService backendService, Service mService, DatabaseHelper mDatabaseHelper, PreferencesHelper mPreferencesHelper) {
        Log.i(GlobalEntities.DATA_MANAGER_TAG, "DataManager: Created");
        mContext = context;
        this.mService = mService;
        this.backendService = backendService;
        this.mDatabaseHelper = mDatabaseHelper;
        this.mPreferencesHelper = mPreferencesHelper;

    }


    public static DataManager getInstance(Context context, BackendService backendService,Service mService, DatabaseHelper mDatabaseHelper, PreferencesHelper mPreferencesHelper) {
        if (dataManager == null) {
            dataManager = new DataManager(context,backendService, mService, mDatabaseHelper, mPreferencesHelper);
        }

        return dataManager;
    }

    public static boolean isNull() {
        return dataManager == null;
    }

    public PreferencesHelper getPreferencesHelper() {
        return mPreferencesHelper;
    }

    public Observable<Response> getPlaces(String location, String radius, String type, String apiKey ){
        Log.v("places_datamanger", apiKey);
        return mService.getPlaces(location, radius, type, apiKey);
    }
    public Observable<Response> getOffers(String lat, String lon, String type ){
        Log.v("offers_datamanger", type);
        return backendService.getOffers(lat,lon, type);
    }
    public Observable<Response> getDetail(String placeId, String apiKey ){
        Log.v("detail_datamanger", apiKey);
        return mService.getDetail(placeId,apiKey);
    }
    public Observable<Response> getAllOffers(String lat, String lon ){
        Log.v("Alloffers_datamanger", lat);
        return backendService.getAllOffers(lat,lon);
    }
}