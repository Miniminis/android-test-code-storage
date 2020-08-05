package com.example.androidtestproject.newsapplication

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.androidtestproject.R
import org.json.JSONException
import org.json.JSONObject
import java.util.*

class NewsMainActivity : AppCompatActivity() {

    //1. 화면 상 보이는 리사이클러 뷰 캐스팅, adapter + layoutmanager 변수 선언
    private var recyclerView: RecyclerView? = null
    private var mAdapter: RecyclerView.Adapter<*>? = null
    private var layoutManager: RecyclerView.LayoutManager? = null

    //api에서 넘겨받을 데이터 세트
    //private String[] myDataset = {"첫번째 기사!", "두번째 기사!!!!", "세번째 기사!!!!", "네번째 기사!!!!"};

    //api에서 넘겨받을 데이터 세트
//private String[] myDataset = {"첫번째 기사!", "두번째 기사!!!!", "세번째 기사!!!!", "네번째 기사!!!!"};
// Instantiate the RequestQueue.
//http 네트워크 통신 전, queue 초기화를 위한 변수 선언
    var queue: RequestQueue? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_main)
        //리사이클러뷰 캐스팅
        recyclerView = findViewById(R.id.my_recycler_view)
        Log.d("test4", "test 시작!4 ")
        // use this setting to improve performance if you know that changes
// in content do not change the layout size of the RecyclerView
//리사이클러(리스트) 레이아웃의 사이즈 고정
        recyclerView?.setHasFixedSize(true)
        // use a linear layout manager
        layoutManager = LinearLayoutManager(this)
        recyclerView?.setLayoutManager(layoutManager)
        /*로직
        * 1. 화면 로딩 -> news api 에서 데이터 받아오기
        * 2. 받아온 데이터 adapter 에 넘겨주기
        * 3. 어뎁터 : 받은 데이터 화면에 구성 및 세팅 */
//http 네트워크 통신 전, request queue 초기화
        queue = Volley.newRequestQueue(this)
        getNews()
    }

    fun getNews() {
        val url =
            "https://newsapi.org/v2/top-headlines?country=kr&apiKey=cff27a9bca8e40d1a24b59fd350073e5"
        // Request a string response from the provided URL.
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            Response.Listener<String?> { response ->
                // Display the first 500 characters of the response string.
                //textView.setText("Response is: "+ response.substring(0,500));
                Log.d("뉴스 들어오니", response)
                //json string json 객체 타입으로  변환
                //try catch error 처리
                try {
                    val obj = JSONObject(response)
                    Log.d("json obj 어떤 모양? ", obj.toString())
                    //json object 에서 articles 배열 가져오기
                    val articles = obj.getJSONArray("articles")
                    //news data 저장을 위한 배열 선언
                    val newslist: MutableList<NewsData> =
                        ArrayList<NewsData>()
                    //가져온 배열 for 문으로 펼치기
                    for (i in 0 until articles.length()) {
                        val article = articles.getJSONObject(i)
                        //매 반복 처리마다 newsdata 객체 생성 및 setter 통해서 값 대입
                        val newsdata = NewsData()
                        newsdata.setTitle(article.getString("title"))
                        newsdata.setContent(article.getString("content"))
                        newsdata.setUrlToImage(article.getString("urlToImage"))
                        newslist.add(newsdata)
                    }
                    // specify an adapter (see also next example)
                    mAdapter =
                        MyAdapter(newslist, this) //adapter 로 서버에서 받은 데이터 넘겨주기
                    recyclerView!!.adapter = mAdapter //adapter 연결
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }, Response.ErrorListener { error ->
                //textView.setText("That didn't work!");
                Log.d("nh", "error : " + error.message)
            })
        // Add the request to the RequestQueue.
        queue?.add(stringRequest)
    }
}
