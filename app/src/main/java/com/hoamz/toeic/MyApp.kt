package com.hoamz.toeic

import android.app.Application
import com.hoamz.toeic.base.BaseSharePref
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApp : Application(){
    override fun onCreate() {
        super.onCreate()
        BaseSharePref.initialize(this)
    }
}