package com.hoamz.toeic.ui.screen.home.setuptest

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.hoamz.toeic.R
import com.hoamz.toeic.base.BannerAdView
import com.hoamz.toeic.baseviewmodel.MainViewModel
import com.hoamz.toeic.ui.screen.home.HomeNavScreen
import com.hoamz.toeic.utils.Contains
import com.hoamz.toeic.utils.ModifierUtils
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun SetUpBeforeTestScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    mainViewModel: MainViewModel
) {

    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState()
    var timeTest by rememberSaveable { mutableIntStateOf(0) }
    var checked by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .verticalScroll(scrollState)
            .padding(horizontal = 8.dp, vertical = 10.dp)
            .padding(bottom = 200.dp)
    ) {

        StickHeaderInSetUpScreen(
            nameScreen = "Incomplete Sentences"
        ) {
            navController.popBackStack()
        }

        ConstraintLayout(
            modifier = Modifier.fillMaxWidth()
        ) {

            val (cardDes, animLottie, cbTestMode, acSetTime) = createRefs()

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(cardDes) {
                        top.linkTo(parent.top, margin = 5.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .padding(5.dp),
                shape = RoundedCornerShape(10.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                ) {
                    Text(text = "Question", fontWeight = FontWeight.SemiBold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = directions,
                        fontWeight = FontWeight.Normal,
                        fontSize = 15.sp
                    )
                }
            }

            val composition by rememberLottieComposition(
                spec = LottieCompositionSpec.Asset("cat5.json")
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(animLottie) {
                        top.linkTo(cardDes.bottom, margin = 20.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .height(130.dp),
                contentAlignment = Alignment.Center
            ) {
                LottieAnimation(
                    modifier = Modifier.size(120.dp),
                    composition = composition,
                    iterations = LottieConstants.IterateForever
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(cbTestMode) {
                        top.linkTo(animLottie.bottom, margin = 15.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .padding(20.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Test mode", fontWeight = FontWeight.Normal)
                Box(
                    modifier = Modifier.width(80.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Switch(
                        checked = checked,
                        onCheckedChange = { checked = it },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color.Black.copy(0.5f),
                            checkedTrackColor = Color.DarkGray.copy(0.2f)
                        )
                    )
                }
            }
            if (checked) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .constrainAs(acSetTime) {
                            top.linkTo(cbTestMode.bottom, margin = 20.dp)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                        .padding(vertical = 10.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_circle_down),
                        tint = colorResource(R.color.progressColor),
                        contentDescription = null,
                        modifier = Modifier
                            .clip(CircleShape)
                            .clickable {
                                if (timeTest > 0) timeTest--
                            }
                    )
                    Spacer(modifier = Modifier.width(20.dp))
                    AnimatedContent(
                        targetState = timeTest,
                        transitionSpec = {
                            ContentTransform(
                                slideInVertically { it } + fadeIn(),
                                slideOutVertically { -it } + fadeOut()
                            )
                        }
                    ) { indexNumber ->
                        Text(
                            text = Contains.LIST_TIME[indexNumber].toString() + "m",
                            fontWeight = FontWeight.Normal
                        )
                    }
                    Spacer(modifier = Modifier.width(20.dp))
                    Icon(
                        painter = painterResource(R.drawable.ic_circle_up),
                        tint = colorResource(R.color.progressColor),
                        contentDescription = null,
                        modifier = Modifier
                            .clip(CircleShape)
                            .clickable {
                                if (timeTest < 18) timeTest++
                            }
                    )
                }
            }
        }
    }

    Box (
        modifier = Modifier.fillMaxSize()
            .navigationBarsPadding(),
        contentAlignment = Alignment.BottomCenter
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.White.copy(0.8f))
                .padding(horizontal = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            BannerAdView()
            ModifierUtils.SpaceHeigh(10.dp)

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 20.dp),
                onClick = {
                    if (checked) {
                        mainViewModel.turnOnTestMode()
                        mainViewModel.setTimeDoTest(time = Contains.LIST_TIME[timeTest])
                    } else {
                        mainViewModel.turnOffTestMode()
                    }

                    scope.launch {
                        delay(1000)
                        navController.navigate(HomeNavScreen.TestScreen.route) {
                            popUpTo(HomeNavScreen.SetupScreen.route) { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                },
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.progressColor)),
                elevation = ButtonDefaults.elevatedButtonElevation(2.dp)
            ) {
                Text(
                    text = "Start now",
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold
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
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ){
        Spacer(modifier = Modifier.width(8.dp))
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
        Spacer(modifier = Modifier.width(15.dp))//20
        Text(
            text = nameScreen,
            fontWeight = FontWeight.Normal
        )
    }
}

//Incomplete Sentences


private const val directions = "A word or phrase is missing in each of the following sentences. Four answer choices are given below each sentence. Select the best answer to complete the sentence. Then mark the letter (A), (B), (C), or (D) on your answer sheet”"