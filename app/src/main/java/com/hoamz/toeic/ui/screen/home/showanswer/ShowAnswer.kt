package com.hoamz.toeic.ui.screen.home.showanswer

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.hoamz.toeic.R
import com.hoamz.toeic.base.BannerAdView
import com.hoamz.toeic.base.BaseSharePref
import com.hoamz.toeic.ui.component.TopBar
import com.hoamz.toeic.ui.screen.navigation.HomeNavScreen
import com.hoamz.toeic.ui.screen.home.test.Answer
import com.hoamz.toeic.ui.screen.home.test.TestViewModel
import kotlin.collections.listOf


@Composable
fun ShowAnswer(
    modifier: Modifier = Modifier,
    navController : NavController,
    testViewModel: TestViewModel,
    showAnswerViewModel: ShowAnswerViewModel
) {

    val tabName = BaseSharePref.getTabPrevious()

    //danh sach chon cua user
    val listAnswerOfUser : List<Answer> by testViewModel.listAnswer.collectAsState()
    //neu nhu list nay trong -> do case tu room lay ra -> nhu binh thuong

    LaunchedEffect(Unit) {
        testViewModel.getAllAnswerByCondition()
    }

    val listCorrectAnswerOfUser : List<Answer> by testViewModel.listCorrectAnswer.collectAsState()
    val listWrongAnswerOfUser : List<Answer> by testViewModel.listWrongAnswer.collectAsState()

    val all = remember(listAnswerOfUser) { listAnswerOfUser }
    val correct = remember(listCorrectAnswerOfUser) { listCorrectAnswerOfUser }
    val wrong = remember(listWrongAnswerOfUser) { listWrongAnswerOfUser }


    //list tab
    val listTab by rememberSaveable {
        mutableStateOf(listOf(
            "All",
            "Correct",
            "Wrong"
        ))
    }

    var listShow by remember {
        mutableStateOf(
            value = when(tabName){
                "All" -> all
                "Correct" -> correct
                "Wrong" -> wrong
                else -> all
            }
        )
    }

    Column(
        modifier = Modifier.fillMaxSize()
            .background(color = Color.White.copy(0.8f))
            .navigationBarsPadding(),
    ) {

        TopBar(
            nameTab = "Show Answers"
        ) {
            navController.popBackStack()
            BaseSharePref.saveTabPrevious(0)
        }

        Spacer(modifier = Modifier.height(8.dp))

        var indexClicked by rememberSaveable {
            mutableIntStateOf(0)
        }
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(color = Color.LightGray.copy(0.4f)),
            verticalAlignment = Alignment.CenterVertically
        ){
            repeat(listTab.size){index->
                Tab(
                    modifier = Modifier
                        .weight(1f)
                        .padding(vertical = 6.dp),
                    isClicked = index == indexClicked,
                    nameTab = listTab[index]
                ) {
                    indexClicked = index
                    BaseSharePref.saveTabPrevious(index)
                    listShow = when (index) {
                        0 -> {
                            //ALL
                            all

                        }
                        1 -> {
                            //correct
                            correct
                        }
                        else -> {
                            //wrong
                            wrong
                        }
                    }
                }
            }
        }

        //list cau hoi da tra loi
        val choice = mapOf(
            0 to "A",
            1 to "B",
            2 to "C",
            3 to "D"
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(10.dp),
        ) {
            items(listShow.size){index ->
                if(index == 5){
                    Row (
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ){
                        BannerAdView()
                    }
                }
                val checkCorrect by remember{
                    derivedStateOf {
                        listShow[index].indexUserClicked == listShow[index].indexCorrectAnswer
                    }
                }
                val imagePainter = if(checkCorrect) painterResource(R.drawable.ic_correct_answer) else painterResource(R.drawable.ic_wrong_answer)

                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .clickable{
                            showAnswerViewModel.onClickedAnswer(listShow[index].indexQuestion)
                            //qua man hinh detail
                            navController.navigate(HomeNavScreen.ResultDetail.route)
                        }
                        .padding(vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ){

                    Row (
                        modifier = Modifier.weight(1f)
                            .padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ){
                        //icon
                        Icon(
                            painter = imagePainter,
                            contentDescription = null,
                            tint = Color.Unspecified
                        )
                        //question number
                        Text(
                            text = "${listShow[index].indexQuestion + 1}.",
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                    //4 dap an
                    Row (
                        modifier = Modifier
                            .weight(3f)
                            .padding(5.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceAround
                    ){
                        repeat(4){i->
                            Card(
                                modifier = Modifier
                                    .size(35.dp)
                                    .clip(CircleShape),
                                colors = CardDefaults.cardColors(
                                    containerColor = when (i) {
                                        listShow[index].indexUserClicked if i != listShow[index].indexCorrectAnswer -> Color.Red.copy(0.8f) // sai
                                        listShow[index].indexCorrectAnswer -> Color.Green // dung
                                        else -> Color.White
                                    }
                                ),
                                elevation = CardDefaults.cardElevation(
                                    defaultElevation = 3.dp
                                )
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(35.dp)
                                        .clip(CircleShape),
                                    contentAlignment = Alignment.Center
                                ){
                                    Text(
                                        text = choice[i].toString(),
                                        fontWeight = FontWeight.SemiBold,
                                        color = if(i == listShow[index].indexUserClicked
                                            || i == listShow[index].indexCorrectAnswer)
                                            Color.White else Color.Black.copy(0.6f),
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Tab(
    modifier: Modifier = Modifier,
    isClicked : Boolean,
    nameTab : String,
    onClicked :() -> Unit
) {
    Row (
        modifier = modifier
            .padding(horizontal = 5.dp)
    ){
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(if (isClicked) Color.White else Color.Transparent)
                .clickable {
                    onClicked()
                },
            contentAlignment = Alignment.Center
        ){
            Text(
                modifier = Modifier.padding(vertical = 8.dp),
                text = nameTab,
                fontWeight = FontWeight.Normal
            )
        }
    }
}
