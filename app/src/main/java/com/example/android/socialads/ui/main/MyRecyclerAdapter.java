package com.example.android.socialads.ui.main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.socialads.R;
import com.example.android.socialads.data.model.Place;
import com.example.android.socialads.ui.detail.DetailActivity;

import java.util.ArrayList;

/**
 * Created by Yomna on 5/13/2017.
 */

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.CustomViewHolder> {

    private ArrayList<Place> places;
    private AppCompatActivity mContext;

    public MyRecyclerAdapter(AppCompatActivity context, ArrayList<Place>places) {
        this.places = places;
        this.mContext = context;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, viewGroup, false);

        CustomViewHolder viewHolder = new CustomViewHolder(view);

        return viewHolder;
    }


    public void onBindViewHolder(final CustomViewHolder customViewHolder, int i) {

        final Place placesItem = places.get(i);
        customViewHolder.name.setText(placesItem.getName());
        customViewHolder.address.setText(placesItem.getVicinity());
        customViewHolder.rating.setRating(placesItem.getRating());
        //Download image using picasso library

        customViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), customViewHolder.name.getText().toString(), Toast.LENGTH_SHORT);
                Log.v("click", "click");
                Intent intent = new Intent(view.getContext(), DetailActivity.class);
                intent.putExtra("place_id", placesItem.getPlace_id());
                view.getContext().startActivity(intent);
            }
        });



        //customViewHolder.imageView.setTag(customViewHolder);
    }


    @Override
    public int getItemCount() {
        return (null != places ? places.size() : 0);
    }




    public class CustomViewHolder extends RecyclerView.ViewHolder {
        protected TextView name;
        protected TextView address;
        protected RatingBar rating;
        // protected TextView textView;

        public CustomViewHolder(View view) {
            super(view);
            this.name = (TextView) view.findViewById(R.id.place_name);
            this.address = (TextView)view.findViewById(R.id.place_address);
            this.rating = (RatingBar) view.findViewById(R.id.place_rate);
        }
    }

}
