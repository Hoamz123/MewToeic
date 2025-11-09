package com.hoamz.toeic.ui.screen.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Analytics
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.rounded.StarOutline
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.hoamz.toeic.ui.screen.home.listtest.ListTestHomeScreen
import com.hoamz.toeic.ui.screen.home.result.ResultScreen
import com.hoamz.toeic.ui.screen.home.setuptest.SetUpBeforeTestScreen
import com.hoamz.toeic.ui.screen.home.test.TestScreen
import com.hoamz.toeic.ui.screen.vocabulary.screen.Vocabulary
import com.hoamz.toeic.ui.screen.starScreen.WrongScreen
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.navigation
import com.hoamz.toeic.baseviewmodel.MainViewModel
import com.hoamz.toeic.ui.screen.home.component.BottomMenuItem
import com.hoamz.toeic.ui.screen.home.component.ItemBottomNav
import com.hoamz.toeic.ui.screen.home.resultdetail.ShowResultDetail
import com.hoamz.toeic.ui.screen.home.showanswer.ShowAnswer
import com.hoamz.toeic.ui.screen.home.showanswer.ShowAnswerViewModel
import com.hoamz.toeic.ui.screen.home.test.TestViewModel
import com.hoamz.toeic.ui.screen.splash.SplashScreen
import com.hoamz.toeic.ui.screen.home.selectVocab.SelectVocabScreen
import com.hoamz.toeic.ui.screen.vocabulary.viewmodel.SelectWordsViewmodel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    mainViewModel: MainViewModel,
    testViewModel: TestViewModel,
    showAnswerViewModel: ShowAnswerViewModel,
    selectWordsViewmodel: SelectWordsViewmodel
) {
    val navController = rememberNavController()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {

        NavHost(
            navController = navController, startDestination = "splash"
        ) {
            //splash
            composable(route = "splash") {
                SplashScreen {
                    navController.navigate("home") {
                        popUpTo("splash") {
                            inclusive = true
                        }
                    }
                }
            }

            mainHome(
                navController = navController,
                mainViewModel = mainViewModel,
                testViewModel = testViewModel,
                selectWordsViewmodel = selectWordsViewmodel
            )

            //cac man hinh chi tiet
            composable(route = HomeNavScreen.SetupScreen.route) {
                SetUpBeforeTestScreen(
                    navController = navController, mainViewModel = mainViewModel
                )
            }

            composable(route = HomeNavScreen.ResultScreen.route) {
                ResultScreen(
                    navController = navController,
                    testViewModel = testViewModel,
                    mainViewModel = mainViewModel
                )
            }

            composable(route = HomeNavScreen.TestScreen.route) {
                TestScreen(
                    navController = navController,
                    mainViewModel = mainViewModel,
                    testViewModel = testViewModel
                )
            }

            composable(route = HomeNavScreen.ShowAnswers.route) {
                ShowAnswer(
                    navController = navController,
                    testViewModel = testViewModel,
                    showAnswerViewModel = showAnswerViewModel
                )
            }

            composable(route = HomeNavScreen.ResultDetail.route) {
                ShowResultDetail(
                    navController = navController,
                    testViewModel = testViewModel,
                    mainViewModel = mainViewModel,
                    showAnswerViewModel = showAnswerViewModel
                )
            }
            //vocabulary o day
            composable(route = HomeNavScreen.SelectVocabulary.route) {
                SelectVocabScreen(
                    navController = navController,
                    mainViewModel = mainViewModel,
                    selectWordsViewmodel = selectWordsViewmodel
                )
            }

            //tao ra 1 man hinh practice o day

        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.mainHome(
    navController: NavHostController,
    mainViewModel: MainViewModel,
    testViewModel: TestViewModel,
    selectWordsViewmodel: SelectWordsViewmodel,
) {
    navigation(
        startDestination = HomeNavScreen.ListTestScreen.route, route = "main_home"
    ) {
        composable(
            "home"
        ) {
            MainHome(
                rootNavController = navController,
                mainViewModel = mainViewModel,
                testViewModel = testViewModel,
                selectWordsViewmodel = selectWordsViewmodel
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainHome(
    modifier: Modifier = Modifier,
    rootNavController: NavController,
    mainViewModel: MainViewModel,
    testViewModel: TestViewModel,
    selectWordsViewmodel: SelectWordsViewmodel,
) {
    val listItemMenu by remember {
        mutableStateOf(
            listOf(
                ItemBottomNav("Home", HomeNavScreen.ListTestScreen.route, Icons.Outlined.Home),
                ItemBottomNav("Star", HomeNavScreen.WrongScreen.route, Icons.Rounded.StarOutline),
                ItemBottomNav(
                    "Vocabulary",
                    HomeNavScreen.Vocabulary.route,
                    Icons.Outlined.Analytics
                )
            )
        )
    }

    val navController = rememberNavController()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        NavHost(
            navController = navController, startDestination = HomeNavScreen.ListTestScreen.route
        ) {
            composable(route = HomeNavScreen.ListTestScreen.route) {
                ListTestHomeScreen(
                    navController = rootNavController,
                    mainViewModel = mainViewModel,
                    testViewModel = testViewModel,
                )
            }
            composable(route = HomeNavScreen.WrongScreen.route) {
                WrongScreen()
            }

            composable(route = HomeNavScreen.Vocabulary.route) {
                Vocabulary(
                    selectWordsViewmodel = selectWordsViewmodel
                )
            }
        }

        val navBackStackEntry = navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry.value?.destination?.route
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(elevation = 50.dp)
                .align(alignment = Alignment.BottomCenter)
                .background(color = Color.White)
                .navigationBarsPadding()
                .statusBarsPadding(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            listItemMenu.forEachIndexed { index, nav ->
                BottomMenuItem(
                    modifier = Modifier.weight(1f),
                    selected = (currentDestination == nav.route),
                    icon = nav.icon,
                    text = nav.title,
                ) {
                    navController.navigate(nav.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            }
        }
    }
}

