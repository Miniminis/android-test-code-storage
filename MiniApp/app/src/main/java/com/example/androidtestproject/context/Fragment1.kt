package com.example.androidtestproject.context


import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.androidtestproject.BuildConfig
import com.example.androidtestproject.R
import com.google.gson.Gson
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.io.Serializable
import java.util.concurrent.TimeUnit


class Fragment1 : Fragment() {

    override fun onAttach(context: Context) {
        super.onAttach(context)
        isActivityThere()
        Log.d("mhh", "context2 : $context")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Log.d("mhh", "context1 : $context")
        isActivityThere()
        return inflater.inflate(R.layout.fragment_fragment1, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        isActivityThere()
        Log.d("mhh", "context3 : $context")

        gitAPI()
    }

    private fun isActivityThere() {
        if(activity == null) {
            Log.d("mhh", "activity is null. current context is $context")
        }
    }

    @SuppressLint("CheckResult")
    fun gitAPI() {

        val appInfoService = GitRetrofitClient.retrofit.create(GitApiService::class.java)

        val source = appInfoService.getGitInfo()
        source
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                Log.d("mhh", "4 $it")
            }
            .doOnTerminate {
                Log.d("mhh", "5")
            }
            .subscribe({ response ->
                Log.d("mhh", "6 $response")

                getString(R.string.app_name)

                Log.d("mhh", "9 activity here - context now : $context")

                if(context == null) {
                    Log.d("mhh", "8 no activity here - context now : $context")
                }

            }, {
                Log.d("mhh", "7 $it")
                // 2020-03-02 16:32:05.151 14570-14570/com.crepass.contexttest
                // D/mhh: 7 java.lang.IllegalStateException: Fragment Fragment1{52895e9 (368f6769-63e6-42f0-85ff-1819436a065c)} not attached to a context.
            })
    }

    object GitRetrofitClient {

        val retrofit: Retrofit

        private val CONNECTION_TIMEOUT = 10
        private val READ_TIMEOUT = 30
        private val WRITE_TIMEOUT = 30

        val baseUrl = "https://api.github.com"

        init {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
            val connectionPool = ConnectionPool()
            val okHttpClient = OkHttpClient.Builder()
                .connectTimeout(CONNECTION_TIMEOUT.toLong(), TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT.toLong(), TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT.toLong(), TimeUnit.SECONDS)
                .connectionPool(connectionPool)
                .addInterceptor(loggingInterceptor)
                .build()

            retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(Gson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .baseUrl(baseUrl)
                .build()
        }
    }


    interface GitApiService {
        @GET("/users/miniminis")
        fun getGitInfo(): Observable<GitModel>
    }

    data class GitModel(val login: String, val id: String): Serializable

}
