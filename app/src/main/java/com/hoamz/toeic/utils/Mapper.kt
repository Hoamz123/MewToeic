package com.hoamz.toeic.utils

import android.os.Build
import androidx.annotation.RequiresApi
import com.hoamz.toeic.data.local.VocabularyCard
import com.hoamz.toeic.data.local.VocabularyEntity
import com.hoamz.toeic.data.remote.Means
import com.hoamz.toeic.data.remote.Phonetic
import com.hoamz.toeic.data.remote.VocabDisplay
import com.hoamz.toeic.data.remote.Vocabulary
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object Mapper {
    fun toVocabularyDisplay(vocabulary: Vocabulary): VocabDisplay {
        val word = vocabulary.word.toString()//lay ra tu
        //lay ra ds phat am
        val phonetics = mutableListOf<Phonetic>()
        vocabulary.phonetics?.let {list ->
            for(phonetic in list){
                if (phonetic.audio?.isNotEmpty() == true) {
                    phonetics.add(
                        Phonetic(
                            text = phonetic.text,
                            audio = phonetic.audio
                        )
                    )
                }
                if(phonetics.size == 2) break
            }
        }
        //ds nghia cua tu
        val meanings  = mutableListOf<Means>()
        //dong nghia / trai nghia
        val synonymsSet = mutableSetOf<String>()
        val antonymsSet = mutableSetOf<String>()


        vocabulary.meanings?.let {
            it.forEach { meaning ->
                //lay nghia
                if(meaning.partOfSpeech?.isNotEmpty() == true && meaning.definitions != null && meaning.definitions.isNotEmpty()){
                    meanings.add(
                        Means(
                            partOfSpeech = meaning.partOfSpeech,
                            definitions = meaning.definitions
                        )
                    )
                }
                //lay dong nghia  /trai nghia
                if(meaning.antonyms != null && meaning.antonyms.isNotEmpty()){
                    antonymsSet.addAll(meaning.antonyms)
                }
                if(meaning.synonyms != null && meaning.synonyms.isNotEmpty()){
                    synonymsSet.addAll(meaning.synonyms)
                }
            }
        }
        meanings.forEach {
            it.definitions?.let { definitions ->
                definitions.forEach { definition ->
                    if(definition.synonyms != null && definition.synonyms.isNotEmpty()){
                        synonymsSet.addAll(definition.synonyms)
                    }
                    if(definition.antonyms != null && definition.antonyms.isNotEmpty()){
                        antonymsSet.addAll(definition.antonyms)
                    }
                }
            }
        }
        val synonyms = mutableListOf<String>()
        synonymsSet.forEach {
            synonyms.add(it)
        }
        val antonyms = mutableListOf<String>()
        antonymsSet.forEach {
            antonyms.add(it)
        }
        return VocabDisplay(
            word = word,
            phonetics = phonetics,
            meanings = meanings,
            synonyms = synonyms,
            antonyms = antonyms
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun toVocabularyEntity(vocabulary: Vocabulary) : VocabularyEntity{
        val word = vocabulary.word.toString()
        var partOfSpeech = "   "
        var definition = "";
        if(!vocabulary.meanings.isNullOrEmpty()){
            if(vocabulary.meanings.isNotEmpty()){
                //tuc co data
                partOfSpeech = vocabulary.meanings[0].partOfSpeech.toString()
                val definitions = vocabulary.meanings[0].definitions
                if(!definitions.isNullOrEmpty() && definitions.isNotEmpty()){
                    definition = definitions[0].definition.toString()
                }
            }
        }
        val date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"))
        val phonetics = mutableListOf<Phonetic>()
        if(!vocabulary.phonetics.isNullOrEmpty()){
            vocabulary.phonetics.forEach { (text, audio) ->
                if(!audio.isNullOrEmpty()){
                    if(phonetics.size < 2){
                        phonetics.add(Phonetic(text,audio))
                    }
                    else return@forEach
                }
            }
        }
        return VocabularyEntity(
            word = word,
            partOfSpeech = partOfSpeech,
            definition = definition,
            date = date,
            phonetics = phonetics
        )
    }

    //loc du lieu de dua nen flashCard
    fun toVocabularyCard(vocabularyEntity: VocabularyEntity) : VocabularyCard{

        val id = vocabularyEntity.id
        val word = vocabularyEntity.word
        val audios = mutableListOf<String>()//chi lay 2 audio thui
        val definition = vocabularyEntity.definition
        val partOfSpeech = Contains.mapPartOfSpeech(vocabularyEntity.partOfSpeech)

        val audiosTemp = mutableListOf<String>()

        vocabularyEntity.phonetics.forEach { phonetic ->
            if(!phonetic.audio.isNullOrEmpty()) {
                audiosTemp.add(phonetic.audio)
            }
        }

        audiosTemp.forEach { audio ->
            audios.add(audio)
            //sau khi add-> neu thay size == 2 thi -> du roi
            if(audios.size == 2) return@forEach
        }

        //da co du lieu audio (hoac la du 2 audio hoac la chi co 1 audio hoac la ko co audio nao)

        return VocabularyCard(
            id = id,
            word = word,
            audios = audios,
            definition = definition,
            partOfSpeech = partOfSpeech
        )
    }
}