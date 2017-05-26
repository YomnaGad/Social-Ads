package com.example.android.socialads.data.remote;

import com.example.android.socialads.data.model.Response;
import com.example.android.socialads.utils.GlobalEntities;
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
 * Created by Yomna on 5/11/2017.
 */

public interface Service {

    @GET("nearbysearch/json?")
    Observable<Response> getPlaces(@Query("location") String location, @Query("radius") String radius,
                                   @Query("type") String type, @Query("key") String key);

    @GET("details/json?")
    Observable<Response> getDetail( @Query("placeid") String placeId,@Query("key") String key);
    class Creator{
        public static Service getService(){

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
                    .baseUrl(GlobalEntities.BASE_URL)
                    .client(okHttpClient)
                    .build();


            return retrofit.create(Service.class);
        }

    }
}
