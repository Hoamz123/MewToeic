package com.hoamz.toeic

import android.app.Application
import android.util.Log
import com.hoamz.toeic.base.BaseSharePref
import dagger.hilt.android.HiltAndroidApp
import java.util.Calendar

@HiltAndroidApp
class MyApp : Application(){
    override fun onCreate() {
        super.onCreate()
        BaseSharePref.initialize(this)
        //lay ra ngay hom nay
        val today = Calendar.getInstance().get(Calendar.DAY_OF_YEAR) + 1

        //lay ra ngay da luu trong sharePref
        val daySaved = BaseSharePref.getDay()

//        val flag = BaseSharePref.finishedProgress()

//        Log.e("DATE","{$daySaved}_{$today}_{$flag}")

        //kiem tra neu khac nhau thi chung to qua ngay moi roi -> reset lai progress
        if(daySaved != today){
            BaseSharePref.saveDay(today)
            BaseSharePref.saveProgressSteak(0)
            BaseSharePref.setUpProgress()
        }
    }
}