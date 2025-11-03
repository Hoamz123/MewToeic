package com.hoamz.toeic.di

import android.app.Application
import android.content.Context
import com.hoamz.toeic.data.repository.LoadQuestionRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object QuestionModule {
    @Provides
    @Singleton
    fun provideLoadQuestion(@ApplicationContext context: Context) : LoadQuestionRepository{
        return LoadQuestionRepository(context)
    }
}