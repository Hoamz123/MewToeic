package com.hoamz.toeic.ui.screen.vocabulary.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hoamz.toeic.data.local.DataChart
import com.hoamz.toeic.data.local.VocabularyEntity
import com.hoamz.toeic.data.remote.Vocabulary
import com.hoamz.toeic.data.repository.VocabularyRepository
import com.hoamz.toeic.utils.Mapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.any
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.collections.forEach

@HiltViewModel
class VocabularyViewModel @Inject constructor(
    private val vocabularyRepository: VocabularyRepository
) : ViewModel(){

    //tranh case delay
    init {
        freshDataChart()
    }

    //delete words
    fun deleteVocabs(vocabs: List<VocabularyEntity>){
        if(vocabs.isEmpty()) return
        viewModelScope.launch {
            vocabularyRepository.deleteVocabularies(vocabs)
        }
    }

    //list cac tu da luu
    val vocabsStored : StateFlow<List<VocabularyEntity>> = vocabularyRepository.getAllNewVocab()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000),emptyList())
    //khi khong con collect nua thi sau 5s -> ngung collect

    private val _dataChart = MutableStateFlow<List<DataChart>>(emptyList())
    val dataForChart  = _dataChart.asStateFlow()

    @RequiresApi(Build.VERSION_CODES.O)
    fun freshDataChart() {
        viewModelScope.launch {
            vocabularyRepository.getDataChart().collect { list ->
                _dataChart.value = list
            }
        }
    }

    //lay ra ds tu mastered
    val masteredVocabs : StateFlow<List<VocabularyEntity>> = vocabularyRepository.getMasteredVocabs()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000),emptyList())

    //lay ra ds tu da tung nhin thay trong my words roi
    val reviewedVocabs : StateFlow<List<VocabularyEntity>> = vocabularyRepository.getReviewedVocabs()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000),emptyList())

    //set word-> mastered
    fun masteredVocab(vocab: VocabularyEntity){
        viewModelScope.launch {
            vocab.id?.let {
                vocabularyRepository.masteredVocab(vocab.id)
            }
        }
    }

    fun unMasteredVocab(vocab: VocabularyEntity){
        viewModelScope.launch {
            vocab.id?.let {
                vocabularyRepository.unMasteredVocab(vocab.id)
            }
        }
    }
    //set word -> reviewed
    fun reviewedVocab(vocab: VocabularyEntity){
        viewModelScope.launch {
            vocab.id?.let {
                vocabularyRepository.reviewedVocab(vocab.id)
            }
        }
    }

    //truyen word giua cac man hinh
    private val _vocabs = MutableStateFlow<List<VocabularyEntity>>(emptyList())
    val vocabs = _vocabs.asStateFlow()//(collect cai nay tai noi nhan)

    //luu lai list word tu A de gui den man hinh B
    //(goi ham nay o noi can truyen)
    fun setUpVocabsToSend(vocabs : List<VocabularyEntity>){
        _vocabs.value = emptyList()
        if(vocabs.isNotEmpty()){
            _vocabs.value = vocabs
        }
    }
}