package com.hoamz.toeic.ui.screen.vocabulary.screen.learnvocab

import androidx.lifecycle.ViewModel
import com.hoamz.toeic.utils.Mapper
import com.hoamz.toeic.data.local.VocabularyCard
import com.hoamz.toeic.data.local.VocabularyEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class FlashCardViewModel : ViewModel(){
    private var _vocabsCard = MutableStateFlow<List<VocabularyCard>>(emptyList())
    val vocabsCard = _vocabsCard.asStateFlow()

    //set up de gui qua man hinh flashCard
    fun sendVocabsCardToFlashCardScreen(vocabs : List<VocabularyEntity>){
        _vocabsCard.value = emptyList()
        vocabs.forEach { entity ->
            _vocabsCard.value += Mapper.toVocabularyCard(entity)
        }
    }
}