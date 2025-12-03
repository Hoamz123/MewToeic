package com.hoamz.toeic.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.hoamz.toeic.data.dao.VocabularyDao
import com.hoamz.toeic.data.local.VocabularyEntity
import com.hoamz.toeic.utils.EntityConverters

@Database(entities = [VocabularyEntity::class], version = 1)
@TypeConverters(EntityConverters::class)
abstract class VocabularyDb() : RoomDatabase() {
    abstract fun vocabularyDao() : VocabularyDao
}