package com.example.youth55test;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GitHubService {
    //public static final String API_URL = "https://p2papp.crepass.com/p2papi/api2/invest/";

    //@Headers("Content-Type: application/json")
    //@POST("loanList")
    //Call<ResponseBody> getLoanList(@Query("pageNum") int pageNum);
    //Call<ResponseBody> getLoanList(@Body String body);

    @GET("/users/{user}/repos")
    Call<List<Repo>> listRepos(@Path("user") String user);
}
