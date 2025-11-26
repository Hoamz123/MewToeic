package com.hoamz.toeic.data.remote

data class Vocabulary(
    val word : String? = "",
    val phonetic : String? = "",
    val phonetics : List<Phonetic>? = emptyList(),
    val meanings : List<Meaning>? = emptyList()
)

data class Phonetic(
    val text: String? = "    ",
    val audio : String? = ""
)

data class Meaning(
    val partOfSpeech : String? = "",
    val definitions : List<Definition>? = emptyList(),
    val synonyms : List<String>? = emptyList(),
    val antonyms : List<String>? = emptyList()
)

data class Means(
    val partOfSpeech : String? = "",
    val definitions : List<Definition>? = emptyList(),
)

data class Definition(
    val definition : String? = "",
    val example : String? = "",
    val synonyms : List<String>? = emptyList(),
    val antonyms : List<String>? = emptyList()
)

data class VocabDisplay(
    val word : String = "",
    val phonetics : List<Phonetic>? = emptyList(),
    val meanings : List<Means>? = emptyList(),
    val synonyms : List<String>? = emptyList(),
    val antonyms : List<String>? = emptyList()
)
