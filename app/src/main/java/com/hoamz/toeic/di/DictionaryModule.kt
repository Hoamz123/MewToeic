package com.hoamz.toeic.di

import com.hoamz.toeic.data.dao.DictionaryDao
import com.hoamz.toeic.utils.Contains
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DictionaryModule {

    @Provides
    @Singleton
    fun provideDictionaryApi() : Retrofit{
        return Retrofit.Builder()
            .baseUrl(Contains.BASE_URL_DictionaryAPI)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideDictionaryDao(retrofit: Retrofit) : DictionaryDao{
        return retrofit.create(DictionaryDao::class.java)
    }
}
