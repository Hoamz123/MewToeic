package com.hoamz.toeic.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class QuestionStar(
    @PrimaryKey
    val question : String,
    val A : String,
    val B : String,
    val C : String,
    val D : String,
    val answer : String,
    val explain : String,
)