package com.example.androidtestproject.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

object NetworkStatus {
    /* utility 화
    * https://youngest-programming.tistory.com/32
    *
    * ConnectivityManager.TYPE.WIMAX : 테블릿의 인터넷 연결 유형
    * https://ababqq.tistory.com/entry/%EC%95%88%EB%93%9C%EB%A1%9C%EC%9D%B4%EB%93%9C-%EB%84%A4%ED%8A%B8%EC%9B%8C%ED%81%AC-%EC%83%81%ED%83%9C-%EC%B2%B4%ED%81%AC-getNetworkInfo-getActiveNetworkInfo
    * */

    fun getConnection(context: Context): Int {
        var cm: ConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        var networkInfo: NetworkInfo? = cm.activeNetworkInfo

        val flagWifi = 1
        val flagMobile = 2
        val flagWimax = 3
        val flagNoConnect = 4

        if(networkInfo != null) {
            var type:Int = networkInfo.type

            when (type) {
                ConnectivityManager.TYPE_MOBILE -> return flagMobile
                ConnectivityManager.TYPE_WIFI -> return flagWifi
                ConnectivityManager.TYPE_WIMAX -> return flagWimax
            }
        }
        return flagNoConnect
    }
}