package com.csi.flowexample

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class SampleApplication : Application() {

    override fun onCreate() {
        super.onCreate()
    }
}