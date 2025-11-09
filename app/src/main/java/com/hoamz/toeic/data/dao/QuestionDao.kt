package com.hoamz.toeic.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hoamz.toeic.data.local.QuestionStar
import kotlinx.coroutines.flow.Flow

@Dao
interface QuestionDao {
    //insert
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuestionStar(questionStar: QuestionStar)

    //del
    @Delete
    suspend fun deleteQuestionStar(questionStar: QuestionStar)

    //get
    @Query("select * from questionstar")
    fun getAllQuestionStar() : Flow<List<QuestionStar>>
}
