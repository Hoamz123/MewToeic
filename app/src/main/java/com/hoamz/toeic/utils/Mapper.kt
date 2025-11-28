package com.hoamz.toeic.utils

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
        vocabulary.phonetics?.let {
            it.forEach { phonetic ->
                //neu nhu co audio
                if (phonetic.audio?.isNotEmpty() == true) {
                    phonetics.add(
                        Phonetic(
                            text = phonetic.text,
                            audio = phonetic.audio
                        )
                    )
                }
            }
        }
        //ds nghia cua tu
        val meanings  = mutableListOf<Means>()
        //dong nghia / trai nghia
        val synonyms = mutableListOf<String>()
        val antonyms = mutableListOf<String>()

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
                    antonyms += meaning.antonyms
                }
                if(meaning.synonyms != null && meaning.synonyms.isNotEmpty()){
                    synonyms += meaning.synonyms
                }
            }
        }

        meanings.forEach {
            it.definitions?.let { definitions ->
                definitions.forEach { definition ->
                    if(definition.synonyms != null && definition.synonyms.isNotEmpty()){
                        synonyms += definition.synonyms
                    }
                    if(definition.antonyms != null && definition.antonyms.isNotEmpty()){
                        antonyms += definition.antonyms
                    }
                }
            }
        }
        return VocabDisplay(
            word = word,
            phonetics = phonetics,
            meanings = meanings,
            synonyms = synonyms,
            antonyms = antonyms
        )
    }

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
}