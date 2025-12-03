package com.hoamz.toeic.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hoamz.toeic.data.remote.Phonetic

@Entity
data class VocabularyEntity(
    @PrimaryKey(autoGenerate = true)
    val id : Long? = null,//lay cai nay cho display
    val word : String = "",//text cua tu
    val phonetics : List<Phonetic> = emptyList(),//audio cua word
    val partOfSpeech : String = "",//tu loai
    val definition : String = "",
    val date : String = "",//2025/11/07
    var isMastered : Boolean = false,//da hoc hay chua
    var isReviewed : Boolean = false//da gap roi hay chua(cai nay se dc set = true khi user vao man hinh chi tiet cua tu do)
)

data class DataChart(
    val cnt : Int,//dem
    val date : String//ngay thang
)

