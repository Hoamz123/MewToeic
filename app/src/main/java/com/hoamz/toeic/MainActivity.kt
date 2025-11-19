package com.hoamz.toeic

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.hoamz.toeic.base.AddMod
import com.hoamz.toeic.base.BaseSharePref
import com.hoamz.toeic.baseviewmodel.MainViewModel
import com.hoamz.toeic.ui.screen.home.HomeScreen
import com.hoamz.toeic.ui.screen.home.showanswer.ShowAnswerViewModel
import com.hoamz.toeic.ui.screen.home.test.TestViewModel
import com.hoamz.toeic.ui.screen.vocabulary.AppDictionaryViewModel
import com.hoamz.toeic.ui.screen.vocabulary.viewmodel.SelectWordsViewmodel
import com.hoamz.toeic.ui.theme.ToeicTheme
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val mainViewModel : MainViewModel by viewModels()
    private val testViewModel : TestViewModel by viewModels()
    private val showAnswerViewModel : ShowAnswerViewModel by viewModels()
    private val selectWordsViewmodel : SelectWordsViewmodel by viewModels()
    private val appDictionaryViewModel : AppDictionaryViewModel by viewModels()
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        AddMod.initialize(this)

        setContent {
            ToeicTheme {
                Column (
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = Color.White.copy(0.8f)),
                ){
                    HomeScreen(
                        mainViewModel = mainViewModel,
                        testViewModel = testViewModel,
                        showAnswerViewModel = showAnswerViewModel,
                        selectWordsViewmodel = selectWordsViewmodel,
                        appDictionaryViewModel = appDictionaryViewModel
                    )
                }
            }
        }
    }
}
