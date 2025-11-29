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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.hoamz.toeic.R
import com.hoamz.toeic.data.remote.VocabDisplay
import com.hoamz.toeic.ui.screen.vocabulary.AppDictionaryViewModel
import com.hoamz.toeic.ui.screen.vocabulary.component.AudioCard
import com.hoamz.toeic.ui.screen.vocabulary.viewmodel.SelectWordsViewmodel
import com.hoamz.toeic.ui.screen.vocabulary.viewmodel.VocabularyViewModel
import com.hoamz.toeic.utils.AppToast
import com.hoamz.toeic.utils.ModifierUtils
import com.hoamz.toeic.utils.ModifierUtils.noRippleClickable
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun WordDetail(
    modifier: Modifier = Modifier,
    navController: NavController,
    vocabularyViewModel: VocabularyViewModel,
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
        }

        Column(
            modifier = Modifier.fillMaxSize()
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            //text cua tu
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = vocabularyDisplay.word,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = colorResource(R.color.bg_btn_cancel),
                )
            }

            ModifierUtils.SpaceHeigh(15.dp)

            if(!vocabularyDisplay.phonetics.isNullOrEmpty()){
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        text = "A. Phonetic",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = colorResource(R.color.bg_btn)
                    )
                }
                ModifierUtils.SpaceHeigh(10.dp)
                //audio
                AudioCard(
                    audios = vocabularyDisplay.phonetics
                )
                ModifierUtils.SpaceHeigh(10.dp)
            }
            //meaning
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = "B. Meaning",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = colorResource(R.color.bg_btn)
                )
            }

            ModifierUtils.SpaceHeigh(10.dp)
            vocabularyDisplay.meanings?.let {
                it.forEach { mean ->
                    //partOfSpeech
                    if(!mean.partOfSpeech.isNullOrEmpty()){
                        ModifierUtils.SpaceHeigh(5.dp)
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Start
                        ) {
                            Text(
                                text = mean.partOfSpeech,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                color = colorResource(R.color.bg_btn)
                            )
                        }
                    }

                    ModifierUtils.SpaceHeigh(10.dp)

                    if(!mean.definitions.isNullOrEmpty()){
                        mean.definitions.forEachIndexed { index, definition ->
                            if (!definition.definition.isNullOrEmpty()) {
                                //definition
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Start
                                ) {
                                    Text(
                                        text = "${index + 1}. " + definition.definition,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp,
                                        color = colorResource(R.color.bg_btn_cancel),
                                    )
                                }

                                if (!definition.example.isNullOrEmpty()) {
                                    ModifierUtils.SpaceHeigh(10.dp)
                                    Text(
                                        text = "Example : ",
                                        modifier = Modifier.fillMaxWidth(),
                                        textAlign = TextAlign.Start,
                                        fontWeight = FontWeight.Normal,
                                        color = colorResource(R.color.bg_btn_cancel),
                                        fontSize = 16.sp,
                                    )
                                    ModifierUtils.SpaceHeigh(5.dp)
                                    Text(
                                        text = definition.example,
                                        modifier = Modifier.fillMaxWidth(),
                                        fontWeight = FontWeight.Normal,
                                        color = colorResource(R.color.bg_btn_cancel),
                                        textAlign = TextAlign.Start,
                                        fontSize = 16.sp,
                                    )
                                }
                            }
                        }
                    }
                }
            }

            if(!vocabularyDisplay.synonyms.isNullOrEmpty()){
                ModifierUtils.SpaceHeigh(10.dp)
                Text(
                    text = "C.Synonyms",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(R.color.bg_btn),
                    fontSize = 16.sp,
                )
                ModifierUtils.SpaceHeigh(5.dp)
                vocabularyDisplay.synonyms.forEach { word ->
                    Text(
                        text = word,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Start,
                        fontWeight = FontWeight.Normal,
                        color = Color.Green,
                        fontSize = 16.sp,
                    )
                    ModifierUtils.SpaceHeigh(5.dp)
                }
            }
            if(!vocabularyDisplay.antonyms.isNullOrEmpty()){
                ModifierUtils.SpaceHeigh(10.dp)
                Text(
                    text = "D.Antonyms",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(R.color.bg_btn),
                    fontSize = 16.sp,
                )
                ModifierUtils.SpaceHeigh(5.dp)
                vocabularyDisplay.antonyms.forEach { word ->
                    Text(
                        text = word,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Start,
                        fontWeight = FontWeight.Normal,
                        color = Color.Red,
                        fontSize = 16.sp,
                    )
                    ModifierUtils.SpaceHeigh(5.dp)
                }
            }
        }
    }
}

