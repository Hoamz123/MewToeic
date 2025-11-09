package com.hoamz.toeic.data.repository

import com.hoamz.toeic.data.dao.QuestionDao
import com.hoamz.toeic.data.local.QuestionStar
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class QuestionStarRepository @Inject constructor(
    private val questionDao: QuestionDao
) {
    //insert
    suspend fun insertQuestionStar(questionStar: QuestionStar){
        questionDao.insertQuestionStar(questionStar)
    }

    //del
    suspend fun deleteQuestionStar(questionStar: QuestionStar){
        questionDao.deleteQuestionStar(questionStar)
    }

    //get
    fun getAllQuestionStar() : Flow<List<QuestionStar>>{
        return questionDao.getAllQuestionStar()
    }
}
