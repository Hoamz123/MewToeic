package com.hoamz.toeic.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hoamz.toeic.data.dao.WordDao
import com.hoamz.toeic.data.local.Word


@Database(entities = [Word::class], version = 1)
abstract class WordsDatabase : RoomDatabase() {
    abstract fun acSelectWord() : WordDao
}