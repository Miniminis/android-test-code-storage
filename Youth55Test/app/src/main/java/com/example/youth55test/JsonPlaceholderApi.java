package com.example.youth55test;

import com.google.gson.JsonObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface JsonPlaceholderApi {
    //public static final String API_URL = "https://p2papp.crepass.com/p2papi/api2/invest/";

//    @POST("loanList")
//    Call<LoanDataList> createPost(@Body Param param);

    @POST("loanList")
    Call<LoanPostResult> createPost(@Body Request request);

//    @POST("loanList")
////    Call<LoanDataList> createPost(@Body Request request);
//
//    @FormUrlEncoded
//    @POST("loanList")
//    Call<LoanDataList> createPost(@Field("pageNum") int pageNum);
//
//    @POST("posts")
//    Call<Post> createPost(@Body Post post);

}
