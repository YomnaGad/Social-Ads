package com.example.android.socialads.ui.offers;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.android.socialads.R;
import com.example.android.socialads.data.DataManager;
import com.example.android.socialads.data.model.Offer;

import java.util.ArrayList;

/**
 * Created by Yomna on 5/23/2017.
 */

public class ActivityOffers extends AppCompatActivity implements OfferBaseView {
    private OfferPresenter offerPresenter;
    private RecyclerView mRecyclerView;
    public static  MyRecyclerAdap adapter;
    ArrayList<Offer> offers;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.offer_activity);
        mRecyclerView = (RecyclerView)findViewById(R.id.recycler_view_offer);
        offerPresenter =  new OfferPresenter(this, DataManager.getInstance(null,null, null, null, null));
        offerPresenter.getAllOffer("30.06662", "31.277872");

    }

    @Override
    public void offerSuccess(ArrayList<Offer> offer) {
        offers = new ArrayList<Offer>();
        offers.addAll(offer);
        adapter = new MyRecyclerAdap(this, offers);
        mRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void offerError(String e) {

    }

    @Override
    public void offerComplete() {

    }
}
