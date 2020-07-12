package com.example.androidtestproject.application

import android.app.Application
import androidx.multidex.MultiDexApplication
import timber.log.Timber

class ApplicationInfo: MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()

        //init timber
        Timber.plant(Timber.DebugTree())
    }
}