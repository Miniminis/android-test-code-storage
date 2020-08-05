package com.example.androidtestproject.network

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.androidtestproject.R
import kotlinx.android.synthetic.main.activity_network_main.*

class NetworkMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_network_main)

        //데이터 버그 테스트
        var str1 = "1720574"
        var str2 = "1400000"
        var str3 = "0"

//        Log.d("mhhhh", "result : ${str1-str2 - str3}")
        Log.d("mhhhh", "result : ${str1.toLong() - str2.toLong() - str3.toLong()}")


        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.layout_main, NetworkTestFinalFragment())
        transaction.commit()

        btnChkConnection.setOnClickListener(mClickListener)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            NetworkCallback(this).getConnectionCallback()   //activity create 시에 정의해주어야함.
        }

    }

    private val mClickListener = View.OnClickListener {
        when(it.id) {
            R.id.btnChkConnection -> {
                var status: Int = NetworkStatus.getConnection(this)
                when(status) {
                    1 -> {
                        textView.text = "와이파이"
                    }
                    2-> {
                        textView.text = "모바일"
                    }
                    3-> {
                        textView.text = "테블릿에서 연결됨"
                    }
                    4 -> {
                        textView.text  = "연결이 안됨"
                    }
                    else -> {}
                }
            }

            R.id.btnChkConnectionCallback -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    NetworkCallback(this).getConnectionCallback()   //이렇게는 동작 안함
                }
            }
        }
    }
}
