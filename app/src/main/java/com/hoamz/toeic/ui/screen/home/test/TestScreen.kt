package com.hoamz.toeic.ui.screen.home.test

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.hoamz.toeic.R
import com.hoamz.toeic.baseviewmodel.MainViewModel
import com.hoamz.toeic.data.local.Question
import com.hoamz.toeic.data.local.QuestionStar
import com.hoamz.toeic.ui.screen.home.component.ExplainAnswerView
import com.hoamz.toeic.ui.screen.home.HomeNavScreen
import com.hoamz.toeic.utils.Contains
import com.hoamz.toeic.utils.DeviceController
import com.hoamz.toeic.utils.ModifierUtils.noRippleClickable
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.collections.getValue


@Composable
fun TestScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    mainViewModel: MainViewModel,
    testViewModel: TestViewModel
) {

    //lay ra trang thai test mode/no test mode
    //lay ra time test
    val isTestMode by mainViewModel.isTestMode.collectAsState()
    val timeDoTest by mainViewModel.timeTest.collectAsState()

    //lay index trc do da gui qua mainViewmodel tu Home
    val indexClicked by mainViewModel.testNumber.collectAsState()
    var isShowLoading by remember {
        mutableStateOf(true)
    }
    LaunchedEffect(Unit) {
        delay(2000)
        isShowLoading = false
    }

    LaunchedEffect(Unit) {
        mainViewModel.freshQuestion(number = indexClicked)
        mainViewModel.freshDefaultAnswer(number = indexClicked)
    }
    val listQuestion: List<Question> by mainViewModel.listQuestion.collectAsState()
    val listDefaultAnswer: List<Answer> by mainViewModel.listDefaultAnswerQuestion.collectAsState()

    LaunchedEffect(listDefaultAnswer) {
        if (listDefaultAnswer.isNotEmpty()) {
            testViewModel.setUpListAnswer(listDefaultAnswer)
        }
    }

    //hien thi loading khi chua load xong du lieu
    if (isShowLoading) {
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
            ) {
                LottieAnimation(
                    composition = composition, iterations = LottieConstants.IterateForever
                )
            }
        }
    } else {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            val pagerQuestionState = rememberPagerState(initialPage = 0) {
                listQuestion.size
            }

            var isShowDialog by rememberSaveable {
                mutableStateOf(false)
            }
            var isShowDialogSubmit by rememberSaveable {
                mutableStateOf(false)
            }

            var doneTest by rememberSaveable {
                mutableStateOf(false)
            }

            var onShowSheet by rememberSaveable {
                mutableStateOf(false)
            }

            var explainContent by rememberSaveable {
                mutableStateOf("")
            }

            //map de luu mp[number] = true/false co danh star hay ko
            val mapStar = remember {
                mutableStateMapOf<Int, Boolean>()
            }

            //setup
            LaunchedEffect(Unit) {
                for (i in 1..30) {
                    mapStar[i] = false
                }
            }

            TopBarTestScreen(
                numberQuestion = pagerQuestionState.currentPage + 1,
                isTestMode = isTestMode,
                timeDoTest = timeDoTest,
                stateStar = mapStar,
                onClickBack = {
//                navController.popBackStack()
                    isShowDialog = true
                },
                onClickSubmit = { state ->
                    if (state == 1) {
                        //nop chu dong
                        //->show dialog o day
                        isShowDialogSubmit = true
                    } else {
                        //neu ko la nop bi dong
                        doneTest = true
                    }
                },
                onClickShowHint = {
                    //show sheet o day
                    explainContent = listQuestion[pagerQuestionState.currentPage].explain
                    onShowSheet = true
                },
                onSaveStarQuestion = {idxQuestion,save ->
                    val starQuestion : QuestionStar = QuestionStar(
                       question =  listQuestion[idxQuestion].question,
                        A = listQuestion[idxQuestion].A,
                        B = listQuestion[idxQuestion].B,
                        C = listQuestion[idxQuestion].C,
                        D = listQuestion[idxQuestion].D,
                        answer = listQuestion[idxQuestion].answer,
                        explain = listQuestion[idxQuestion].explain,
                    )
                    if(save){
                        mainViewModel.insertQuestionStar(questionStar = starQuestion)
                    }
                    else{
                        mainViewModel.deleteQuestionStar(questionStar = starQuestion)
                    }
                }
            )
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                val scope = rememberCoroutineScope()
                HorizontalPager(
                    state = pagerQuestionState
                ) { index ->//index nay lam trong vong for
                    ViewDisplayQuestion(
                        numberQuestion = index + 1,
                        question = listQuestion[index],
                        testViewModel = testViewModel,
                        isTestModel = isTestMode
                    ) { indexUserClicked, indexCorrectAnswer ->
                        //luu lai cau tra loi cua user
                        val answerOfUser = Answer(
                            indexQuestion = pagerQuestionState.currentPage,
                            indexUserClicked = indexUserClicked,
                            indexCorrectAnswer = indexCorrectAnswer
                        )
                        testViewModel.addAfterUserAnswer(answerOfUser)
                        if (pagerQuestionState.currentPage < listQuestion.size - 1) {
                            scope.launch {
                                delay(700)
                                pagerQuestionState.animateScrollToPage(
                                    pagerQuestionState.currentPage + 1, animationSpec = tween(
                                        durationMillis = 700, easing = FastOutSlowInEasing
                                    )
                                )
                            }
                        } else {
                            //neu khong phai test mode -> tu dong nop luon
                            if (!isTestMode) {
                                doneTest = true
                            }
                        }
                    }
                }

                if (onShowSheet) {
                    ExplainAnswerView(
                        onShowSheet = true, content = explainContent
                    ) { value ->
                        onShowSheet = value
                    }
                }

                LaunchedEffect(doneTest) {
                    if (doneTest) {
                        navController.navigate(HomeNavScreen.ResultScreen.route) {
                            popUpTo(HomeNavScreen.TestScreen.route) {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                    }
                }

                if (isShowDialogSubmit) {
                    DialogAskUserQuitOrSubmit(
                        modifier = Modifier.align(Alignment.Center),
                        textTitle = Contains.ASK_SUBMIT,
                        textDescription = Contains.DESCRIPTION_ASK,
                        textAction = Contains.SUBMIT,
                        navController = navController,
                        onClickDismiss = {
                            isShowDialogSubmit = false
                        },
                        onSubmitted = {
                            doneTest = true
                        })
                }

                if (isShowDialog) {
                    DialogAskUserQuitOrSubmit(
                        modifier = Modifier.align(Alignment.Center),
                        textTitle = Contains.ASK_QUIT,
                        textDescription = Contains.DESCRIPTION_QUIT,
                        textAction = Contains.QUIT,
                        navController = navController,
                        onClickDismiss = {
                            isShowDialog = false
                        })
                }
            }
        }
    }
}

@Composable
fun ViewDisplayQuestion(
    modifier: Modifier = Modifier,
    numberQuestion: Int,
    question: Question,
    testViewModel: TestViewModel,
    isTestModel: Boolean,
    onNextQuestion: (Int, Int) -> Unit,
    //idClick cai user click
    //indexCorrect cai dap an dung
) {

    //trang thai click / cua 4 cau hoi
    val stateClicked by testViewModel.answeredQuestions.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize(),
    ) {

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
                .padding(vertical = 5.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(width = 100.dp, height = 2.dp)
                    .padding(start = 10.dp)
                    .background(color = Color.Magenta)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                val choice = mapOf(
                    0 to "A", 1 to "B", 2 to "C", 3 to "D"
                )
                var clickAnswer by rememberSaveable {
                    mutableIntStateOf(-1)
                }

                val indexCorrect = if (question.A == question.answer) 0
                else if (question.B == question.answer) 1
                else if (question.C == question.answer) 2
                else 3

                val clicked = stateClicked[numberQuestion] ?: false

                repeat(4) { index ->
                    AnswerChoice(
                        choice = choice[index].toString(),
                        idSelected = index == clickAnswer,
                        isTestMode = isTestModel,
                        isClicked = if (!isTestModel) clicked else index == clickAnswer,
                        isCorrect = index == indexCorrect
                    ) {
                        if (!isTestModel) {
                            //neu ko phai test mode -> cho click 1 lan
                            if (!clicked) {
                                //khi click xong ms cho biet dap an
                                clickAnswer = index
                                testViewModel.answeredQuestion(numberQuestion)
                                onNextQuestion(clickAnswer, indexCorrect)//sang cau moi
                            }
                        } else {
                            clickAnswer = index
                            onNextQuestion(clickAnswer, indexCorrect)//sang cau moi
                        }
                    }
                }
            }
        }
    }
}

//0 = A,1 = B,2 = C,3 = D
@Composable
fun AnswerChoice(
    modifier: Modifier = Modifier,
    choice: String,
    idSelected: Boolean,
    isTestMode: Boolean,
    isCorrect: Boolean,
    isClicked: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .noRippleClickable {
                onClick()
            },
        shape = CircleShape,
        border = BorderStroke(
            width = 1.dp,
            color = if (isClicked && (idSelected && isCorrect || !idSelected && isCorrect || idSelected)) Color.Transparent else Color.Black
        ),
        colors = CardDefaults.cardColors(
            containerColor = if (!isTestMode) {
                if (!isClicked) Color.White
                else {
                    when {
                        idSelected && isCorrect -> Color.Green//chon dung
                        idSelected && !isCorrect -> Color.Red.copy(0.8f)//chon sai thi mau do
                        !idSelected && isCorrect -> Color.Green
                        else -> Color.White
                    }
                }
            } else {
                if (isClicked) Color.Magenta.copy(0.8f)
                else Color.White
            }
        )
    ) {
        Box(
            modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
        ) {
            Text(
                text = choice,
                color = if (isClicked && (idSelected && isCorrect || !idSelected && isCorrect || idSelected)) Color.White else Color.Black.copy(
                    0.6f
                ),
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
fun ViewQuestion(
    modifier: Modifier = Modifier, question: Question
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.Magenta.copy(0.3f))
            ) {
                Text(
                    modifier = Modifier.padding(10.dp),
                    text = question.question,
                    fontWeight = FontWeight.Normal
                )
            }

            Spacer(modifier = Modifier.height(15.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    modifier = Modifier.padding(vertical = 8.dp),
                    text = "A. ${question.A}",
                    fontWeight = FontWeight.Normal
                )
                Text(
                    modifier = Modifier.padding(vertical = 8.dp),
                    text = "B. ${question.B}",
                    fontWeight = FontWeight.Normal
                )
                Text(
                    modifier = Modifier.padding(vertical = 8.dp),
                    text = "C. ${question.C}",
                    fontWeight = FontWeight.Normal
                )
                Text(
                    modifier = Modifier.padding(vertical = 8.dp),
                    text = "D. ${question.D}",
                    fontWeight = FontWeight.Normal
                )
            }
        }
    }
}

@SuppressLint("DefaultLocale")
@Composable
fun TopBarTestScreen(
    modifier: Modifier = Modifier,
    numberQuestion: Int,
    isTestMode: Boolean,
    timeDoTest: Int,
    stateStar: MutableMap<Int, Boolean>,
    onClickBack: () -> Unit,
    onClickShowHint: () -> Unit,
    onClickSubmit: (Int) -> Unit,
    onSaveStarQuestion :(Int, Boolean) -> Unit
) {

    val isStar = stateStar[numberQuestion] ?: false

    //luu lai vao room
    //ten kia luc setup map thi neu index nao da o trong star store roi thi se true

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
            .padding(start = 16.dp, end = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            IconButton(
                onClick = {
                    onClickBack()
                }, modifier = Modifier.clip(CircleShape)
            ) {
                Icon(
                    Icons.Default.ArrowBackIos, contentDescription = null
                )
            }
            Text(
                text = "Question $numberQuestion",
                fontWeight = FontWeight.Normal,
            )

            Icon(
                imageVector = if (!isStar) Icons.Outlined.StarOutline
                else Icons.Filled.Star,
                contentDescription = null,
                tint = if (!isStar) Color.Black.copy(0.5f)
                else colorResource(R.color.progressColor),
                modifier = Modifier
                    .padding(13.dp)
                    .noRippleClickable {
                        stateStar[numberQuestion] = !isStar
                        //save or del
                        onSaveStarQuestion(numberQuestion - 1, stateStar[numberQuestion] == true)
                        //stateStar[numberQuestion] (la mutable thay doi) -> recompose lai UI -> chay lai ham nay -> isStar thay doi
                    },
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            if (isTestMode) {
                var timeTest by remember {
                    mutableIntStateOf(timeDoTest * 60)
                }

                LaunchedEffect(Unit) {
                    while (timeTest > 0) {
                        delay(1000)//gia lap de lay 1s
                        timeTest--
                    }
                }

                val minutes = timeTest / 60
                val second = timeTest % 60

                Text(
                    text = String.format("%02d:%02d", minutes, second),
                    fontWeight = FontWeight.SemiBold,
                    color = if (timeTest < 60) Color.Red.copy(0.8f) else Color.Green
                )

                if (timeTest == 0) {
                    //{0) la nop do het thoi gian
                    onClickSubmit(0)
                }
                Text(
                    text = "Submit", modifier = Modifier.noRippleClickable {
                            onClickSubmit(1)//(1) la nop chu dong
                        }, fontWeight = FontWeight.SemiBold
                )
            } else {
                Text(
                    text = "Explain",
                    modifier = Modifier.noRippleClickable {
                            onClickShowHint()
                        },
                    fontWeight = FontWeight.SemiBold,
                    color = colorResource(R.color.progressColor)
                )
            }
        }
    }
}

//hien thi khi user nhan <
@Composable
fun DialogAskUserQuitOrSubmit(
    modifier: Modifier = Modifier,
    textTitle: String,
    textDescription: String,
    textAction: String,
    onClickDismiss: () -> Unit,
    navController: NavController,
    onSubmitted: (() -> Unit)? = null
) {

    var isShow by rememberSaveable {
        mutableStateOf(true)
    }

    if (isShow) {
        Dialog(
            onDismissRequest = {
                isShow = false
                onClickDismiss()
            },
            properties = DialogProperties(usePlatformDefaultWidth = false)//mo rong het chieu ngang cua dien thoai
        ) {
            Card(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 26.dp)
                    .wrapContentHeight(),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.elevatedCardElevation(
                    defaultElevation = 5.dp
                ),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Icon(
                            Icons.Filled.QuestionMark,
                            contentDescription = null,
                            tint = Color.Red.copy(0.8f)
                        )
                        Text(
                            text = textTitle, fontWeight = FontWeight.Bold, fontSize = 18.sp
                        )
                    }

                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        text = textDescription,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Normal
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(
                            modifier = Modifier.size(width = 80.dp, height = 35.dp),
                            contentPadding = PaddingValues(vertical = 8.dp),
                            onClick = {
                                isShow = false
                                if (textAction != Contains.SUBMIT) {
                                    navController.popBackStack()
                                } else {
                                    onSubmitted?.invoke()
                                }
                            },
                            shape = RoundedCornerShape(5.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Blue.copy(0.4f)
                            )
                        ) {
                            Text(
                                text = textAction,
                                color = Color.White,
                                fontWeight = FontWeight.Normal
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Button(
                            modifier = Modifier.size(width = 80.dp, height = 35.dp),
                            contentPadding = PaddingValues(vertical = 8.dp),
                            onClick = {
                                onClickDismiss()
                            },
                            shape = RoundedCornerShape(5.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.White
                            ),
                            border = BorderStroke(width = 0.8.dp, color = Color.Gray)
                        ) {
                            Text(
                                text = "Cancel",
                                color = Color.Black.copy(0.8f),
                                fontWeight = FontWeight.Normal
                            )
                        }
                    }
                }
            }
        }
    }
}