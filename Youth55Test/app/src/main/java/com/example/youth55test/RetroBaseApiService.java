package com.example.youth55test;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RetroBaseApiService {

    public static final String Base_URL = "https://p2papp.crepass.com/p2papi/api2/";

    @FormUrlEncoded
    @POST("invest/loanList")
    Call<ResponseGet> postFirst(@FieldMap HashMap<String, Object> parameters);

}
