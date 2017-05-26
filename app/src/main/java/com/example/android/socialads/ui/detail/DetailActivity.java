package com.example.android.socialads.ui.detail;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.android.socialads.R;
import com.example.android.socialads.data.DataManager;
import com.example.android.socialads.data.model.ResultDetail;
import com.example.android.socialads.data.model.Review;

import java.util.ArrayList;

/**
 * Created by Yomna on 5/23/2017.
 */

public class DetailActivity extends AppCompatActivity implements DetailBaseView {
    private DetailPresenter detailPresenter ;
    private ResultDetail resultDetail;
    private TextView name ;
    private RatingBar rate;
    private TextView offer;
    private TextView review;
    private RecyclerView mRecyclerView;
    ArrayList<Review> reviews;
    public static RecyclerAdapterReview adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        resultDetail = new ResultDetail();
        Bundle bundle = getIntent().getExtras();
        String placeid = bundle.getString("place_id");
        String specialOffer = bundle.getString("offer");
        name =(TextView)findViewById(R.id.place_name);
        rate =(RatingBar)findViewById(R.id.place_rate);
        offer=(TextView) findViewById(R.id.offer);
        review = (TextView)findViewById(R.id.review);
        mRecyclerView = (RecyclerView)findViewById(R.id.recycler_view_review);
        detailPresenter =  new DetailPresenter(this, DataManager.getInstance(null,null, null, null, null));
        detailPresenter.getDetail(placeid, "");
        reviews = new ArrayList<Review>();
        if (specialOffer!=null){
            offer.setText(specialOffer);
        }
        else
            offer.setText("No special offers");


    }
    @Override
    public void detailSuccess(ResultDetail detail) {
        resultDetail = detail;
        name.setText(resultDetail.getName());
        rate.setRating(resultDetail.getRating());
        if (detail.getReviews().size()!=0) {
            reviews.clear();
            reviews.addAll(detail.getReviews());

            Log.v("review", detail.getReviews().get(0).getText().toString());
            adapter = new RecyclerAdapterReview(this, reviews);
            mRecyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
        else
            review.setText("No reviews to show");

    }

    @Override
    public void detailtError(String e) {

    }

    @Override
    public void detailComplete() {

    }
}

