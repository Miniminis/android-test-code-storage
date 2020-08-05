package com.example.androidtestproject.network


import android.annotation.TargetApi
import android.content.Context
import android.net.*
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.androidtestproject.R
import com.example.androidtestproject.databinding.FragmentNetworkTestFinalBinding


class NetworkTestFinalFragment : Fragment() {

    private lateinit var binding: FragmentNetworkTestFinalBinding

    private lateinit var networkErrorListener: NetworkErrorListener

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_network_test_final, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnChkConnection01.setOnClickListener(mClickListener)
        binding.btnChkConnection02.setOnClickListener(mClickListener)
        binding.btnChkConnection03.setOnClickListener(mClickListener)
        binding.btnChkConnection04.setOnClickListener(mClickListener)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        networkErrorListener = NetworkErrorListener(this.activity!!)
        networkErrorListener.setReconnectListener(mReconnectListener)
    }

    private val mClickListener = View.OnClickListener {
        when(it.id) {
            R.id.btnChkConnection01 -> { chkConnection01() }
            R.id.btnChkConnection02 -> { chkConnection02() }
            R.id.btnChkConnection03 -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    chkConnection03()
                }
            }
            R.id.btnChkConnection04 -> { chkConnection04() }
        }
    }



    fun chkConnection01() {
        val cm = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo      //depreciated
        val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true       ////depreciated

        Toast.makeText(context, "is Connected? :: $isConnected", Toast.LENGTH_SHORT).show()
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun chkConnection02() {
        val cm:ConnectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val nc = cm.getNetworkCapabilities(cm.activeNetwork)
        if(nc == null) {
            Toast.makeText(context, "인터넷 capability 없음", Toast.LENGTH_SHORT).show()
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun chkConnection03() {
        var cm:ConnectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        var builder = NetworkRequest.Builder()
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)

        cm.registerNetworkCallback(builder.build(), mNetworkCallback)
    }

    var mNetworkCallback = @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    object: ConnectivityManager.NetworkCallback() {

        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            Log.d("statuschk", "available")
        }

        override fun onLosing(network: Network, maxMsToLive: Int) {
            super.onLosing(network, maxMsToLive)
            Log.d("statuschk", "onLosing")
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            Log.d("statuschk", "onLost")
        }

        override fun onUnavailable() {
            super.onUnavailable()
            Log.d("statuschk", "onUnavailable")
        }
    }

    fun chkConnection04() {
        connection()
        networkErrorListener.setErrorCode(-1)
    }

    fun connection() {
        Toast.makeText(context, "connection started", Toast.LENGTH_SHORT).show()
    }

    val mReconnectListener = object : ReConnectListenerInterface {
        override fun setReconnectListener() {
            connection()
        }
    }
}
