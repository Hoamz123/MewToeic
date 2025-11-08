package com.hoamz.toeic.ui.screen.home.resultdetail

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.hoamz.toeic.baseviewmodel.MainViewModel
import com.hoamz.toeic.data.local.Question
import com.hoamz.toeic.ui.screen.home.component.ExplainAnswerView
import com.hoamz.toeic.ui.screen.home.showanswer.ShowAnswerViewModel
import com.hoamz.toeic.ui.screen.home.test.Answer
import com.hoamz.toeic.ui.screen.home.test.TestViewModel
import com.hoamz.toeic.ui.screen.home.test.ViewQuestion
import com.hoamz.toeic.utils.ModifierUtils.noRippleClickable
import kotlinx.coroutines.delay


@Composable
fun ShowResultDetail(
    modifier: Modifier = Modifier,
    navController: NavController,
    testViewModel: TestViewModel,
    mainViewModel: MainViewModel,
    showAnswerViewModel: ShowAnswerViewModel
) {

    //lay ra index cua cau hoi ma user clicked tu ShowAnswer
    val listQuestion : List<Question> by mainViewModel.listQuestion.collectAsState()

    val indexAnswer by showAnswerViewModel.numberClicked.collectAsState()

    //lay ra list cau tra user vua moi lam
    val listAnswerOfUser : List<Answer> by testViewModel.listAnswer.collectAsState()

    //state cua horizontal page
    val pagerState = rememberPagerState(initialPage = 0) {
       listAnswerOfUser.size
    }

    var onShowSheet by rememberSaveable {
        mutableStateOf(false)
    }

    var explainContent by rememberSaveable {
        mutableStateOf("")
    }

    LaunchedEffect(Unit) {
        //moi khi chay vao man hinh nay 1 lan
        pagerState.animateScrollToPage(indexAnswer)
    }

    //hien thi loading khi chua load xong du lieu
    var isShowLoading by remember {
        mutableStateOf(true)
    }
    LaunchedEffect(Unit) {
        delay(2300)
        isShowLoading = false
    }

    if(isShowLoading){
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val composition by rememberLottieComposition(
                spec = LottieCompositionSpec.Asset("cat3.json")
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                contentAlignment = Alignment.Center
            ){
                LottieAnimation(
                    composition = composition,
                    iterations = LottieConstants.IterateForever
                )
            }
        }
    }
    else {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            TopBarTestScreen(
                numberQuestion = pagerState.currentPage + 1,
                onClickBack = {
                    //back ve ShowAnswer
                    navController.popBackStack()
                }
            ) {
                //hien thi bottomSheet
                explainContent = listQuestion[pagerState.currentPage].explain
                onShowSheet = true
            }

            Box(
                modifier = Modifier.fillMaxSize()
            ){
                HorizontalPager(
                    state = pagerState
                ) {index ->
                    ViewDisplayQuestionAndAnswer(
                        numberQuestion = index + 1,
                        question = listQuestion[index],
                        answer = listAnswerOfUser[index]
                    )
                }
            }

            if(onShowSheet){
                ExplainAnswerView(
                    onShowSheet = true,
                    content = explainContent
                ) {value->
                    onShowSheet = value
                }
            }

        }
    }
}

@Composable
fun ViewDisplayQuestionAndAnswer(
    modifier: Modifier = Modifier,
    numberQuestion : Int,
    question: Question,
    answer: Answer
) {
    Box(
        modifier = Modifier.fillMaxSize(),
    ){
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            item {
                Box(
                    modifier = Modifier.size(width = 100.dp, height = 10.dp)
                )
            }
            item {
                ViewQuestion(
                    question = question
                )
            }
            item {
                Box(
                    modifier = Modifier.size(width = 100.dp, height = 300.dp)
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .navigationBarsPadding()
                .padding(vertical = 10.dp)
        ) {
            Text(
                text = "Question $numberQuestion",
                fontWeight = FontWeight.SemiBold,
                color = Color.Magenta,
                modifier = Modifier.padding(start = 16.dp)
            )
            Box(
                modifier = Modifier
                    .size(width = 100.dp, height = 2.dp)
                    .padding(start = 10.dp)
                    .background(color = Color.Magenta)
            )
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ){
                val choice = mapOf(
                    0 to "A",
                    1 to "B",
                    2 to "C",
                    3 to "D"
                )

                repeat(4){index ->
                    Card(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape),
                        shape = CircleShape,
                        border = BorderStroke(width = 1.dp, color = if(index == answer.indexUserClicked || index == answer.indexCorrectAnswer) Color.Transparent else Color.Black),
                        colors = CardDefaults.cardColors(
                            containerColor = when {
                                index == answer.indexUserClicked && answer.indexUserClicked != answer.indexCorrectAnswer -> Color.Red.copy(0.8f)
                                index == answer.indexCorrectAnswer -> Color.Green
                                else -> Color.White
                            }
                        )
                    ){
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ){
                            Text(
                                text = choice[index].toString(),
                                color = if(index == answer.indexUserClicked || index == answer.indexCorrectAnswer) Color.White else Color.Black.copy(0.6f),
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TopBarTestScreen(
    modifier: Modifier = Modifier,
    numberQuestion : Int,
    onClickBack :() -> Unit,
    onClickShowHint :() -> Unit,
) {
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
            .padding(start = 16.dp, end = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
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
            Text(
                text = "Question $numberQuestion",
                fontWeight = FontWeight.Normal,
            )
        }

        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ){
            Text(
                text ="Explain",
                modifier = Modifier
                    .noRippleClickable{
                        onClickShowHint() },
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}