package com.hoamz.toeic.di

import android.content.Context
import androidx.room.Room
import com.hoamz.toeic.data.ActivityRecentDatabase
import com.hoamz.toeic.data.dao.ActivityRecentDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RoomModule{

    @Provides
    @Singleton//dambao instance duoc hilt tai su dung trnog toan bo app
    //hilt chi tao duy nhat 1 instance cho app
    fun provideActivityRecentDatabase(@ApplicationContext context: Context) : ActivityRecentDatabase{
        return Room.databaseBuilder(
            context = context,
            klass = ActivityRecentDatabase::class.java,
            name = "activity_recent_database"
        ).build()
    }


    @Provides
    @Singleton
    fun provideActRecentDao(activityRecentDatabase: ActivityRecentDatabase) : ActivityRecentDao{
        return activityRecentDatabase.actRecentDao()
    }

}