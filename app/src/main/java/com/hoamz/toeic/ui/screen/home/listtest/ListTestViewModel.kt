package com.hoamz.toeic.ui.screen.home.listtest

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class ListTestViewModel  : ViewModel(){
    private val _isShowListTestCurrent = MutableStateFlow(false)
    val isShowListTestCurrent : StateFlow<Boolean> = _isShowListTestCurrent

    fun controllerStateListCurrent(state : Boolean){
        _isShowListTestCurrent.value = state
    }
}