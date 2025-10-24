package com.hoamz.toeic.ui.screen.home.result

import android.util.Log
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.util.Logger
import com.hoamz.toeic.R
import com.hoamz.toeic.baseviewmodel.MainViewModel
import com.hoamz.toeic.data.local.ActivityRecent
import com.hoamz.toeic.data.local.Question
import com.hoamz.toeic.ui.screen.home.HomeNavScreen
import com.hoamz.toeic.ui.screen.home.test.Answer
import com.hoamz.toeic.ui.screen.home.test.TestViewModel

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
        modifier = Modifier.fillMaxSize(),
    ) {
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp)
                .padding(start = 16.dp, end = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            IconButton(
                onClick = {
                    navController.popBackStack()
                    testViewModel.clearDataAnswer()
                }, modifier = Modifier.clip(CircleShape)
            ) {
                Icon(Icons.Default.ArrowBackIos, contentDescription = null)
            }
            Spacer(modifier = Modifier.width(10.dp))
            Text(text = "Result", fontWeight = FontWeight.Normal,)
        }

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(vertical = 5.dp)
        ) {
            //card notification finish
            item {
                Card (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
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
                            .padding(vertical = 10.dp),
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
                                .padding(start = 10.dp)
                                .clip(CircleShape)
                                .size(100.dp),
                            contentScale = ContentScale.Crop
                        )

                        Column(
                            modifier = Modifier
                                .padding(8.dp)
                                .padding(vertical = 10.dp)
                        ) {

                            Row (
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 5.dp),
                                horizontalArrangement = Arrangement.Center
                            ){
                                Text(
                                    text = "You have completed the test",
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color.Black,
                                    fontSize = 18.sp
                                )
                            }

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 5.dp),
                                horizontalArrangement = Arrangement.Center
                            ){
                                Text(
                                    text = "Fill the Sentence",
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color.Green.copy(0.8f),
                                    fontSize = 16.sp
                                )
                            }

                            Row (
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 5.dp),
                                horizontalArrangement = Arrangement.Center
                            ){
                                Text(
                                    text = "Wish you luck next time",
                                    color = Color.Black,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 14.sp
                                )
                            }
                        }
                    }
                }
            }

            //card result
            item{
               Card(
                   modifier = Modifier
                       .fillMaxWidth()
                       .padding(10.dp),
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


            //show all answer and vocabulary
            item {
                Spacer(modifier = Modifier.height(20.dp))
                //show all answer
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {

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
                    Button(
                        modifier = Modifier.fillMaxWidth()
                            .padding(horizontal = 16.dp,vertical = 5.dp),
                        onClick = {
                            navController.navigate(HomeNavScreen.ShowAnswers.route)
                        },
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if(animatedProgress <= 5) Color.Red else if(animatedProgress <= 15) Color.Yellow else Color.Green
                        )
                    ) {
                        Text(
                            text = "See all answer",
                            fontWeight = FontWeight.SemiBold,
                            color = Color.Black
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Button(
                        modifier = Modifier.fillMaxWidth()
                            .padding(horizontal = 16.dp,vertical = 5.dp),
                        onClick = {

                        },
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Magenta.copy(0.4f)
                        )
                    ) {
                        Text(
                            text = "Vocabulary",
                            fontWeight = FontWeight.SemiBold,
                            color = Color.Black
                        )
                    }
                }
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
        modifier = Modifier.size(150.dp),
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
            color = if(animatedCorrectNumber <= 5) Color.Red else if(animatedCorrectNumber <= 15) Color.Yellow else Color.Green,
            strokeWidth = 8.dp,
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

