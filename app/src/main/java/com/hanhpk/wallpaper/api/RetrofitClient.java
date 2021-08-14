package com.hanhpk.wallpaper.api;


import com.hanhpk.wallpaper.MyApp;
import com.hanhpk.wallpaper.util.Utils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * //  created by PhungKimHanh
 *
 */

public class RetrofitClient {
    private  final String BASE_URL = "https://www.flickr.com/";
    private static RetrofitClient instance;
    private Retrofit retrofit;
    private ApiService apiService;

    private RetrofitClient(){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();

        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(loggingInterceptor);

        OkHttpClient client = builder.addInterceptor(new ConnectivityInterceptor(MyApp.getInstance()))
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60,TimeUnit.SECONDS)
                .build();


        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static RetrofitClient getInstance(){
        if (instance==null){
           instance = new RetrofitClient();
        }
        return instance;
    }

    public ApiService getApiService(){
        return retrofit.create(ApiService.class);
    }
}
