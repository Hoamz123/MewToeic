package com.hoamz.toeic.base

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import androidx.compose.ui.unit.Constraints
import com.hoamz.toeic.utils.Contains
import androidx.core.content.edit
import java.util.Calendar
import java.util.Date


object BaseSharePref {
    private lateinit var sharePref : SharedPreferences

    fun initialize(context: Context){
        sharePref = context.getSharedPreferences("db_local", Context.MODE_PRIVATE)//che do private
    }

    fun saveProgressSteak(progress : Int){
        sharePref.edit {
            putInt(Contains.STEAK_KEY, progress)
        }
    }

    fun getProgressSteak() : Int {
        return sharePref.getInt(Contains.STEAK_KEY,0)
    }


    fun getNumberSteak() : Int {
       return sharePref.getInt(Contains.NUMBER_STEAK,0)
    }


    //moi khi save thi co them 1 steak
    fun saveNumberSteak(){
        val steaks : Int = getNumberSteak() + 1
        sharePref.edit {
            putInt(Contains.NUMBER_STEAK, steaks)
        }
    }

    fun getDay() : Int{
        return sharePref.getInt("Day", Calendar.getInstance().get(Calendar.DAY_OF_YEAR))//mac dinh la ngay hom nay
    }

    //de phuc vu cho vc reset progress khi qua ngay moi
    fun saveDay(day : Int){
        sharePref.edit { putInt("Day", day) }
    }
}