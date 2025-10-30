package com.hoamz.toeic.ui.screen.vocabulary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hoamz.toeic.data.remote.Vocabulary
import com.hoamz.toeic.data.repository.DictionaryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


@HiltViewModel
class AppDictionaryViewModel @Inject constructor(
    private val dictionaryRepository: DictionaryRepository
) : ViewModel(){

    fun getDescriptionOfWord(word : String) : StateFlow<Vocabulary?>{
        return dictionaryRepository.getDescriptionOfWord(word)
            .stateIn(viewModelScope,
                SharingStarted.WhileSubscribed(2000),
            null)
    }
}