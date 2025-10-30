package com.hoamz.toeic.data.remote

data class Vocabulary(
    val word : String?,
    val phonetic : String?,
    val phonetics : List<Phonetic>? = emptyList(),
    val meanings : List<Meaning>? = emptyList()
)

data class Phonetic(
    val text: String?,
    val audio : String
)

data class Meaning(
    val partOfSpeech : String,
    val definitions : List<Definition>
)

data class Definition(
    val definition : String
)
