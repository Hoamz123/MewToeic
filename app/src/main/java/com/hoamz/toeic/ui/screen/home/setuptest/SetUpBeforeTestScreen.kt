package com.hoamz.toeic.ui.screen.home.setuptest

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.hoamz.toeic.R
import com.hoamz.toeic.baseviewmodel.MainViewModel
import com.hoamz.toeic.data.local.Question
import com.hoamz.toeic.ui.screen.home.HomeNavScreen
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun SetUpBeforeTestScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    mainViewModel: MainViewModel
) {
    val scope = rememberCoroutineScope()
    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        //thoi gian cai dat test mode
        var timeTest by rememberSaveable {
            mutableIntStateOf(3)
        }

        StickHeaderInSetUpScreen(
            nameScreen = "Incomplete Sentences"
        ){
            navController.popBackStack()
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    ),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 3.dp
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                    ) {
                        Text(
                            text = "Question",
                            fontWeight = FontWeight.SemiBold,
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        Text(text = directions,
                            fontWeight = FontWeight.Normal)
                    }
                }
            }

            item {
                val composition by rememberLottieComposition(
                    spec = LottieCompositionSpec.Asset("cat5.json")
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(130.dp),
                    contentAlignment = Alignment.Center
                ){
                    LottieAnimation(
                        modifier = Modifier.size(120.dp),
                        composition = composition,
                        iterations = LottieConstants.IterateForever//vo han
                    )
                }
                Spacer(modifier = Modifier.height(15.dp))

            }

            item {
                var checked by rememberSaveable {
                    mutableStateOf(false)
                }
                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text(
                        text = "Test mode",
                        fontWeight = FontWeight.Normal
                    )
                    Box (
                        modifier = Modifier.width(80.dp),
                        contentAlignment = Alignment.Center
                    ){
                        Switch(
                            checked = checked,
                            onCheckedChange = {
                                checked = it
                            },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.Black.copy(0.6f),
                                checkedTrackColor = Color.LightGray
                            )
                        )
                    }
                }

                if(checked){
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 10.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_minus),
                                contentDescription = null,
                                modifier = Modifier.
                                clip(CircleShape)
                                    .clickable{
                                    if(timeTest > 3){
                                        timeTest--
                                    }
                                }
                            )
                            Spacer(modifier = Modifier.width(10.dp))

                            Icon(
                                Icons.Outlined.Timer,
                                contentDescription = null,
                                tint = Color.Red.copy(0.8f)
                            )
                            Spacer(modifier = Modifier.width(3.dp))

                            Text(
                                text = "$timeTest:00", fontWeight = FontWeight.Normal
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Icon(
                                painter = painterResource(R.drawable.id_add),
                                contentDescription = null,
                                modifier = Modifier.
                                    clip(CircleShape)
                                    .clickable{
                                    if(timeTest < 20){
                                        timeTest++
                                    }
                                }
                            )
                        }
                    Spacer(modifier = Modifier.width(10.dp))
                }
                else{
                    Spacer(modifier = Modifier.height(30.dp))
                }

                Row (
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = {
                            if(checked){
                                //neu truoc do da bat test mode -> click button -> da bat trang that test mode
                                mainViewModel.turnOnTestMode()
                                mainViewModel.setTimeDoTest(time = timeTest)
                            }
                            scope.launch {
                                delay(1000)
                                navController.navigate(HomeNavScreen.TestScreen.route){
                                    popUpTo(HomeNavScreen.SetupScreen.route){
                                        inclusive = true
                                    }
                                    launchSingleTop = true
                                }
                            }
                        }, shape = RoundedCornerShape(20.dp), colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(R.color.purple_200)
                        )
                    ) {
                        Text(
                            text = "Start now",
                        )
                    }
                }
            }

            item {
                Box(
                    modifier = Modifier.size(width = 100.dp, height = 100.dp)
                )
            }
        }
    }
}

@Composable
fun StickHeaderInSetUpScreen(
    modifier: Modifier = Modifier,
    nameScreen : String,
    onClickBack :() -> Unit
) {
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        Spacer(modifier = Modifier.width(16.dp))
        IconButton(
            onClick = {
                onClickBack()
            },
            modifier = Modifier
                .clip(CircleShape)
        ) {
            Icon(Icons.Default.ArrowBackIos,
                contentDescription = null)
        }
        Spacer(modifier = Modifier.width(20.dp))
        Text(
            text = nameScreen,
            fontWeight = FontWeight.SemiBold,
        )
    }
}

//Incomplete Sentences


private const val directions = "A word or phrase is missing in each of the following sentences. Four answer choices are given below each sentence. Select the best answer to complete the sentence. Then mark the letter (A), (B), (C), or (D) on your answer sheet”"