package com.hoamz.toeic.ui.screen.vocabulary.screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.hoamz.toeic.baseviewmodel.MainViewModel
import com.hoamz.toeic.data.remote.Meaning
import com.hoamz.toeic.data.remote.Phonetic
import com.hoamz.toeic.data.remote.Vocabulary
import com.hoamz.toeic.ui.screen.vocabulary.viewmodel.SelectWordsViewmodel

@Composable
fun ShowNewWords(
    modifier: Modifier = Modifier,
    mainViewModel: MainViewModel,
    navController: NavController,
    selectWordsViewmodel: SelectWordsViewmodel
) {

}

@Composable
fun AWord(
    modifier: Modifier = Modifier,
    vocabulary: Vocabulary
) {
    // du lieu call ve tu API
    val word = vocabulary.word//(cai nay coi nhu la du lieuchuan luon)
    val _phonetics : List<Phonetic> = vocabulary.phonetics ?: emptyList()
    val _meaning : List<Meaning> = vocabulary.meanings ?: emptyList()

    //du lieu chuan de hien thi nen view
    val phonetics : MutableList<Phonetic> = mutableListOf();
    val meaning : MutableList<Meaning> = mutableListOf()

    _phonetics.forEach { phonetic ->
        if(phonetic.text == null || phonetic.audio == null) return@forEach
        if(phonetic.text.isNotEmpty() && phonetic.audio.isNotEmpty()){

        }
    }
}