package com.hoamz.toeic.ui.screen.home.result

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.hoamz.toeic.R
import com.hoamz.toeic.base.AddMod
import com.hoamz.toeic.base.BannerAdView
import com.hoamz.toeic.baseviewmodel.MainViewModel
import com.hoamz.toeic.data.local.ActivityRecent
import com.hoamz.toeic.data.local.Question
import com.hoamz.toeic.ui.component.TopBar
import com.hoamz.toeic.ui.screen.navigation.HomeNavScreen
import com.hoamz.toeic.ui.screen.home.test.Answer
import com.hoamz.toeic.ui.screen.home.test.TestViewModel
import com.hoamz.toeic.utils.Contains
import com.hoamz.toeic.utils.ModifierUtils

@Composable
fun ResultScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    testViewModel: TestViewModel,
    mainViewModel: MainViewModel
) {


    val listAnswerOfUser : List<Answer> by testViewModel.listAnswer.collectAsState()//da co list cau tra loi cua user
    val numberCorrect = testViewModel.countNumberCorrectAnswer(listAnswerOfUser)//so cau user lam dung
    val listQuestionCurrent : List<Question> by mainViewModel.listQuestion.collectAsState()
    val testNumber by mainViewModel.testNumber.collectAsState()
    val percentCorrect = numberCorrect.toFloat() / 30.toFloat()


    var progress by rememberSaveable {
        mutableIntStateOf(0)
    }

    LaunchedEffect(Unit) {
        progress = numberCorrect
    }

    val animatedProgress by animateIntAsState(
        targetValue = progress, animationSpec = tween(
            durationMillis = 2000, easing = LinearOutSlowInEasing
        )
    )

    //luu lai vao room
    LaunchedEffect(Unit) {
        val activityRecent = ActivityRecent(
            timeStamp = System.currentTimeMillis(),
            numberAnswerCorrect = numberCorrect,
            listAnswer = listAnswerOfUser,
            nameTest = "Test $testNumber",
            listQuestion = listQuestionCurrent)
        mainViewModel.insertNewActivity(activityRecent = activityRecent)
    }

    Column(
        modifier = Modifier.fillMaxSize()
            .background(color = Color.White.copy(0.8f)),
    ) {

        TopBar(
            nameTab = "Result"
        ) {
            if(checkAds()) AddMod.showAdd()
            testViewModel.clearDataAnswer()
            navController.popBackStack()
        }

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
        ) {
            //card notification finish
            item {
                Card (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp, vertical = 3.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    ),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 3.dp
                    )
                ){
                    Row (
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 5.dp),
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        var progress by rememberSaveable {
                            mutableIntStateOf(0)
                        }

                        LaunchedEffect(Unit) {
                            progress = numberCorrect
                        }

                        val animatedProgress by animateIntAsState(
                            targetValue = progress,
                            animationSpec = tween(
                                durationMillis = 2000,
                                easing = LinearOutSlowInEasing
                            )
                        )

                        val emotion = if(animatedProgress <= 5){
                            R.drawable.img
                        }
                        else if(animatedProgress <= 10){
                            R.drawable.img_2
                        }
                        else if(animatedProgress <= 15){
                            R.drawable.img_3
                        }
                        else if(animatedProgress <= 25){
                            R.drawable.img_4
                        }
                        else R.drawable.img_5
                        Image(
                            painter = painterResource(emotion),
                            contentDescription = null,
                            modifier = Modifier
                                .padding(start = 16.dp)
                                .clip(CircleShape)
                                .size(80.dp),
                            contentScale = ContentScale.Crop
                        )

                        Column(
                            modifier = Modifier
                                .padding(8.dp)
                                .padding(vertical = 5.dp)
                        ) {

                            Row (
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 5.dp),
                                horizontalArrangement = Arrangement.Center
                            ){
                                Text(
                                    text = Contains.ON_COMPLETED_TEST,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color.Black,
                                    fontSize = 16.sp
                                )
                            }

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 5.dp),
                                horizontalArrangement = Arrangement.Center
                            ){
                                Text(
                                    text = Contains.TYPE_TEST,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color.Green.copy(0.8f),
                                    fontSize = 15.sp
                                )
                            }

                            Row (
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 5.dp),
                                horizontalArrangement = Arrangement.Center
                            ){
                                Text(
                                    text = Contains.GOOD_LUCK,
                                    color = Color.Black,
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 14.sp
                                )
                            }
                        }
                    }
                }
            }

            //card result
            item{

               ModifierUtils.SpaceHeigh(10.dp)

               Card(
                   modifier = Modifier
                       .fillMaxWidth()
                       .padding(horizontal = 10.dp, vertical = 3.dp),
                   shape = RoundedCornerShape(10.dp),
                   elevation = CardDefaults.cardElevation(
                       defaultElevation = 3.dp
                   ),
                   colors = CardDefaults.cardColors(
                       containerColor = Color.White
                   )
               ) {
                    Box(
                        modifier = Modifier.fillMaxWidth()
                            .padding(vertical = 10.dp),
                        contentAlignment = Alignment.Center
                    ){
                        AnimatedProgressIndicator(
                            targetProgress = percentCorrect,
                            numberCorrect = numberCorrect
                        )
                    }
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
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            //quang cao o day
            BannerAdView()
            ModifierUtils.SpaceHeigh(10.dp)

            Button(
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 8.dp),
                onClick = {
                    navController.navigate(HomeNavScreen.ShowAnswers.route)
                }, shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = /*if (animatedProgress <= 5) Color.Green else if (animatedProgress <= 15)*/ colorResource(R.color.progressColor) /*else Color.Green*/
                ), elevation = ButtonDefaults.elevatedButtonElevation(2.dp)
            ) {
                Text(
                    text = "See all answer",
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
            }
            Button(
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 8.dp),
                onClick = {
                    navController.navigate(HomeNavScreen.SelectVocabulary.route)
                },
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Green,
                ),
                elevation = ButtonDefaults.elevatedButtonElevation(2.dp)
            ) {
                Text(
                    text = "Vocabulary",
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun AnimatedProgressIndicator(
    modifier: Modifier = Modifier,
    targetProgress : Float,
    numberCorrect : Int
) {

    var progress by rememberSaveable {
        mutableFloatStateOf(0f)
    }

    var numberAnswerCorrect by rememberSaveable {
        mutableIntStateOf(0)
    }

    //khi composable hien thi -> ban dau la 0f
    LaunchedEffect(Unit) {
        progress = targetProgress
        numberAnswerCorrect = numberCorrect
    }

    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(
            durationMillis = 2000,
            easing = LinearOutSlowInEasing
        )
    )

    val animatedCorrectNumber by animateIntAsState(
        targetValue = numberAnswerCorrect,
        animationSpec = tween(
            durationMillis = 2000,
            easing = LinearOutSlowInEasing
        )
    )

    Box(
        modifier = Modifier.size(130.dp),
        contentAlignment = Alignment.Center
    ){
        CircularProgressIndicator(
            progress = { 1f },
            modifier = Modifier.fillMaxSize(),
            color = Color.LightGray,
            strokeWidth = 7.dp,
            trackColor = ProgressIndicatorDefaults.circularIndeterminateTrackColor,
            strokeCap = ProgressIndicatorDefaults.CircularDeterminateStrokeCap,
        )

        CircularProgressIndicator(
            progress = { animatedProgress },
            modifier = Modifier.fillMaxSize()
                .graphicsLayer{
                    rotationZ = 180f
                },
            color = if(animatedCorrectNumber <= 5) Color.Red else if(animatedCorrectNumber <= 15) colorResource(R.color.progressColor) else Color.Green,
            strokeWidth = 7.dp,
            trackColor = ProgressIndicatorDefaults.circularIndeterminateTrackColor,
            strokeCap = ProgressIndicatorDefaults.CircularDeterminateStrokeCap,
        )

        Text(
            modifier = Modifier.align(Alignment.Center),
            text = "$animatedCorrectNumber / 30",
            fontSize = 25.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

//neu thoi diem bath ma chia het cho ca 2 va 3 thi se hien quang cao
fun checkAds() : Boolean {
    val time = System.currentTimeMillis()
    return (time % 3).toInt() == 0
}


