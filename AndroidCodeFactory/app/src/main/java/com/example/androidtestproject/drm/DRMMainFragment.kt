package com.example.androidtestproject.drm

import android.media.MediaDrm
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.androidtestproject.R
import java.lang.Exception
import java.util.*

/*
* UUID : https://developer.android.com/reference/java/util/UUID#randomUUID()
* MediaDrm : https://developer.android.com/reference/android/media/MediaDrm.html#public-constructors
* Drm : https://source.android.com/devices/drm
* */

class DRMMainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_d_r_m_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getDRM()
    }

    private fun getDRM() {
        val WIDEVINE_UUID = UUID.randomUUID()

        val wvDrm = try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                MediaDrm(WIDEVINE_UUID)
            } else {
                null
            }
        } catch (e: Exception) {
            //WIDEVINE is not available
            null
        }


        wvDrm!!.apply {
            val widevineId: ByteArray? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                wvDrm.getPropertyByteArray(MediaDrm.PROPERTY_DEVICE_UNIQUE_ID)
            } else {
                null
            }
            val encodedWidevineId = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Base64.getEncoder().encodeToString(widevineId).trim()
            } else {
            }
            Log.d("DRM", "Widevine ID:$encodedWidevineId")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                wvDrm.close()
            }
        }
    }


}
