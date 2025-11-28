package com.hoamz.toeic.data.dao

import com.hoamz.toeic.data.remote.Vocabulary
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

//link API : https://api.dictionaryapi.dev/api/v2/entries/en/{word}

interface DictionaryDao {
    //endpoint en/{word}
    @GET("en/{word}")
    suspend fun getDescriptionOfVocab(@Path("word") word : String) : Response<List<Vocabulary>>//tra ve noi dung cua 1 tu
}