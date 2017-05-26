package com.example.android.socialads.ui.offers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.socialads.R;
import com.example.android.socialads.data.model.Offer;
import com.example.android.socialads.ui.detail.DetailActivity;

import java.util.ArrayList;

/**
 * Created by Yomna on 5/23/2017.
 */

public class MyRecyclerAdap  extends RecyclerView.Adapter<MyRecyclerAdap.CustomViewHolder> {

    private ArrayList<Offer> offers;
    private AppCompatActivity mContext;

    public MyRecyclerAdap(AppCompatActivity context, ArrayList<Offer>offers) {
        this.offers = offers;
        this.mContext = context;
    }

    @Override
    public MyRecyclerAdap.CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.offer_item, viewGroup, false);

        MyRecyclerAdap.CustomViewHolder viewHolder = new MyRecyclerAdap.CustomViewHolder(view);

        return viewHolder;
    }


    public void onBindViewHolder(final MyRecyclerAdap.CustomViewHolder customViewHolder, int i) {

        final Offer offerItem = offers.get(i);
        customViewHolder.name.setText(offerItem.getName());
        customViewHolder.offer.setText(offerItem.getOffer());
        //Download image using picasso library

        customViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(view.getContext(), DetailActivity.class);
                intent.putExtra("place_id", offerItem.getPlace_id());
                intent.putExtra("offer", offerItem.getOffer());
                view.getContext().startActivity(intent);
            }
        });



        //customViewHolder.imageView.setTag(customViewHolder);
    }


    @Override
    public int getItemCount() {
        return (null != offers ? offers.size() : 0);
    }




    public class CustomViewHolder extends RecyclerView.ViewHolder {
        protected TextView name;
        protected TextView offer;
        // protected TextView textView;

        public CustomViewHolder(View view) {
            super(view);
            this.name = (TextView) view.findViewById(R.id.place_name);
            this.offer = (TextView)view.findViewById(R.id.place_offer);
        }
    }

}
