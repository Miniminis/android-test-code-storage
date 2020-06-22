package com.example.androidtestproject.application

import android.app.Application
import timber.log.Timber

class ApplicationInfo: Application() {

    override fun onCreate() {
        super.onCreate()

        //init timber
        Timber.plant(Timber.DebugTree())
    }
}