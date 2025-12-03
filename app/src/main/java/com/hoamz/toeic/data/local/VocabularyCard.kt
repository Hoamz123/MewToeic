package com.hoamz.toeic.data.local

import com.hoamz.toeic.data.remote.Definition
import com.hoamz.toeic.data.remote.Phonetic

//du lieu hien thi tren flashcard
data class VocabularyCard(
    val id : Long? = null,//lay cai nay cho display
    val word : String = "",//lay cai nay
    val audios : List<String> = emptyList(),//audio cua tu
    val definition: String = "",
    val partOfSpeech : String = ""
)