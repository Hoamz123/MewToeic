package com.hoamz.toeic.data.repository

import android.app.Application
import android.content.Context
import com.hoamz.toeic.data.local.Question
import com.hoamz.toeic.ui.screen.home.test.Answer
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LoadQuestionRepository @Inject constructor(
    private val context: Context
) {
    private suspend fun loadJsonFronAssets(
        nameFile : String
    ) : String {
        return withContext(Dispatchers.IO) {
            context.assets.open(nameFile).bufferedReader().use { it.readText() }
        }
    }

    suspend fun loadQuestion(
        nameFile : String
        ) : List<Question>{
        return withContext(Dispatchers.IO){
            try{
                val json = loadJsonFronAssets(nameFile)
                val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
                val type = Types.newParameterizedType(
                    Map::class.java,
                    String::class.java,
                    Question::class.java)
                val adapter = moshi.adapter<Map<String, Question>>(type)
                val mapQuestion : Map<String, Question>? = adapter.fromJson(json)
                mapQuestion?.values?.toList() ?: emptyList()
            }
            catch (_ : Exception){
                emptyList()
            }
        }
    }

    suspend fun loadTestQuestion(number : Int) : List<Question>{
        val nameFile = "test_${number}.json"
        return loadQuestion(nameFile)
    }

    suspend fun loadAnswerDefaultQuestion(number : Int) : List<Answer> {
        val nameFile = "test_${number}.json"
        val list : List<Question>  = loadQuestion(nameFile)
        val defaultList  = mutableListOf<Answer>()
        list.forEachIndexed {index, question ->
            val indexCorrect = if(question.A == question.answer) 0
            else if(question.B == question.answer) 1
            else if(question.C == question.answer) 2
            else 3
            defaultList.add(Answer(index,-1,indexCorrect))
        }
        return defaultList.toList()
    }
}