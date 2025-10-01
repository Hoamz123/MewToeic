package com.hoamz.toeic.ui.screen.home


private const val RESULT_SCREEN = "ResultScreen"
private const val TEST_SCREEN = "TestScreen"
private const val SETUP_SCREEN = "SetupScreen"
private const val HOME_SCREEN = "HomeScreen"
private const val WRONG_SCREEN = "WrongScreen"
private const val VOCABULARY_SCREEN = "VocabularyScreen"
private const val LIST_TEST_SCREEN = "ListTestScreen"
private const val SHOW_ALL_ANSWER = "showAnswers"
private const val SHOW_RESULT_DETAIL = "resultDetail"


sealed class HomeNavScreen(val route : String){
    object ListTestScreen : HomeNavScreen(route = LIST_TEST_SCREEN)
    object WrongScreen : HomeNavScreen(route = WRONG_SCREEN)
    object Vocabulary : HomeNavScreen(route = VOCABULARY_SCREEN)
    object ResultScreen : HomeNavScreen(route = RESULT_SCREEN)
    object TestScreen : HomeNavScreen(route = TEST_SCREEN)
    object SetupScreen : HomeNavScreen(route = SETUP_SCREEN)
    object ShowAnswers : HomeNavScreen(route = SHOW_ALL_ANSWER)
    object ResultDetail : HomeNavScreen(route = SHOW_RESULT_DETAIL)
}
