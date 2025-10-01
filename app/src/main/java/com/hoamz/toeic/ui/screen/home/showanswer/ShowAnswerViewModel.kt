package com.hoamz.toeic.ui.screen.home.showanswer

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ShowAnswerViewModel @Inject constructor() : ViewModel(){
    private val _numberClicked = MutableStateFlow(-1)
    val numberClicked : StateFlow<Int> = _numberClicked

    //dang o man hinh show Answer -> click vao 1 cau nao do -> truyen cau do vao day
    fun onClickedAnswer(numberAnswer : Int){
        _numberClicked.value = -1
        _numberClicked.value = numberAnswer
    }
}