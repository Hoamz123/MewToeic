package com.hoamz.toeic.ui.screen.vocabulary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hoamz.toeic.data.local.Word
import com.hoamz.toeic.data.remote.VocabDisplay
import com.hoamz.toeic.data.remote.Vocabulary
import com.hoamz.toeic.data.repository.DictionaryRepository
import com.hoamz.toeic.utils.Mapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class AppDictionaryViewModel @Inject constructor(
    private val dictionaryRepository: DictionaryRepository
) : ViewModel(){

    private var _vocabDisplay: MutableStateFlow<VocabDisplay> = MutableStateFlow(VocabDisplay())
    val vocabDisplay = _vocabDisplay.asStateFlow()//du lieu ben ngoai co the quan sat dc

    fun setUpDescriptionOfWords(word: Word) {
        viewModelScope.launch(Dispatchers.IO) {
            val vocab = dictionaryRepository.getDescriptionOfWordOnce(word.word)
            if(vocab != null) {
                val vocabDisplay = Mapper.fromVocabulary(vocab)
                _vocabDisplay.value = vocabDisplay
            }
        }
    }
}