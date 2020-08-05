package com.example.youth55test;

import com.google.gson.JsonObject;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IRetrofit {

    //public static final String API_URL = "https://p2papp.crepass.com/p2papi/api2/invest/";

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST("loanList")
    Call<ResponseBody> postRawJSON(@Body JsonObject locationPost);

}
