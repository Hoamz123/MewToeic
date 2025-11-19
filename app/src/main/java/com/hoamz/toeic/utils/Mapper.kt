package com.hoamz.toeic.utils

import com.hoamz.toeic.data.remote.VocabDisplay
import com.hoamz.toeic.data.remote.Vocabulary

object Mapper {
    fun fromVocabulary(vocabulary: Vocabulary?) : VocabDisplay{
        return VocabDisplay(
            word = vocabulary?.word ?: "",
            phonetic = vocabulary?.phonetic ?: "",
            des = vocabulary?.meanings?.get(0)?.definitions?.get(0)?.definition ?: "",
            phonetics = vocabulary?.phonetics,
            partOfSpeech = vocabulary?.meanings?.get(0)?.partOfSpeech ?: "   "
        )
    }
}