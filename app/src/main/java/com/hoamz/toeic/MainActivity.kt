package com.hoamz.toeic

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.AnyRes
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Bookmarks
import androidx.compose.material.icons.outlined.Error
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.hoamz.toeic.baseviewmodel.MainViewModel
import com.hoamz.toeic.ui.screen.home.BottomMenuItem
import com.hoamz.toeic.ui.screen.home.HomeScreen
import com.hoamz.toeic.ui.screen.home.ItemBottomNav
import com.hoamz.toeic.ui.screen.home.showanswer.ShowAnswerViewModel
import com.hoamz.toeic.ui.screen.home.test.TestViewModel
import com.hoamz.toeic.ui.screen.splash.SplashScreen
import com.hoamz.toeic.ui.theme.ToeicTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlin.collections.listOf

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val mainViewModel : MainViewModel by viewModels()
    private val testViewModel : TestViewModel by viewModels()
    private val showAnswerViewModel : ShowAnswerViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ToeicTheme {
                val navController = rememberNavController()


//                Column(
////                    modifier = Modifier.fillMaxSize()
////                        .padding(10.dp),
////                    verticalArrangement = Arrangement.Center,
////                    horizontalAlignment = Alignment.CenterHorizontally
////                ) {
////                    FlashCard(
////                        front = "Hello",
////                        back = "Ni hao"
////                    )
////                }


                // FlashCard

                NavHost(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = Color.LightGray.copy(0.1f)),
                    navController = navController,
                    startDestination = "splash"
                ){
                    composable(route = "splash"){
                        SplashScreen {
                            navController.navigate("home"){
                                popUpTo("splash"){
                                    inclusive = true
                                }
                            }
                        }
                    }
                    composable(route = "home"){
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
}

@Composable
fun FlashCard(
    modifier: Modifier = Modifier,
    front : String,
    back : String
) {
    var flipped by rememberSaveable{
        mutableStateOf(false)
    }

    val rotation by animateFloatAsState(
        targetValue = if(flipped) 180f else 0f,
        animationSpec = tween(
            durationMillis = 500,
            easing = LinearOutSlowInEasing
        )
    )

    Box(
        modifier = Modifier.size(300.dp)
            .clickable{
                flipped = !flipped
            },
        contentAlignment = Alignment.Center
    ){
        //card
        Card(
            modifier = Modifier.fillMaxSize()
                .graphicsLayer{
                    rotationX = rotation
                    cameraDistance = 12f * density
                },
            colors = CardDefaults.cardColors(
                containerColor = Color.Magenta
            ),
            shape = RoundedCornerShape(10.dp)
        ) {
            if(rotation <= 90f){
                Box(
                    modifier = Modifier.fillMaxSize()
                        .background(color = Color.White),
                    contentAlignment = Alignment.Center
                ){
                    Text(
                        text = front,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
            else{
                Box(
                    modifier = Modifier.fillMaxSize()
                        .background(color = Color.Magenta),
                    contentAlignment = Alignment.Center
                ){
                    Text(
                        modifier = Modifier
                            .graphicsLayer(
                                rotationX = 180f
                            ),
                        text = back,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}
