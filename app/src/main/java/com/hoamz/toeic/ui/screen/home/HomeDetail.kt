package com.hoamz.toeic.ui.screen.home

import android.util.Log
import android.widget.ProgressBar
import android.widget.Toast
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.sharp.Favorite
import androidx.compose.material3.BottomAppBarState
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimatable
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.hoamz.toeic.R
import com.hoamz.toeic.base.BaseSharePref
import com.hoamz.toeic.data.local.Question
import com.hoamz.toeic.utils.Contains
import com.hoamz.toeic.utils.ModifierUtils.noRippleClickable
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun TopBarHome(
    modifier: Modifier = Modifier,
    username : String,
) {
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 16.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ){
        Row (
            modifier = Modifier
                .weight(1f)
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Image(
                painter = painterResource(R.drawable.img_5),
                contentDescription = null,
                contentScale = ContentScale.Inside,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.width(10.dp))

            Text(
                text = username,
                fontWeight = FontWeight.Normal,
                fontFamily = FontFamily.Default
            )
        }

        Row (
            modifier = Modifier
                .size(width = 35.dp, height = 35.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ){
            val progressInitialize = BaseSharePref.getProgressSteak()
            CalculatorProgressSteak(
                modifier = Modifier.fillMaxSize(),
                progressInitialize = progressInitialize
            )
        }
    }
}

//calculatorProgress
@Composable
fun CalculatorProgressSteak(
    modifier: Modifier = Modifier,
    progressInitialize : Int,//gia tri mac dinh ban dau khoi tao
) {
    var progress by remember {
        mutableIntStateOf(progressInitialize)
    }

    //ban dau van dang trong qua trinh tang
    var onProgressing by remember {
        mutableStateOf(true)
    }

    //kiem tra xem trc do da hoan thanh progress hay chua
    val finished = BaseSharePref.onFinishedProgress()

    //moi s thi tang them 1
    LaunchedEffect(Unit) {
        while(progress < 100 && onProgressing){
            delay(1000)//on 100s thi + 1 steak
            progress++
            BaseSharePref.saveProgressSteak(progress)
        }
        //neu nhu da du dieu kien
        if(progress == 100 && !finished){
            //dung qua trinh tang
            onProgressing = false
            //tang them 1 steak
            BaseSharePref.finishedProgress()//hoan thanh
            BaseSharePref.saveNumberSteak()
            progress++
            //de ngay hom nay no khong chay nua(qua hom sau se dc reset lai)
        }
    }

    //khoi tao anim
    val progressCircle by animateFloatAsState(
        targetValue = progress.toFloat() / 100.toFloat(),
        animationSpec = tween(
            easing = LinearOutSlowInEasing
        )
    )

    //tao circle progress
    //loi dung tinh chat de nen nhau cua box de xu ly case nay
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            progress = {1f},//lam nen,
            modifier = Modifier.fillMaxSize(),
            color = Color.LightGray.copy(0.5f),
            strokeWidth = 4.dp,
            trackColor = ProgressIndicatorDefaults.circularIndeterminateTrackColor,
            strokeCap = ProgressIndicatorDefaults.CircularDeterminateStrokeCap
        )

        CircularProgressIndicator(
            progress = {progressCircle},
            modifier = Modifier.fillMaxSize()
                .graphicsLayer{
                    rotationZ = 180f
                },
            color = colorResource(R.color.progressColor),
            strokeWidth = 4.dp,
            trackColor = ProgressIndicatorDefaults.circularIndeterminateTrackColor,
            strokeCap = ProgressIndicatorDefaults.CircularDeterminateStrokeCap
        )

        val context = LocalContext.current

        IconButton(
            modifier = modifier
                .padding(5.dp),
            onClick = {
                val cnt = BaseSharePref.getNumberSteak()
                Toast.makeText(context,"$cnt", Toast.LENGTH_LONG).show()
            }
        ) {
            Icon(
                Icons.Filled.LocalFireDepartment,
                contentDescription = null,
                tint = Color.Red.copy(0.6f)
            )
        }
    }
}

//view animation
@Composable
fun LottieHorizontal(
    modifier: Modifier = Modifier
) {
    val listLottie = listOf(
        "cat1.json",
        "cat2.json",
        "cat6.json"
    )

    val pagerState = rememberPagerState(initialPage = 0){
        listLottie.size
    }

    LaunchedEffect(Unit) {
        while (true){
            delay(5000)
            val currentPage = (pagerState.currentPage + 1) % listLottie.size
            pagerState.animateScrollToPage(currentPage,
                animationSpec = tween(
                    durationMillis = 2000,
                    easing = FastOutSlowInEasing
                )
            )
        }
    }

    Card (
        modifier = Modifier
            .fillMaxWidth()
            .height(130.dp)
            .padding(10.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp
        ),
    ){
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) {page ->
            LottieAnim(
                anim = listLottie[page],
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
fun LottieAnim(
    modifier: Modifier = Modifier,
    anim : String
) {
    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.Asset(anim)
    )

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ){
        LottieAnimation(composition = composition, iterations = LottieConstants.IterateForever)
    }
}

@Composable
fun ListTest(
    modifier: Modifier = Modifier,
    onClickCategories :() -> Unit,
    onClickTest :(Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = Contains.CATEGORIES,
                fontWeight = FontWeight.Normal
            )
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                Icons.Default.ArrowRight,
                contentDescription = null,
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable {
                        onClickCategories()
                    },
                tint = Color.LightGray
            )
        }
        Box(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ){
            LazyRow(
                modifier = Modifier
                    .wrapContentHeight()
                    .padding(3.dp),
                contentPadding = PaddingValues(vertical = 5.dp)
            ) {
                items(10) { index ->
                    CardTest(testNumber = (index + 1).toString()){
                        onClickTest(index)
                    }
                }
            }
        }
    }
}

@Composable
fun CardTest(
    modifier: Modifier = Modifier,
    testNumber : String,
    onClick :() -> Unit
) {
    if(testNumber != "0"){
        Spacer(modifier = Modifier.width(5.dp))
    }
    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .size(60.dp)
            .noRippleClickable {
                onClick()
            },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp
        )
    ){
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            contentAlignment = Alignment.Center
        ){
            Text(
                text = "Test $testNumber",
                color = Color.Black,
            )
        }

    }
    Spacer(modifier = Modifier.width(5.dp))
}

@Composable
fun TestCurrent(
    nameTest : String,
    numberQuestion : Int,
    numberCorrect : Int,
    modifier: Modifier = Modifier,
    onClick :() -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .noRippleClickable {
                onClick()
            },
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp
        ),
    ){
        Row (
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ){
            Column(
                modifier = Modifier
                    .weight(2f)
                    .padding(12.dp)
            ) {
                Text(
                    text = nameTest,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = "$numberQuestion Question",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal
                )
            }
            CircularProgressIndicatorWithText(
                score = numberCorrect,
                total = numberQuestion,
                modifier = Modifier.weight(1f),
                size = 65
            )
        }
    }
}

@Composable
fun CircularProgressIndicatorWithText(
    score: Int,
    total: Int,
    size : Int,
    modifier: Modifier = Modifier,
    strokeWidth: Dp = 5.dp,
) {
    val progress = score.toFloat() / total.toFloat()

    val color : Color = if(score <= 5) Color.Red else if(score <= 15) colorResource(R.color.progressColor) else Color.Green

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(size.dp)
            .padding(8.dp)
    ) {

        CircularProgressIndicator(
            progress = { 1f },
            modifier = Modifier.fillMaxSize(),
            color = color.copy(alpha = 0.1f),
            strokeWidth = strokeWidth - 1.dp,
            trackColor = ProgressIndicatorDefaults.circularIndeterminateTrackColor,
            strokeCap = ProgressIndicatorDefaults.CircularDeterminateStrokeCap,
        )

        CircularProgressIndicator(
            progress = {
                progress
            },
            modifier = Modifier.fillMaxSize(),
            color = color,
            strokeWidth = strokeWidth - 1.dp,
            trackColor = ProgressIndicatorDefaults.circularIndeterminateTrackColor,
            strokeCap = ProgressIndicatorDefaults.CircularDeterminateStrokeCap,
        )

        Text(
            text = "$score/$total",
            fontSize = 12.sp
        )
    }
}

//bottom sheet show  explain cua hoi
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExplainAnswerView(
    modifier: Modifier = Modifier,
    content : String? = null,
    onShowSheet : Boolean,
    onHideSheet :(Boolean) -> Unit
) {
    val modalBottomSheet = rememberModalBottomSheetState()

    val scope = rememberCoroutineScope()

    val hideSheet = {
        scope.launch {
            modalBottomSheet.hide()
        }
    }

    if(onShowSheet){
        ModalBottomSheet(
            sheetState = modalBottomSheet,
            containerColor = Color.Transparent,
            dragHandle = { null },
            scrimColor = Color.Transparent,
            onDismissRequest = {
                hideSheet().invokeOnCompletion {
                    onHideSheet(false)
                }
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Transparent)
                    .height(500.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(width = 100.dp, height = 20.dp)
                        .clip(RoundedCornerShape(topEnd = 8.dp, topStart = 8.dp))
                        .background(color = Color.DarkGray.copy(0.3f)),
                    contentAlignment = Alignment.Center
                ){
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(start = 5.dp, end = 5.dp),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(2.dp)
                                .background(color = Color.White.copy(0.5f))
                        )
                        Spacer(modifier = Modifier.height(3.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(2.dp)
                                .background(Color.White.copy(0.5f))
                        )
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(shape = RoundedCornerShape(topEnd = 20.dp, topStart = 20.dp)),
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp)
                            .background(color = Color.Magenta.copy(0.6f)),
                        contentAlignment = Alignment.CenterStart
                    ){
                        Text(
                            text = "Explain",
                            modifier = Modifier.padding(start = 16.dp),
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White
                        )

                        IconButton(
                            onClick = {
                                hideSheet().invokeOnCompletion {
                                    onHideSheet(false)
                                }
                            },
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .padding(end = 10.dp)
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_exit),
                                contentDescription = null,
                                tint = Color.Unspecified
                            )
                        }

                    }
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .background(color = Color.White)
                            .padding(start = 10.dp, end = 10.dp, top = 10.dp)
                    ) {
                        Text(
                            text = content ?: "null",
                            color = Color.Black
                        )
                    }
                }
            }
        }
    }
}




