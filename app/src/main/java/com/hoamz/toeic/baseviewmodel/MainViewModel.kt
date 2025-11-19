package com.hoamz.toeic.baseviewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.hoamz.toeic.data.local.ActivityRecent
import com.hoamz.toeic.data.local.Question
import com.hoamz.toeic.data.local.QuestionStar
import com.hoamz.toeic.data.local.Word
import com.hoamz.toeic.data.repository.ActivityRecentRepository
import com.hoamz.toeic.data.repository.LoadQuestionRepository
import com.hoamz.toeic.data.repository.QuestionStarRepository
import com.hoamz.toeic.ui.screen.home.test.Answer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val loadQuestionRepository: LoadQuestionRepository,
    private val activityRecentRepository: ActivityRecentRepository,
    private val questionStarRepository: QuestionStarRepository
) : ViewModel() {
    private val _testNumber = MutableStateFlow(0)
    val testNumber : StateFlow<Int> = _testNumber

    fun setTestNumber(number : Int){
        _testNumber.value = number
    }

    private val _listQuestion = MutableStateFlow<List<Question>>(emptyList())
    val listQuestion : StateFlow<List<Question>> = _listQuestion

    fun freshQuestion(number : Int){
        viewModelScope.launch {
            //gam moi
            _listQuestion.value = loadQuestionRepository.loadTestQuestion(number)
        }
    }

    //truyen tu Home qua khi user clicked vao card recent
    fun passQuestionRecent(listQuestion : List<Question>) {
        //gam moi
        _listQuestion.value = listQuestion
    }


    private val _listDefaultAnswerQuestion = MutableStateFlow<List<Answer>>(emptyList())
    val listDefaultAnswerQuestion : StateFlow<List<Answer>> = _listDefaultAnswerQuestion

    fun freshDefaultAnswer(number : Int){
        viewModelScope.launch {
            _listDefaultAnswerQuestion.value = loadQuestionRepository.loadAnswerDefaultQuestion(number)
        }
    }

    private val _isTestMode = MutableStateFlow(false)
    val isTestMode : StateFlow<Boolean> = _isTestMode

    private val _timeTest = MutableStateFlow(2000)
    val timeTest : StateFlow<Int> = _timeTest

    fun setTimeDoTest(time : Int){
        _timeTest.value = time
    }

    fun turnOnTestMode(){
        _isTestMode.value = true
    }

    fun turnOffTestMode(){
        _isTestMode.value = false
        _timeTest.value = 2
    }
    /*

    //tranh case user khong chon dap an ma da nop -> null

    ys tuong khac phuc
    tao ra 1 list da co du 30 cau tra loi {index question,index Clicked User = -1,indx Correct}
    khi user click thi chi can dat lai clicked User -> giai quyet dc van de user ko chon
    -- them tinh nang countdown  _> den h tu dong nop bai or user co the nop truocn

     */

    //insert act new
    fun insertNewActivity(activityRecent: ActivityRecent){
        viewModelScope.launch {
            activityRecentRepository.insertNewActRecent(activityRecent)
        }
    }
    //get
    val activitiesRecent : StateFlow<List<ActivityRecent>> =
        activityRecentRepository.getAllActivitiesRecent()
        .stateIn(viewModelScope, SharingStarted.Lazily,emptyList())


    //method star question
    //insert
    fun insertQuestionStar(questionStar: QuestionStar){
        viewModelScope.launch {
            questionStarRepository.insertQuestionStar(questionStar)
        }
    }

    private val _starQuestion = MutableStateFlow<QuestionStar?>(null)
    val starQuestion : StateFlow<QuestionStar?> = _starQuestion

    fun sendQuestionStar(questionStar: QuestionStar){
        _starQuestion.value = questionStar
    }


    //del
    fun deleteQuestionStar(questionStar: QuestionStar){
        viewModelScope.launch {
            questionStarRepository.deleteQuestionStar(questionStar)
        }
    }

    //get
    val questionStars : StateFlow<List<QuestionStar>> =
        questionStarRepository.getAllQuestionStar()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000),emptyList())
}