package com.hoamz.toeic.di

import android.content.Context
import androidx.room.Room
import com.hoamz.toeic.data.ActivityRecentDatabase
import com.hoamz.toeic.data.QuestionStarDb
import com.hoamz.toeic.data.WordsDatabase
import com.hoamz.toeic.data.dao.ActivityRecentDao
import com.hoamz.toeic.data.dao.QuestionDao
import com.hoamz.toeic.data.dao.WordDao
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
    @Singleton//dam bao instance duoc hilt tai su dung trnog toan bo app
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
    fun provieStoreWords(@ApplicationContext context: Context) : WordsDatabase{
        return Room.databaseBuilder(
            context =  context,
            klass = WordsDatabase::class.java,
            name = "words_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideActRecentDao(activityRecentDatabase: ActivityRecentDatabase) : ActivityRecentDao{
        return activityRecentDatabase.actRecentDao()
    }

    @Provides
    @Singleton
    fun provideAcWordsDao(wordsDatabase: WordsDatabase) : WordDao{
        return wordsDatabase.acSelectWord()
    }

    @Provides
    @Singleton
    fun provideQuestionStarDb(@ApplicationContext context: Context) : QuestionStarDb{
        return Room.databaseBuilder(
            context = context,
            klass = QuestionStarDb::class.java,
            name = "question_star_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideQuestionDao(questionStarDb: QuestionStarDb) : QuestionDao{
        return questionStarDb.QuestionDao()
    }
}