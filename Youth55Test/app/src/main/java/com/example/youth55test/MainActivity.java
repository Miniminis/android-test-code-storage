package com.example.youth55test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    //private TextView textView;
    private JsonPlaceholderApi jsonPlaceholderApi;

    private RecyclerView recyclerView;
    private RecyclerAdapter recyclerAdapter;
    private RecyclerView.LayoutManager layoutManager;

    List<LoanData> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //setContentView(R.layout.activity_test);

        //textView = findViewById(R.id.TextView_test);
        recyclerView = findViewById(R.id.my_recycler_view);
        layoutManager = new LinearLayoutManager(this);
        list = new ArrayList<>();
        recyclerView.setLayoutManager(layoutManager);
        recyclerAdapter = new RecyclerAdapter(getApplicationContext(), list);
        recyclerView.setAdapter(recyclerAdapter);

//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("https://p2papp.crepass.com/p2papi/api2/invest/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();

//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("https://jsonplaceholder.typicode.com/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();

        //jsonPlaceholderApi = retrofit.create(JsonPlaceholderApi.class);
        jsonPlaceholderApi = LoanClient.getClient().create(JsonPlaceholderApi.class);

        postLoanList();
        //postTry();
        //createPost();
    }

    public void postLoanList() {
        Param param = new Param(1);
        Request req = new Request(param);
        //Call<LoanPostResult> callLoanList= jsonPlaceholderApi.createPost(req);
        Call<LoanPostResult> callLoanList= jsonPlaceholderApi.createPost(req);

        //Call<LoanDataList> callLoanList= jsonPlaceholderApi.createPost(1);
        //Log.d("리스트 출력1", callLoanList.toString());

        callLoanList.enqueue(new Callback<LoanPostResult>() {

            @Override
            public void onResponse(Call<LoanPostResult> call, Response<LoanPostResult> response) {
                if(!response.isSuccessful()) {
                    //textView.setText("response chk ::: "+response.code());
                    Log.d("반응체크1 ", "response code :::  "+response.code());
                    return;
                }

                LoanPostResult result = response.body();
                //List<LoanData> list = result.getResult().getList();
                list = result.getResult().getList();
                Log.d("반응체크2 ", "list:::  "+list);
                recyclerAdapter.setList(list); //넘어온 데이터 화면에 표시하기 위해 adapter 로 전달


//                String content = "콘텐츠가 안나와";
                //Character c = 'A';
//                content += " \n ==== \n";
//                content += "response.code() in content " + response.code();
//                content += " \n ==== \n";
//                content += result.getMessage();
//                content += " \n ==== \n";
//                content += result.getState();
//                content += " \n ==== \n";
//                content += result.getResult();
//                content += " \n ==== \n";
//                content += result.getResult().getTotPageCount();

//                for(LoanData data : list) {
////                    String content = "";
//                    content += "loanSubject"+ data.getLoanSubject();
//                    //textView.append(content);
//                }
//
//
//                //textView.setText(c);
//                textView.setText(content);
            }

            @Override
            public void onFailure(Call<LoanPostResult> call, Throwable t) {
                //textView.setText("t.getMessage() ::: "+t.getMessage());
                Log.d("통신오류발생", "통신 에러 :::  "+t.getMessage());
            }
        });
    }

//    public void createPost() {
//        Post post = new Post(1, "제목", "내용입니다.");
//        Call<Post> responsePost = jsonPlaceholderApi.createPost(post);
//        responsePost.enqueue(new Callback<Post>() {
//            @Override
//            public void onResponse(Call<Post> call, Response<Post> response) {
//                if(!response.isSuccessful()){
//                    textView.setText("code :::: "+ response.code());
//                    return;
//                }
//
//                Post res = response.body();
//                String content = "";
//                content += " \n ==== \n";
//                content += response.code();
//                content += " \n ==== \n";
//                content += res.getId();
//                content += " \n ==== \n";
//                content += res.getUserId();
//                content += " \n ==== \n";
//                content += res.getText();
//                content += "\n ==== \n";
//                content += res.getTitle();
//
//                textView.setText(content);
//
//            }
//
//
//            @Override
//            public void onFailure(Call<Post> call, Throwable t) {
//
//            }
//        });
//
//
//    }

    public void postTry() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("pageNum",1);
//        JsonArray citiesArray = new JsonArray();
//        citiesArray.add("Dhaka");
//        citiesArray.add("Örebro");
//        jsonObject.add("cities", citiesArray);

        IRetrofit jsonPostService = ServiceGenerator.createService(IRetrofit.class, "https://p2papp.crepass.com/p2papi/api2/invest/");
        Call<ResponseBody> call = jsonPostService.postRawJSON(jsonObject);
        call.enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try{
                    Log.e("IRetrofit-TEST", response.body().toString());
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("IRetrofit-TEST", call.toString());
            }
        });
    }
}


//        //git repository list  : GET + list
//        //1. retrofit interface 생성
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("https://api.github.com/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        //2. retrofit interface 에 gitservice interface 붙여주기
//        GitHubService service = retrofit.create(GitHubService.class);
//
//        //3. 받은 gitservice interface 에 대한 callback 함수 만들기
//        Call<List<Repo>> repoList = service.listRepos("miniminis");
//        repoList.enqueue(new Callback<List<Repo>>() {
//            @Override
//            public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {
//                int statusCode = response.code();
//                List<Repo> user = response.body();
//
//                Log.d("GIT API TEST ING", user.size()+"개의 데이터가 존재합니다.");
//            }
//
//            @Override
//            public void onFailure(Call<List<Repo>> call, Throwable t) {
//                Log.d("GIT API TEST ING", "실패했습니다!");
//            }
//        });
