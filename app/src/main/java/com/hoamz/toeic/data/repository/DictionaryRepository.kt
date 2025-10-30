package com.hoamz.toeic.data.repository

import com.hoamz.toeic.data.dao.DictionaryDao
import com.hoamz.toeic.data.remote.Vocabulary
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class DictionaryRepository @Inject constructor(
    private val dictionaryDao: DictionaryDao
) {
    fun getDescriptionOfWord(word : String) : Flow<Vocabulary?> = flow{
        try{
            val response = dictionaryDao.getDescriptionOfWord(word)
            if(response.isSuccessful){
                emit(response.body()?.first())
            }
            else emit(null)
        }
        catch (_ : Exception){
            emit(null)
        }
    }
}
