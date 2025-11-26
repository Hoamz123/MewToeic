package com.hoamz.toeic.ui.screen.vocabulary.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.hoamz.toeic.R
import com.hoamz.toeic.data.remote.VocabDisplay
import com.hoamz.toeic.ui.screen.vocabulary.AppDictionaryViewModel
import com.hoamz.toeic.ui.screen.vocabulary.viewmodel.SelectWordsViewmodel
import com.hoamz.toeic.utils.ModifierUtils

@Composable
fun WordDetail(
    modifier: Modifier = Modifier,
    navController: NavController,
    selectWordsViewmodel: SelectWordsViewmodel,
    appDictionaryViewModel: AppDictionaryViewModel
) {
    //lay ra chi tiet cua tu
    val vocabularyDisplay : VocabDisplay = appDictionaryViewModel.vocabDisplay.collectAsState().value

    Column(
        modifier = Modifier.fillMaxSize()
            .background(color = Color.White.copy(0.8f))
            .navigationBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(vertical = 10.dp)
                .padding(start = 16.dp, end = 26.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = {
                        navController.popBackStack()
                    },
                    modifier = Modifier.clip(CircleShape)
                ) {
                    Icon(Icons.Default.ArrowBackIos, contentDescription = null)
                }

                ModifierUtils.SpaceWidth(10.dp)
                Text(text = "Word Details", fontWeight = FontWeight.Normal)
            }

            Text(
                text = "Mastered",
                color = colorResource(R.color.progressColor),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Normal
            )
        }

        Column(
            modifier = Modifier.fillMaxSize()
                .background(color = Color.Green)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            repeat(20){ _ ->
                Text(
                    text = "Test",
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Normal,
                    color = Color.White
                )
                ModifierUtils.SpaceHeigh(100.dp)
            }
        }
    }
}