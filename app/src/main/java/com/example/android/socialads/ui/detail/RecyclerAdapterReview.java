package com.example.android.socialads.ui.detail;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.socialads.R;
import com.example.android.socialads.data.model.Review;

import java.util.ArrayList;

/**
 * Created by Yomna on 5/23/2017.
 */

public class RecyclerAdapterReview  extends RecyclerView.Adapter<RecyclerAdapterReview.CustomViewHolder> {

    private ArrayList<Review> reviews;
    private AppCompatActivity mContext;

    public RecyclerAdapterReview(AppCompatActivity context, ArrayList<Review>reviews) {
        this.reviews = reviews;
        this.mContext = context;
    }

    @Override
    public RecyclerAdapterReview.CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.review_item, viewGroup, false);

        RecyclerAdapterReview.CustomViewHolder viewHolder = new RecyclerAdapterReview.CustomViewHolder(view);

        return viewHolder;
    }


    public void onBindViewHolder(final CustomViewHolder customViewHolder, int i) {

        final Review reviewItem = reviews.get(i);
        customViewHolder.text.setText(reviewItem.getText());




        //customViewHolder.imageView.setTag(customViewHolder);
    }


    @Override
    public int getItemCount() {
        return (null != reviews ? reviews.size() : 0);
    }




    public class CustomViewHolder extends RecyclerView.ViewHolder {
        protected TextView text;
        // protected TextView textView;

        public CustomViewHolder(View view) {
            super(view);
            this.text = (TextView) view.findViewById(R.id.review_text);
        }
    }

}

