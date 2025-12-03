package com.hoamz.toeic.utils

import com.hoamz.toeic.data.local.VocabularyEntity
import com.hoamz.toeic.data.remote.Phonetic

object Contains {
    const val ASK_SUBMIT = "Do you want to submit?"
    const val DESCRIPTION_ASK = "You can view the results and answers,\nafter you have submitted the test."
    const val SUBMIT = "Submit"

    const val ASK_QUIT = "Do you want to quit?"
    const val DESCRIPTION_QUIT = "You will lose the progress of the lesson if you quit now"
    const val QUIT = "Quit"

    val LIST_TIME = listOf(
        2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20
    )
    const val BASE_URL_DictionaryAPI = "https://api.dictionaryapi.dev/api/v2/entries/"

    const val STEAK_KEY = "steak_key"

    const val NUMBER_STEAK = "number_steak"

    const val ALL = "All"
    const val CORRECT = "Correct"
    const val WRONG = "Wrong"

    const val FINISHED = "Finished"

    const val NAME_DB = "db_local"

    const val TAB = "TAB_NAME"

    const val RECENT = "Recent"

    const val CATEGORIES = "Categories"

    const val ON_COMPLETED_TEST = "You have completed the test"

    const val TYPE_TEST = "Fill the Sentence"

    const val GOOD_LUCK = "Wish you luck next time"

    const val STATE_REMIND = "state_remind"
    const val WORDS = "words"
    const val TIME_PERIOD = "time_Period"
    const val INDEX_QUESTION_SELECTING = "index_question_selecting"
    val listDropPeriod = listOf<Int>(
        1,2,5,10,15,20
    )
    val listDropNumberWords = listOf<Int>(
        1,5,10,15,20
    )

    const val MISS_INFO_REMIND = "Please fill in both fields"

    const val TITLE_DEL_Q_STAR = "Are you sure you want to unstar this question?"
    const val MSG_DEL_Q_STAR = "Remove it from your starred list?"


    fun cleanWord(word : String) : String{
        return word.dropLastWhile {
            !it.isLetter()  // xoa ki tu cuoi cung cho den khi gap chu cai
        }
    }

    val sampleVocabulary = VocabularyEntity(
        word = "hello",
        phonetics = listOf(
            Phonetic(
                text = "/həˈloʊ/",
                audio = "https://api.dictionaryapi.dev/media/pronunciations/en/hello-au.mp3"
            ), Phonetic(
                text = "/hɛˈloʊ/",
                audio = "https://api.dictionaryapi.dev/media/pronunciations/en/hello-uk.mp3"
            )
        ),
        partOfSpeech = "interjection",
        definition = "Used as a greeting or to begin a conversation."
    )

    fun getShuffleListVocab(vocabs : List<VocabularyEntity>) : List<VocabularyEntity>{
        //neu so tu hien co nho hon 15 tu thi lay tat
        if(vocabs.size < 15){
            return vocabs
        }
        return vocabs.shuffled().take(15)
    }

    fun mapPartOfSpeech(pos: String): String {
        return when (pos.lowercase()) {
            "noun" -> "n"
            "verb" -> "v"
            "adjective" -> "adj"
            "adverb" -> "adv"
            "pronoun" -> "pron"
            "preposition" -> "prep"
            "conjunction" -> "conj"
            "interjection" -> "interj"
            "article" -> "art"
            "determiner" -> "det"

            // loại chi tiết
            "transitive verb" -> "vt"
            "intransitive verb" -> "vi"
            "phrasal verb" -> "phrv"
            "auxiliary verb" -> "auxv"
            "modal verb" -> "modal"

            "countable noun" -> "n(C)"
            "uncountable noun" -> "n(U)"
            "proper noun" -> "propn"

            else -> pos
        }
    }

}