package com.example.android.socialads.ui.main;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.android.socialads.R;
import com.example.android.socialads.data.DataManager;
import com.example.android.socialads.data.local.DatabaseHelper;
import com.example.android.socialads.data.local.DbOpenHelper;
import com.example.android.socialads.data.local.PreferencesHelper;
import com.example.android.socialads.data.model.Place;
import com.example.android.socialads.data.remote.BackendService;
import com.example.android.socialads.data.remote.Service;
import com.example.android.socialads.ui.filter.FilterActivity;
import com.example.android.socialads.ui.offers.ActivityOffers;
import com.example.android.socialads.ui.service.MyService;

import java.util.ArrayList;

/**
 * Created by Yomna on 5/12/2017.
 */

public class MainActivity  extends AppCompatActivity implements MainBaseView{
    //public static ArrayList<Double> locationDetails = new ArrayList<Double>();
    private MainPresenter mainPresenter ;
    private BroadcastReceiver broadcastReceiver;
    private String locationDetails;
    private RecyclerView mRecyclerView;
    public static MyRecyclerAdapter adapter;
    ArrayList<Place> getPlaces;
    //ArrayList<Place> comparedPlaces;
    SharedPreferences prefs;
    String location;
    int count = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DbOpenHelper dbOpenHelper = DbOpenHelper.getInstance(this.getApplicationContext());
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(dbOpenHelper);
        PreferencesHelper preferencesHelper = new PreferencesHelper();
        Service service = Service.Creator.getService();
        BackendService backendService = BackendService.Creator.getService();
        DataManager dataManager = DataManager.getInstance(this, backendService, service, databaseHelper, preferencesHelper);

        mRecyclerView = (RecyclerView)findViewById(R.id.recycler_view_interest);

        mainPresenter =  new MainPresenter(this, DataManager.getInstance(null,null, null, null, null));
        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        Intent intent = new Intent(this, MyService.class);
        startService(intent);



    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_settings){
            startActivity(new Intent(this, FilterActivity.class));
            return true;
        }
        if (id == R.id.show_offer){
            startActivity(new Intent(this, ActivityOffers.class));
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (broadcastReceiver == null){
            broadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    Object coordinates = intent.getExtras().get("coordinates");
                    locationDetails = String.valueOf(coordinates);
                    getPlaces = new ArrayList<Place>();
                    /*SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
                                String location = prefs.getString(getString(R.string.pref_filter_key),
                                               getString(R.string.pref_filter_default));*/
                    location = prefs.getString(getString(R.string.pref_filter_key),
                            getString(R.string.pref_filter_default));
                    //solving bug "i dont know why location string contains popular word when app strated "
                    //too lazy to track the bug
                    if(location.equals("popular")){
                        location = "restaurant";
                    }
                    Log.i("type", location);
                    Log.i("type", "");
                    mainPresenter.getPlaces(locationDetails, "500", location, "");
                    Log.v("coordinates",locationDetails);
                }
            };
        }
        registerReceiver(broadcastReceiver, new IntentFilter("location_update"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(broadcastReceiver!=null){
            unregisterReceiver(broadcastReceiver);
        }
    }

    @Override
    public void interestSuccess(ArrayList<Place> places) {
        getPlaces.clear();
        getPlaces.addAll(places);
        Log.v("places", getPlaces.get(0).toString());
        adapter = new MyRecyclerAdapter(this, getPlaces);
        mRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public void interestError(String e) {

    }

    @Override
    public void interestComplete() {

    }
    public boolean isTwoArrayListsWithSameValues(ArrayList<Place> list1, ArrayList<Place> list2)
    {
        //null checking
        if(list1==null && list2==null)
            return true;
        if((list1 == null && list2 != null) || (list1 != null && list2 == null))
            return false;

        if(list1.size()!=list2.size())
            return false;
        for(Object itemList1: list1)
        {
            if(!list2.contains(itemList1))
                return false;
        }

        return true;
    }
}
