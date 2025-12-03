package com.hoamz.toeic.ui.screen.navigation


private const val RESULT_SCREEN = "ResultScreen"
private const val TEST_SCREEN = "TestScreen"
private const val SETUP_SCREEN = "SetupScreen"
private const val HOME_SCREEN = "HomeScreen"
private const val QUESTION_STAR_SCREEN = "starScreen"
private const val VOCABULARY_SCREEN = "VocabularyScreen"
private const val LIST_TEST_SCREEN = "ListTestScreen"
private const val SHOW_ALL_ANSWER = "showAnswers"
private const val SHOW_RESULT_DETAIL = "resultDetail"
private const val SELECT_VOCAB = "selectVocabulary"
private const val SHOW_WORDS = "show_words"
private const val WORD_DETAIL = "word_detail"
private const val FLASHCARD = "flashcard"
private const val MINIGAME = "minigame"


sealed class HomeNavScreen(val route : String){
    object ListTestScreen : HomeNavScreen(route = LIST_TEST_SCREEN)
    object QuestionStarScreen : HomeNavScreen(route = QUESTION_STAR_SCREEN)
    object Vocabulary : HomeNavScreen(route = VOCABULARY_SCREEN)
    object ResultScreen : HomeNavScreen(route = RESULT_SCREEN)
    object TestScreen : HomeNavScreen(route = TEST_SCREEN)
    object SetupScreen : HomeNavScreen(route = SETUP_SCREEN)
    object ShowAnswers : HomeNavScreen(route = SHOW_ALL_ANSWER)
    object ResultDetail : HomeNavScreen(route = SHOW_RESULT_DETAIL)
    object SelectVocabulary : HomeNavScreen(route = SELECT_VOCAB)
    object ShowNewWords : HomeNavScreen(route = SHOW_WORDS)
    object WordDetail : HomeNavScreen(route = WORD_DETAIL)
    object FlashCard : HomeNavScreen(route = FLASHCARD)
    object MiniGame : HomeNavScreen(route = MINIGAME)
}
