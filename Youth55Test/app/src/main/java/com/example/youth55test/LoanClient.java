package com.example.youth55test;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoanClient {

    public static String Base_URL = "https://p2papp.crepass.com/p2papi/api2/invest/";

    private static Retrofit retrofit;

    public static Retrofit getClient(){
        if(retrofit == null) {
             retrofit = new Retrofit.Builder()
                    .baseUrl(Base_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}

