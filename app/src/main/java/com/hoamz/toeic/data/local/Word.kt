package com.hoamz.toeic.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.DayOfWeek


@Entity
data class Word(
    @PrimaryKey(autoGenerate = true)
    val id : Long? = null,
    val word : String = "",
    val date : String = "",//2025/11/07
    var isMastered : Boolean = false,//da hoc hay chua
    var isReviewed : Boolean = false//da gap roi hay chua
)


data class DataChart(
    val cnt : Int,//dem
    val date : String//ngay thang
)

