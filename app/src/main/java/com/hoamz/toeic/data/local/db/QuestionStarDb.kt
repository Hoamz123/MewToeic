package com.hoamz.toeic.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hoamz.toeic.data.dao.QuestionDao
import com.hoamz.toeic.data.local.QuestionStar

@Database(entities = [QuestionStar::class], version = 1)
abstract class QuestionStarDb : RoomDatabase(){
    abstract fun QuestionDao() : QuestionDao
}