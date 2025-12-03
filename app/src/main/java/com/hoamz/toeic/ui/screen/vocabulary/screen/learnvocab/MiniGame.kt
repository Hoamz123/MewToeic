package com.hoamz.toeic.ui.screen.vocabulary.screen.learnvocab

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.hoamz.toeic.ui.component.TopBar

@Composable
fun MiniGame(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    Column(
        modifier = Modifier.fillMaxSize()
            .navigationBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TopBar(
            nameTab = "Mini Game"
        ) {
            navController.popBackStack()
        }
    }
}