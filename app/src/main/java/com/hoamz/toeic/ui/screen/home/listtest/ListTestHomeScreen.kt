package com.hoamz.toeic.ui.screen.home.listtest

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.hoamz.toeic.base.BannerAdView
import com.hoamz.toeic.baseviewmodel.MainViewModel
import com.hoamz.toeic.data.local.ActivityRecent
import com.hoamz.toeic.ui.screen.home.HomeNavScreen
import com.hoamz.toeic.ui.screen.home.component.ListTest
import com.hoamz.toeic.ui.screen.home.component.LottieHorizontal
import com.hoamz.toeic.ui.screen.home.component.TestCurrent
import com.hoamz.toeic.ui.screen.home.component.TopBarHome
import com.hoamz.toeic.ui.screen.home.test.TestViewModel
import com.hoamz.toeic.utils.Contains
import com.hoamz.toeic.utils.ModifierUtils.noRippleClickable


@Composable
fun ListTestHomeScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    mainViewModel: MainViewModel,
    testViewModel: TestViewModel,
    listTestViewModel: ListTestViewModel = viewModel()
) {

    //lay ra list act current
    val activitiesRecent  : List<ActivityRecent> by mainViewModel.activitiesRecent.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White.copy(0.8f))
            .statusBarsPadding(),
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {
        item {
            TopBarHome(
                username = "username"
            )
            Spacer(modifier = Modifier.height(5.dp))
        }

        item {
            LottieHorizontal()
        }

        item{
            ListTest(onClickCategories = {
                //mo dialog hien thi list de
                //no den may nua lam
            }
            ) {index ->
                mainViewModel.setTestNumber(index + 1)
                //tam thoi se qua setup
                navController.navigate(HomeNavScreen.SetupScreen.route)
            }
        }
        item {
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp)
            ){
                var isShowRecent by rememberSaveable {
                    mutableStateOf(false)
                }
                Text(
                    text = Contains.RECENT,
                    modifier = Modifier.noRippleClickable{
                        isShowRecent = !isShowRecent
                        listTestViewModel.controllerStateListCurrent(isShowRecent)
                    },
                    fontWeight = FontWeight.Normal
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = if(isShowRecent) Icons.Default.ArrowDropDown else Icons.Default.ArrowDropUp,
                    modifier = Modifier
                        .clip(CircleShape)
                        .clickable {
                            isShowRecent = !isShowRecent
                            listTestViewModel.controllerStateListCurrent(isShowRecent)
                        },
                    contentDescription = null,
                    tint = Color.LightGray
                )
            }
        }

        item {
            val stateListCurrent by listTestViewModel.isShowListTestCurrent.collectAsState()
            AnimatedVisibility(
                visible = stateListCurrent,
                enter = expandVertically( // bung xuống từ trên
                    expandFrom = Alignment.Top
                ) + fadeIn(),
                exit = shrinkVertically( // thu lên trên
                    shrinkTowards = Alignment.Top
                ) + fadeOut()
            ) {
                Column (
                    modifier = Modifier.fillMaxWidth()
                        .padding(2.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    repeat(activitiesRecent.size){index->
                        if(index == 2){
                            BannerAdView()
                        }
                        val numberCorrect = activitiesRecent[index].numberAnswerCorrect
                        TestCurrent(nameTest = activitiesRecent[index].nameTest,numberCorrect = numberCorrect,numberQuestion = 30){
                            mainViewModel.passQuestionRecent(activitiesRecent[index].listQuestion)
                            testViewModel.setUpListAnswer(activitiesRecent[index].listAnswer)
                            navController.navigate(HomeNavScreen.ResultScreen.route)
                        }
                    }
                }
            }
        }

        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .padding(bottom = 20.dp),
                contentAlignment = Alignment.BottomCenter
            ){}
        }
    }
}
