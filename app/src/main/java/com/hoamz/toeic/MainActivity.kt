package com.hoamz.toeic

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.navigation.compose.rememberNavController
import com.hoamz.toeic.base.AddMod
import com.hoamz.toeic.base.BaseSharePref
import com.hoamz.toeic.baseviewmodel.MainViewModel
import com.hoamz.toeic.ui.screen.home.HomeScreen
import com.hoamz.toeic.ui.screen.home.showanswer.ShowAnswerViewModel
import com.hoamz.toeic.ui.screen.home.test.TestViewModel
import com.hoamz.toeic.ui.theme.ToeicTheme
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val mainViewModel : MainViewModel by viewModels()
    private val testViewModel : TestViewModel by viewModels()
    private val showAnswerViewModel : ShowAnswerViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        AddMod.initialize(this)

        //lay ra ngay hom nay
        val today = Calendar.getInstance().get(Calendar.DAY_OF_YEAR)

        //lay ra ngay da luu trong sharePref
        val daySaved = BaseSharePref.getDay()

        //kiem tra neu khac nhau thi chung to qua ngay moi roi -> reset lai progress
        if(daySaved != today){
            BaseSharePref.saveDay(today)
            BaseSharePref.saveProgressSteak(0)
        }

        setContent {
            ToeicTheme {
                val navController = rememberNavController()
                Column (
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = Color.White.copy(0.5f)),
                ){
                    HomeScreen(
                        mainViewModel = mainViewModel,
                        testViewModel = testViewModel,
                        showAnswerViewModel = showAnswerViewModel
                    )
                }
            }
        }
    }
}
