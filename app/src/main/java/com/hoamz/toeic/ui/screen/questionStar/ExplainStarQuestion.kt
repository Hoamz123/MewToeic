package com.hoamz.toeic.ui.screen.questionStar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.hoamz.toeic.R
import com.hoamz.toeic.base.BannerAdView
import com.hoamz.toeic.baseviewmodel.MainViewModel
import com.hoamz.toeic.data.local.QuestionStar
import com.hoamz.toeic.ui.component.TopBar
import com.hoamz.toeic.utils.ModifierUtils

@Composable
fun ExplainStarQuestion(
    modifier: Modifier = Modifier,
    mainViewModel: MainViewModel,
    navController: NavController
) {
    val starQuestion : QuestionStar? by mainViewModel.starQuestion.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize()
            .background(color = Color.White.copy(0.8f))
            .padding(horizontal = 5.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopBar(
            nameTab = "Explain"
        ) {
            navController.popBackStack()
        }

        Column(
            modifier = Modifier.fillMaxSize()
                .padding(5.dp)
        ) {
            starQuestion?.question?.let {
                Text(
                    text = it, fontWeight = FontWeight.SemiBold,
                    color = colorResource(R.color.progressColor)
                )
            }

            ModifierUtils.SpaceHeigh(10.dp)

            repeat(4){index ->
                Row(
                    modifier = Modifier.fillMaxWidth()
                        .padding(horizontal = 10.dp)
                ) {
                    Text(
                        text = when (index) {
                            0 -> "A ${starQuestion?.A}"
                            1 -> "B ${starQuestion?.B}"
                            2 -> "C ${starQuestion?.C}"
                            else -> "D ${starQuestion?.D}"
                        },
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Start
                    )
                }
            }

            ModifierUtils.SpaceHeigh(10.dp)

            starQuestion?.answer?.let {
                Row(
                    modifier = Modifier.fillMaxWidth()
                        .padding(horizontal = 10.dp)
                ) {
                    Text(
                        text =  "Correct : $it",
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Green,
                        textAlign = TextAlign.Start
                    )
                }
            }
            ModifierUtils.SpaceHeigh(10.dp)

            starQuestion?.explain?.let {
                Text(
                    text = it,
                    fontWeight = FontWeight.Normal,
                    color = Color.Magenta.copy(0.8f)
                )
            }

            ModifierUtils.SpaceHeigh(10.dp)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                BannerAdView()
            }
        }
    }
}

