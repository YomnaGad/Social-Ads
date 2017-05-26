package com.example.android.socialads.ui.service;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.android.socialads.R;
import com.example.android.socialads.data.DataManager;
import com.example.android.socialads.data.local.DatabaseHelper;
import com.example.android.socialads.data.local.DbOpenHelper;
import com.example.android.socialads.data.local.PreferencesHelper;
import com.example.android.socialads.data.model.Offer;
import com.example.android.socialads.data.remote.BackendService;
import com.example.android.socialads.ui.detail.DetailActivity;

import java.util.ArrayList;

public class MyService extends Service implements ServiceBaseView {
    private static final String TAG = "BOOMBOOMTESTGPS";
    private LocationManager mLocationManager = null;
    private static final int LOCATION_INTERVAL = 60000;
    private static final float LOCATION_DISTANCE = 0;
    private int count =0 ;
    private ServicePresenter servicePresenter ;
    private BroadcastReceiver broadcastReceiver;
    SharedPreferences prefs;
    String type;
    String temp;
  /*  private double latitude;
    private double longitude;*/
    ArrayList<Offer> offers = new ArrayList<Offer>();

    @Override
    public void serviceSuccess(ArrayList<Offer> offersArr) {
        offers.addAll(offersArr);
    }

    @Override
    public void serviceError(String e) {

    }

    @Override
    public void serviceComplete() {

    }


    private class LocationListener implements android.location.LocationListener {
        Location mLastLocation;

        public LocationListener(String provider) {
            Log.e(TAG, "LocationListener " + provider);
            mLastLocation = new Location(provider);
        }

        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onLocationChanged(Location location) {
            Log.e(TAG, "onLocationChanged: " + location);
            mLastLocation.set(location);
          /*  latitude = mLastLocation.getLatitude();
            longitude = mLastLocation.getLongitude();*/
            prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            DbOpenHelper dbOpenHelper = DbOpenHelper.getInstance(getApplicationContext());
            DatabaseHelper databaseHelper = DatabaseHelper.getInstance(dbOpenHelper);
            PreferencesHelper preferencesHelper = new PreferencesHelper();
            com.example.android.socialads.data.remote.Service service = com.example.android.socialads.data.remote.Service.Creator.getService();
            BackendService backendService = BackendService.Creator.getService();
            DataManager dataManager = DataManager.getInstance(getApplicationContext(), backendService, service, databaseHelper, preferencesHelper);

            servicePresenter = new ServicePresenter(MyService.this,dataManager);
            type = prefs.getString(getString(R.string.pref_filter_key),
                    getString(R.string.pref_filter_default));
          //type = PreferencesHelper.getFromPrefs(MyService.this, R.string.pref_filter_key,R.string.pref_filter_default );
            Log.v("MyService", type);
            if (type.equals("popular"))
            {
                type = "restaurant";
            }
            else if (type.equals("clothing_store")){
                type="clothing";
            }
            servicePresenter.getOffers(String.valueOf(30.06662), String.valueOf(31.277872), type);
            count = count +1;

            if((count==0 || type.equals(temp)==false )&& offers.size()!=0  ) {
                temp = type;
                Log.v("count", String.valueOf(count));
                NotificationCompat.Builder builder =
                        new NotificationCompat.Builder(getApplicationContext())
                                .setContentTitle(offers.get(0).getName())
                                .setContentText(offers.get(0).getOffer()).
                                setSmallIcon(R.drawable.placeholder).setDefaults(Notification.DEFAULT_SOUND);
                Intent notificationIntent = new Intent(getApplicationContext(), DetailActivity.class);
                notificationIntent.putExtra("place_id", offers.get(0).getPlace_id());
                notificationIntent.putExtra("offer", offers.get(0).getOffer());
                PendingIntent contentIntent = PendingIntent.getActivity(getApplicationContext(), 0, notificationIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);
                builder.setContentIntent(contentIntent);

                // Add as notification
                NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                manager.notify(0, builder.build());
            }
            Intent i = new Intent("location_update");
            i.putExtra("coordinates", mLastLocation.getLatitude()+","+ mLastLocation.getLongitude());
            sendBroadcast(i);
        }

        @Override
        public void onProviderDisabled(String provider) {
            Log.e(TAG, "onProviderDisabled: " + provider);
        }

        @Override
        public void onProviderEnabled(String provider) {
            Log.e(TAG, "onProviderEnabled: " + provider);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            Log.e(TAG, "onStatusChanged: " + provider);
        }
    }

    LocationListener[] mLocationListeners = new LocationListener[]{
            new LocationListener(LocationManager.GPS_PROVIDER),
            new LocationListener(LocationManager.NETWORK_PROVIDER)
    };

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand");
        super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        Log.e(TAG, "onCreate");
        initializeLocationManager();
        try {
            mLocationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE,
                    mLocationListeners[1]);
        } catch (java.lang.SecurityException ex) {
            Log.i(TAG, "fail to request location update, ignore", ex);
        } catch (IllegalArgumentException ex) {
            Log.d(TAG, "network provider does not exist, " + ex.getMessage());
        }
        try {
            mLocationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE,
                    mLocationListeners[0]);
        } catch (java.lang.SecurityException ex) {
            Log.i(TAG, "fail to request location update, ignore", ex);
        } catch (IllegalArgumentException ex) {
            Log.d(TAG, "gps provider does not exist " + ex.getMessage());
        }
    }

    @Override
    public void onDestroy() {
        Log.e(TAG, "onDestroy");
        super.onDestroy();
        if (mLocationManager != null) {
            for (int i = 0; i < mLocationListeners.length; i++) {
                try {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    mLocationManager.removeUpdates(mLocationListeners[i]);
                } catch (Exception ex) {
                    Log.i(TAG, "fail to remove location listners, ignore", ex);
                }
            }
        }
    }

    private void initializeLocationManager() {
        Log.e(TAG, "initializeLocationManager");
        if (mLocationManager == null) {
            mLocationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        }
    }
}