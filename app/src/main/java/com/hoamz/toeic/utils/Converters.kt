package com.hoamz.toeic.utils

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.hoamz.toeic.data.local.Question
import com.hoamz.toeic.data.remote.Phonetic
import com.hoamz.toeic.ui.screen.home.test.Answer

class EntityConverters {
    private val gson = Gson()
    @TypeConverter
    fun fromListQuestionToGson(listQuestion : List<Question>) : String{
        return gson.toJson(listQuestion)
    }//chuyen doi list ve gson -> dang string -> luu vao room

    //chuyen nguoc lai tu gson ve List<Question>
    @TypeConverter
    fun fromGsonToListQuestion(data : String?) : List<Question>{
        if(data.isNullOrEmpty()) return emptyList()
        //dinh nghia kieu tra ve
        val type = object : TypeToken<List<Question>>() {}.type
        return gson.fromJson(data,type)
    }

    @TypeConverter
    fun fromListAnswerToGson(listAnswer : List<Answer>) : String{
        return gson.toJson(listAnswer)
    }//chuyen doi list ve gson -> dang string -> luu vao room

    //chuyen nguoc lai tu gson ve List<Answer>
    @TypeConverter
    fun fromGsonToListAnswer(data : String?) : List<Answer>{
        if(data.isNullOrEmpty()) return emptyList()
        //dinh nghia kieu tra ve
        val type = object : TypeToken<List<Answer>>() {}.type
        return gson.fromJson(data,type)
    }

    @TypeConverter
    fun fromListPhoneticsToJson(phonetics: List<Phonetic>) : String{
        return gson.toJson(phonetics)
    }

    @TypeConverter
    fun fromGsonToListPhonetics(phonetics : String?) : List<Phonetic>{
        if(phonetics.isNullOrEmpty()) return emptyList()
        val type = object : TypeToken<List<Phonetic>>() {}.type
        return gson.fromJson(phonetics,type)
    }
}
