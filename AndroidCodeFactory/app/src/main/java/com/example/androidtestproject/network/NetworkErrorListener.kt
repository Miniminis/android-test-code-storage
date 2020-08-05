package com.example.androidtestproject.network

import android.content.Context
import android.content.DialogInterface
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class NetworkErrorListener(private var mContext: Context) {

    private var nextListener: ReConnectListenerInterface? = null

    fun setReconnectListener(reConnectListener: ReConnectListenerInterface) {
        this.nextListener = reConnectListener
    }

    fun finishActivity() {
        (mContext as AppCompatActivity).finish()
    }

    fun setErrorCode(errorCode: Int) {
        when(errorCode) {
            -1 -> {
                Log.d("networkError", "에러코드 : -1")
                val alertDialog: AlertDialog? = mContext?.let {
                    val builder = AlertDialog.Builder(it)
                    builder.apply {
                        setPositiveButton("재접속",
                            DialogInterface.OnClickListener { dialog, id ->
                                // User clicked OK button
                                nextListener?.setReconnectListener()
                            })
                        setNegativeButton("종료",
                            DialogInterface.OnClickListener { dialog, id ->
                                // User cancelled the dialog
                                dialog.dismiss()
                                finishActivity()
                            })
                    }

                    // Create the AlertDialog
                    builder.create()
                }

                if(!alertDialog!!.isShowing) {
                    alertDialog.show()
                }
            }
            else -> {
                Log.d("networkError", "에러코드 : not found")
            }
        }
    }
}