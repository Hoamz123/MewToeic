package com.hoamz.toeic.ui.screen.home.test

import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class TestViewModel @Inject constructor() : ViewModel(){
    fun countNumberCorrectAnswer(list : List<Answer>) : Int{
        var cnt : Int = 0
        list.forEach { answer ->
            if(answer.indexUserClicked == answer.indexCorrectAnswer) cnt++
        }
        return cnt
    }
    private val _listAnswer = MutableStateFlow<List<Answer>>(emptyList())
    val listAnswer : StateFlow<List<Answer>> = _listAnswer//goi list nay la lay all
    private val _listCorrectAnswer = MutableStateFlow<List<Answer>>(emptyList())
    val listCorrectAnswer : StateFlow<List<Answer>> = _listCorrectAnswer//goi list nay la lay all dung
    private val _listWrongAnswer = MutableStateFlow<List<Answer>>(emptyList())
    val listWrongAnswer : StateFlow<List<Answer>> = _listWrongAnswer//goi list nay la lay all sai

    fun setUpListAnswer(defaultList : List<Answer>){
        _listAnswer.value = emptyList()
        _listAnswer.value = defaultList
    }
    //goi ham nay moi khi user chon xong 1 cau
//    fun addAfterUserAnswer(answer: Answer){
//        _listAnswer.value = _listAnswer.value + answer
//    }

    fun addAfterUserAnswer(answer: Answer){
        _listAnswer.update { it->
            it.toMutableList().also { it[answer.indexQuestion] = answer }
        }
    }

    //lay ra tat ca nhung chi nhung cau dung
    fun getAllAnswerByCondition(){
        _listWrongAnswer.value =  emptyList()
        _listCorrectAnswer.value =  emptyList()
        _listAnswer.value.forEach { answer ->
            if(answer.indexUserClicked == answer.indexCorrectAnswer){
                //neu dung
                _listCorrectAnswer.value = _listCorrectAnswer.value + answer
            }
            else if(answer.indexUserClicked != -1){
                //sai
                _listWrongAnswer.value = _listWrongAnswer.value + answer
            }
        }
    }

    fun clearDataAnswer(){
        _listAnswer.value = emptyList()
        _listWrongAnswer.value =  emptyList()
        _listCorrectAnswer.value =  emptyList()
    }

    //check only click
    private val _answeredQuestions = MutableStateFlow<Map<Int, Boolean>>(emptyMap())
    val answeredQuestions : StateFlow<Map<Int, Boolean>> = _answeredQuestions

    fun answeredQuestion(numberQuestion : Int){
        //neu chua ton tai
        if(!_answeredQuestions.value.containsKey(numberQuestion)){
            _answeredQuestions.value = _answeredQuestions.value.toMutableMap().apply {
                put(numberQuestion,true)
            }
        }
    }
}