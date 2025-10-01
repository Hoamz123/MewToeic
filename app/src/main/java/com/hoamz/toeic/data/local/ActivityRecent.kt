package com.hoamz.toeic.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hoamz.toeic.ui.screen.home.test.Answer

@Entity
data class ActivityRecent(
    @PrimaryKey(autoGenerate = true)
    val id : Long? = null,
    val nameTest : String,
    val numberAnswerCorrect : Int,
    val timeStamp : Long,//thoi gian
    val listQuestion : List<Question>,//danh sach cau hoi
    val listAnswer : List<Answer>//danh sach cau tra loi
)