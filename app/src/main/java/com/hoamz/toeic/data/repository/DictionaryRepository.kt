package com.hoamz.toeic.data.repository

import com.hoamz.toeic.data.dao.DictionaryDao
import com.hoamz.toeic.data.remote.Meaning
import com.hoamz.toeic.data.remote.Phonetic
import com.hoamz.toeic.data.remote.Vocabulary
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import kotlin.collections.forEach


class DictionaryRepository @Inject constructor(
    private val dictionaryDao: DictionaryDao
) {
    suspend fun getDescriptionOfWordOnce(word: String): Vocabulary? {
        return try {
            val response = dictionaryDao.getDescriptionOfWord(word)
            if (response.isSuccessful) {
                val vocabSource = response.body()?.first()
                val phonetics = vocabSource?.phonetics?.filter {
                    (it.audio?.isNotEmpty() == true) || (it.text?.isNotEmpty() == true)
                } ?: emptyList()
                val meanings = vocabSource?.meanings?.filter { !it.partOfSpeech.isNullOrEmpty() && !it.definitions.isNullOrEmpty() } ?: emptyList()
                Vocabulary(vocabSource?.word, vocabSource?.phonetic, phonetics, meanings)
            } else null
        } catch (_: Exception) { null }
    }
}
