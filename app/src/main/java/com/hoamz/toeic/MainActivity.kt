package com.hoamz.toeic

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.hoamz.toeic.base.AddMod
import com.hoamz.toeic.baseviewmodel.MainViewModel
import com.hoamz.toeic.ui.screen.navigation.HomeScreen
import com.hoamz.toeic.ui.screen.home.showanswer.ShowAnswerViewModel
import com.hoamz.toeic.ui.screen.home.test.TestViewModel
import com.hoamz.toeic.ui.screen.vocabulary.AppDictionaryViewModel
import com.hoamz.toeic.ui.screen.vocabulary.viewmodel.SelectWordsViewmodel
import com.hoamz.toeic.ui.screen.vocabulary.viewmodel.VocabularyViewModel
import com.hoamz.toeic.ui.theme.ToeicTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val mainViewModel : MainViewModel by viewModels()
    private val testViewModel : TestViewModel by viewModels()
    private val showAnswerViewModel : ShowAnswerViewModel by viewModels()
    private val selectWordsViewmodel : SelectWordsViewmodel by viewModels()
    private val appDictionaryViewModel : AppDictionaryViewModel by viewModels()
    private val vocabularyViewModel : VocabularyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        AddMod.initialize(this)

        setContent {
            ToeicTheme {
                Column(
                    modifier = Modifier.fillMaxSize()
                        .background(color = Color.White.copy(0.8f))
                        .statusBarsPadding(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {

//                    FlashCard()

                    HomeScreen(
                        mainViewModel = mainViewModel,
                        testViewModel = testViewModel,
                        showAnswerViewModel = showAnswerViewModel,
                        selectWordsViewmodel = selectWordsViewmodel,
                        appDictionaryViewModel = appDictionaryViewModel,
                        vocabularyViewModel = vocabularyViewModel
                    )
                }
            }
        }
    }
}
