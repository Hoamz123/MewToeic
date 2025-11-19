package com.hoamz.toeic.ui.screen.vocabulary.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hoamz.toeic.data.local.DataChart
import com.hoamz.toeic.data.local.Word
import com.hoamz.toeic.data.repository.WordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class SelectWordsViewmodel @Inject constructor(
    private val wordRepository: WordRepository
) : ViewModel(){

    //tranh case delay
    init {
        freshDataChart()
    }

    //insert new word
    fun insertNewWords(words: List<Word>) {
        if (words.isEmpty()) return//neu ko co gia tri thi bo

        val listWord = mutableListOf<String>()
        words.forEach { word ->
            listWord.add(word.word)
        }

        viewModelScope.launch {
            val wordsInserted = wordRepository.getAllWordsExisted(listWord)//day la nhung tu da ton tai trong room
                .map { it.word }
                .toSet()
            words.forEach { word ->
                if(word.word !in wordsInserted){
                    wordRepository.insertNewWord(word)
                }
            }
        }
    }

    //delete words
    fun deleteWords(words: List<Word>){
        if(words.isEmpty()) return
        viewModelScope.launch {
            wordRepository.deleteAllWords(words)
        }
    }

    //list cac tu da luu
    val wordsStored : StateFlow<List<Word>> = wordRepository.getAllNewWords()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000),emptyList())
    //khi khong con collect nua thi sau 5s -> ngung collect

    private val _dataChart = MutableStateFlow<List<DataChart>>(emptyList())
    val dataForChart : StateFlow<List<DataChart>> = _dataChart

    @RequiresApi(Build.VERSION_CODES.O)
    fun freshDataChart() {
        viewModelScope.launch {
            wordRepository.getDataChart().collect { list ->
                _dataChart.value = list
            }
        }
    }

    //lay ra ds tu mastered
    val masteredWords : StateFlow<List<Word>> = wordRepository.getMasteredWords()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000),emptyList())

    //lay ra ds tu da tung nhin thay trong my words roi
    val reviewedWords : StateFlow<List<Word>> = wordRepository.getReviewedWords()
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000),emptyList())

    //set word-> mastered
    fun masteredWord(word: Word){
        viewModelScope.launch {
            word.id?.let {
                wordRepository.masteredWord(word.id)
            }
        }
    }

    fun unMasteredWord(word: Word){
        viewModelScope.launch {
            word.id?.let {
                wordRepository.unMasteredWord(word.id)
            }
        }
    }
    //set word -> reviewed
    fun reviewedWord(word: Word){
        viewModelScope.launch {
            word.id?.let {
                wordRepository.reviewedWord(word.id)
            }
        }
    }

    //truyen word giua cac man hinh
    private val _words = MutableStateFlow<List<Word>>(emptyList())
    val words : StateFlow<List<Word>> = _words//(collect cai nay tai noi nhan)

    //luu lai list word tu A de gui den man hinh B
    //(goi ham nay o noi can truyen)
    fun setUpWordsToSend(words : List<Word>){
        if(words.isNotEmpty()){
            _words.value = words
        }
    }
}