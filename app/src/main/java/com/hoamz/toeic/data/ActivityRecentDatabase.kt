package com.hoamz.toeic.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.hoamz.toeic.data.dao.ActivityRecentDao
import com.hoamz.toeic.data.local.ActivityRecent
import com.hoamz.toeic.utils.EntityConverters

@Database(entities = [ActivityRecent::class], version = 1)
@TypeConverters(EntityConverters::class)
abstract class ActivityRecentDatabase : RoomDatabase(){
    abstract fun actRecentDao () : ActivityRecentDao
}