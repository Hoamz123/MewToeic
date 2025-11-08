package com.hoamz.toeic.base

import android.content.Context
import android.content.SharedPreferences
import com.hoamz.toeic.utils.Contains
import androidx.core.content.edit
import java.util.Calendar


object BaseSharePref {
    //bien khoi tao sau
    private lateinit var sharePref : SharedPreferences

    //khoi tao
    fun initialize(context: Context){
        sharePref = context.getSharedPreferences(Contains.NAME_DB, Context.MODE_PRIVATE)//che do private
    }

    //luu lai progress
    fun saveProgressSteak(progress : Int){
        sharePref.edit {
            putInt(Contains.STEAK_KEY, progress)
        }
    }

    //lay ra progress hien tai
    fun getProgressSteak() : Int {
        return sharePref.getInt(Contains.STEAK_KEY,0)
    }


    //lay ra so steak
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

    fun resetNumberSteak(){
        sharePref.edit {
            putInt(Contains.NUMBER_STEAK, 0)
        }
    }

    //khi luot du x(s) -> dung lai (hoan thanh 1 progress)
    fun onFinishedProgress() : Boolean{
        return sharePref.getBoolean(Contains.FINISHED,false)
    }

    //khi hoan thanh
    fun finishedProgress(){
        sharePref.edit{
            putBoolean(Contains.FINISHED,true)
        }
    }

    fun setUpProgress(){
        sharePref.edit{
            putBoolean(Contains.FINISHED,false)
        }
    }

    fun getDay() : Int{
        return sharePref.getInt("Day", Calendar.getInstance().get(Calendar.DAY_OF_YEAR))//mac dinh la ngay hom nay
    }

    //de phuc vu cho vc reset progress khi qua ngay moi
    fun saveDay(day : Int){
        sharePref.edit { putInt("Day", day) }
    }

    //luu lai tab previous de get du lieu khi tu man hinh khac quay lai
    fun saveTabPrevious(indexTab : Int){
        val tabName = when(indexTab){
            0 -> {
                Contains.ALL
            }
            1 -> {
                Contains.CORRECT
            }
            else -> {
                Contains.WRONG
            }
        }
        sharePref.edit{
            putString(Contains.TAB,tabName)
        }
    }

    //lay ra tabName previous
    fun getTabPrevious() : String {
        var tab =  sharePref.getString(Contains.TAB,Contains.ALL)
        if(tab.isNullOrBlank()) tab = Contains.ALL
        return tab
    }

    //save trang thai remind
    fun saveRemind(){
        sharePref.edit {
            putBoolean(Contains.STATE_REMIND,true)
        }
    }

    //huy trang thai remind
    fun cancelRemind(){
        sharePref.edit {
            putBoolean(Contains.STATE_REMIND,false)
        }
        sharePref.edit{
            putInt(Contains.WORDS,0)
        }
        sharePref.edit{
            putInt(Contains.TIME_PERIOD,0)
        }
    }

    //check nhac nho hay ko
    fun checkRemind() : Boolean {
        return sharePref.getBoolean(Contains.STATE_REMIND,false)
    }

    //luu lai khi so luong tu muon nhac nho
    fun saveNumberWordNeedRemind(cnt : Int){
        sharePref.edit{
            putInt(Contains.WORDS,cnt)
        }
    }

    //lay ra so luong tu muon nhac nho
    fun getNumberWordNeedRemind() : Int{
        return sharePref.getInt(Contains.WORDS,0)
    }

    //luu thoi gian nhac lap lai nhac nho
    fun savePeriod(time : Int){
        sharePref.edit{
            putInt(Contains.TIME_PERIOD,time)
        }
    }

    //lay ra thoi gian da dat trc do
    fun getPeriod() : Int{
        return sharePref.getInt(Contains.TIME_PERIOD,0)
    }
}