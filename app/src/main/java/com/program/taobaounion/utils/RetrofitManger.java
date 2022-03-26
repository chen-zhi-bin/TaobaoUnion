package com.program.taobaounion.utils;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManger {
    private static final RetrofitManger outInstance= new RetrofitManger();
    private final Retrofit mRetrofit;

    public static RetrofitManger getInstance(){
        return outInstance;
    }

    private RetrofitManger(){
        //船舰retorfit
        mRetrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public Retrofit getRetrofit(){
        return mRetrofit;
    }
}
