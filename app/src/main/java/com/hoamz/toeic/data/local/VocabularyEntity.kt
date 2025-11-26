package com.hoamz.toeic.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hoamz.toeic.data.remote.Phonetic

@Entity
data class VocabularyEntity(
    @PrimaryKey(autoGenerate = false)
    val word : String = "",
    val phonetics : List<Phonetic> = emptyList(),
    val partOfSpeech : String = "",
    val definition : String = "",
)
