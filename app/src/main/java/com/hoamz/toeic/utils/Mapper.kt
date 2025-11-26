package com.hoamz.toeic.utils

import com.hoamz.toeic.data.remote.Means
import com.hoamz.toeic.data.remote.Phonetic
import com.hoamz.toeic.data.remote.VocabDisplay
import com.hoamz.toeic.data.remote.Vocabulary

object Mapper {
    fun fromVocabulary(vocabulary: Vocabulary): VocabDisplay {
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
}