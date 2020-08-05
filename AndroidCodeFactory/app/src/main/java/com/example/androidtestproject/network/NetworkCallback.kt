package com.example.androidtestproject.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi

class NetworkCallback(private val context: Context) {

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun getConnectionCallback() {
        var cm:ConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        var builder = NetworkRequest.Builder()
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)

        cm.registerNetworkCallback(builder.build(), mNetworkCallback)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    var mNetworkCallback = object: ConnectivityManager.NetworkCallback() {

        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            Toast.makeText(context, "available", Toast.LENGTH_LONG).show()
            Log.d("statuschk", "available")
        }

        override fun onLosing(network: Network, maxMsToLive: Int) {
            super.onLosing(network, maxMsToLive)
            Toast.makeText(context, "onLosing", Toast.LENGTH_LONG).show()
            Log.d("statuschk", "onLosing")

        }

        override fun onLost(network: Network) {
            super.onLost(network)
            Toast.makeText(context, "onLost", Toast.LENGTH_LONG).show()
            Log.d("statuschk", "onLost")

        }

        override fun onUnavailable() {
            super.onUnavailable()
            Toast.makeText(context, "onUnavailable", Toast.LENGTH_LONG).show()
            Log.d("statuschk", "onUnavailable")

        }
    }
}

/*
와이파이, 모바일 모두 listening 함을 확인
- wifi 연결되면 cellar 는 losing, lost 절차 밟게됨
* 2020-02-13 10:56:35.282 16966-17026/com.crepass.network D/statuschk: available
2020-02-13 10:57:22.011 16966-17026/com.crepass.network D/statuschk: onLost
2020-02-13 10:57:26.565 16966-17026/com.crepass.network D/statuschk: available
2020-02-13 10:57:29.835 16966-17026/com.crepass.network D/statuschk: available
2020-02-13 10:57:30.491 16966-17026/com.crepass.network D/statuschk: onLosing
2020-02-13 10:57:31.076 16966-17026/com.crepass.network D/statuschk: onLost
2020-02-13 10:57:45.735 16966-17026/com.crepass.network D/statuschk: onLost
2020-02-13 10:57:48.219 16966-17026/com.crepass.network D/statuschk: available
2020-02-13 10:58:12.027 16966-17026/com.crepass.network D/statuschk: onLost
2020-02-13 10:58:22.947 16966-17026/com.crepass.network D/statuschk: available
2020-02-13 10:58:36.976 16966-17026/com.crepass.network D/statuschk: available
2020-02-13 10:58:37.430 16966-17026/com.crepass.network D/statuschk: onLosing
2020-02-13 10:58:37.615 16966-17026/com.crepass.network D/statuschk: onLost
*
* */