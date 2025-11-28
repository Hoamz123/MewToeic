package com.hoamz.toeic.ui.screen.vocabulary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hoamz.toeic.data.local.Word
import com.hoamz.toeic.data.remote.VocabDisplay
import com.hoamz.toeic.data.repository.DictionaryRepository
import com.hoamz.toeic.data.repository.VocabularyRepository
import com.hoamz.toeic.utils.Mapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class AppDictionaryViewModel @Inject constructor(
    private val dictionaryRepository: DictionaryRepository,
    private val vocabularyRepository: VocabularyRepository
) : ViewModel(){

    private var _vocabDisplay: MutableStateFlow<VocabDisplay> = MutableStateFlow(VocabDisplay())
    val vocabDisplay = _vocabDisplay.asStateFlow()//du lieu ben ngoai co the quan sat dc

    fun setUpDescriptionOfWords(word: Word) {
        viewModelScope.launch(Dispatchers.IO) {
            val vocab = dictionaryRepository.getDescriptionOfWordOnce(word.word)
            if(vocab != null) {
                val vocabDisplay = Mapper.toVocabularyDisplay(vocab)
                _vocabDisplay.value = vocabDisplay
            }
        }
    }

    fun insertNewVocabs(vocabs: List<String>) {
        if (vocabs.isEmpty()) return//neu ko co gia tri thi bo

        //call API de lay ra chi tiet tung tu mot
        viewModelScope.launch {
            val deferredList  = vocabs.map { word ->
                async {
                    try{
                        dictionaryRepository.getDescriptionOfWordOnce(word)
                    }catch (_ : Exception){
                        null
                    }
                }
            }
            val result = deferredList.awaitAll().filterNotNull()//chi giu lai nhung object != null

            //lay ra ds tu muon luu vao room
            val listVocab = mutableListOf<String>()
            val vocabularies = result.map { Mapper.toVocabularyEntity(it) }
            vocabularies.forEach { vocab ->
                if(vocab.word.isNotEmpty()){
                    listVocab.add(vocab.word)
                }
            }
            //lay ra nhung tu da nam trong room
            val wordsInserted = vocabularyRepository.getAllVocabsExisted(listVocab)//day la nhung tu da ton tai trong room
                .map { it.word }
                .toSet()
            vocabularies.forEach { vocab ->
                //neu ko nam trong ds nhung tu da nam trong room -> lan dau insert->ok
                if(vocab.word !in wordsInserted){
                    vocabularyRepository.insertNewWord(vocab)
                }
            }
        }
    }
}