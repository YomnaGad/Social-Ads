package com.example.android.socialads.data.remote;

import com.example.android.socialads.data.model.Response;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Yomna on 5/22/2017.
 */

public interface BackendService {

    @GET("getNearestStores?")
    Observable<Response> getOffers(@Query("lat") String lat,@Query("lon") String lon, @Query("category") String type);

    @GET("getOnePlace?")
    Observable<Response> getAllOffers(@Query("lat") String lat,@Query("lon") String lon);

    class Creator{
        public static BackendService getService(){

            final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .readTimeout(60, TimeUnit.SECONDS)
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .build();
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            // configure retrofit in android app
            Retrofit retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .baseUrl("http://ec2-52-35-30-100.us-west-2.compute.amazonaws.com:80/store/")
                    .client(okHttpClient)
                    .build();


            return retrofit.create(BackendService.class);
        }

    }
}
