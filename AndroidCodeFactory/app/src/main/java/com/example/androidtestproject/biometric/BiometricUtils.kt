package com.example.androidtestproject.biometric

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.hardware.fingerprint.FingerprintManagerCompat

class BiometricUtils {

    /*
    * check points
    * 1. android m 이상 : 지문인식 가능
    * 2. android p 이상 : biometric prompt 가능
    * 3. hardware 가 fingerprint 를 지원되는지
    * 4. 이미 등록된 지문이 있는지
    * 5. 권한은 제대로 부여되었는지
    * */

    fun isBiometricPromptAvailable(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.P
    }

    fun isSdkVersionSupported(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
    }

    fun isHardwareSupported(context: Context): Boolean {
        val fingerprintManager = FingerprintManagerCompat.from(context)
        return fingerprintManager.hasEnrolledFingerprints()
    }

    fun isPermissionGranted(context: Context): Boolean {
        return ActivityCompat.checkSelfPermission(context, Manifest.permission.USE_FINGERPRINT) == PackageManager.PERMISSION_GRANTED
    }
}