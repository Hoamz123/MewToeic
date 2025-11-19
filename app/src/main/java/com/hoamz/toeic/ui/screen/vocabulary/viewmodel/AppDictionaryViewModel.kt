package com.hoamz.toeic.ui.screen.vocabulary

import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hoamz.toeic.data.local.Word
import com.hoamz.toeic.data.remote.VocabDisplay
import com.hoamz.toeic.data.remote.Vocabulary
import com.hoamz.toeic.data.repository.DictionaryRepository
import com.hoamz.toeic.utils.Mapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AppDictionaryViewModel @Inject constructor(
    private val dictionaryRepository: DictionaryRepository
) : ViewModel(){

    private val _listVocab = MutableStateFlow<List<Vocabulary?>>(emptyList())
    val listVocab : StateFlow<List<Vocabulary?>> = _listVocab

    fun setUpDescriptionOfWords(words: List<Word>) {
        viewModelScope.launch(Dispatchers.IO) {
            val vocabs = mutableListOf<Vocabulary?>()
            for (word in words) {
                val vocab = dictionaryRepository.getDescriptionOfWordOnce(word.word)
                vocabs.add(vocab)
                _listVocab.value = vocabs.toList()
            }
        }
    }
}