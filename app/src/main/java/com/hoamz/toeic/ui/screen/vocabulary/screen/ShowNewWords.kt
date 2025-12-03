package com.hoamz.toeic.ui.screen.vocabulary.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.hoamz.toeic.R
import com.hoamz.toeic.baseviewmodel.MainViewModel
import com.hoamz.toeic.data.local.VocabularyEntity
import com.hoamz.toeic.ui.component.TopBar
import com.hoamz.toeic.ui.screen.navigation.HomeNavScreen
import com.hoamz.toeic.ui.screen.vocabulary.AppDictionaryViewModel
import com.hoamz.toeic.ui.screen.vocabulary.viewmodel.SelectWordsViewmodel
import com.hoamz.toeic.ui.screen.vocabulary.viewmodel.VocabularyViewModel
import com.hoamz.toeic.utils.CustomIcon
import com.hoamz.toeic.utils.ModifierUtils
import com.hoamz.toeic.utils.ModifierUtils.noRippleClickable
import com.hoamz.toeic.utils.PlayAudio

@Composable
fun ShowNewWords(
    modifier: Modifier = Modifier,
    mainViewModel: MainViewModel,
    navController: NavController,
    selectWordsViewmodel: SelectWordsViewmodel,
    appDictionaryViewModel: AppDictionaryViewModel,
    vocabularyViewModel: VocabularyViewModel
) {
    //words truyen qua man hinh nay se dc nhan tai day
//    val words: List<Word> by selectWordsViewmodel.words.collectAsState()
    //keyboard controller

    val vocabs : List<VocabularyEntity> by vocabularyViewModel.vocabs.collectAsState()

    val localKeyboard = LocalSoftwareKeyboardController.current

    //this is content search
    var content by remember {
        mutableStateOf("")
    }

    var filteredListWord by rememberSaveable {
        mutableStateOf(vocabs)
    }

    LaunchedEffect(content) {
        val tmp: MutableList<VocabularyEntity> = mutableListOf()
        vocabs.forEach { vocab ->
            if (vocab.word.contains(content)) {
                tmp += vocab
            }
        }
        filteredListWord = tmp
    }

    if (vocabs.isEmpty()) {
        //no data
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White.copy(0.8f))
                .navigationBarsPadding(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopBar(
                nameTab = "Vocabulary"
            ) {
                navController.popBackStack()
            }

            ModifierUtils.SpaceHeigh(10.dp)

            val composition by rememberLottieComposition(
                spec = LottieCompositionSpec.Asset("empty.json")
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.White.copy(0.8f)),
                contentAlignment = Alignment.Center
            ) {
                LottieAnimation(
                    composition = composition,
                    iterations = LottieConstants.IterateForever
                )
            }
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White.copy(0.8f))
                .navigationBarsPadding(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            var isSearch by remember {
                mutableStateOf(false)
            }
            //topBar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp)
                    .padding(start = 16.dp, end = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    IconButton(
                        onClick = {
                            localKeyboard?.hide()
                            navController.popBackStack()//back ve man hinh trc
                        }, modifier = Modifier.clip(CircleShape)
                    ) {
                        Icon(Icons.Default.ArrowBackIos, contentDescription = null)
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(text = "Vocabulary", fontWeight = FontWeight.Normal)
                }

                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    IconButton(
                        onClick = {
                            //search...
                            isSearch = !isSearch
                        }, modifier = Modifier.clip(CircleShape)
                    ) {
                        Icon(
                            Icons.Default.Search,
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    IconButton(
                        onClick = {
                            //select word to learn ...
                        }, modifier = Modifier.clip(CircleShape)
                    ) {
                        Icon(
                            painterResource(R.drawable.ic_select_words),
                            contentDescription = null,
                            tint = Color.Black,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }
            AnimatedVisibility(
                visible = isSearch, enter = expandVertically( // bung xuống từ trên
                    expandFrom = Alignment.Top
                ) + fadeIn(), exit = shrinkVertically( // thu lên trên
                    shrinkTowards = Alignment.Top
                ) + fadeOut(),
                modifier = Modifier.padding(bottom = 5.dp)
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 5.dp),
                    value = content,
                    onValueChange = {
                        content = it
                    },
                    singleLine = true,
                    placeholder = {
                        Text("Enter your search...")
                    },
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = colorResource(R.color.selected_color),
                        unfocusedBorderColor = colorResource(R.color.selected_color)
                    ),
                    trailingIcon = {
                        if (content.isEmpty()) {
                            null
                        } else {
                            IconButton(
                                onClick = {
                                    content = ""
                                }, modifier = Modifier.clip(CircleShape)
                            ) {
                                Icon(
                                    Icons.Default.Clear,
                                    contentDescription = null,
                                    tint = Color.Gray.copy(0.6f)
                                )
                            }
                        }
                    }
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
                    .navigationBarsPadding(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(vertical = 5.dp)
                ) {
                    items(filteredListWord.size) { index ->
                        AWord(vocabularyEntity = filteredListWord[index],
                            onClickMastered = { isMastered ->
                            if (isMastered) {
                                vocabularyViewModel.masteredVocab(filteredListWord[index].id)
                            } else {
                                vocabularyViewModel.unMasteredVocab(filteredListWord[index].id)
                            }
                        }, onClickedWord = {
                            //qua man hinh detail
                            appDictionaryViewModel.setUpDescriptionOfWords(filteredListWord[index].word)
                            appDictionaryViewModel.setUpIdOfVocabularyToSend(filteredListWord[index].id)
                            navController.navigate(HomeNavScreen.WordDetail.route)
                        })
                    }
                }
            }
        }
    }
}

@Composable
fun AWord(
    modifier: Modifier = Modifier,
    vocabularyEntity: VocabularyEntity,
    onClickMastered: (isMastered: Boolean) -> Unit,
    onClickedWord: () -> Unit
) {
    val context = LocalContext.current
    var mastered by rememberSaveable {
        mutableStateOf(vocabularyEntity.isMastered)
    }

    Card(modifier = Modifier
        .fillMaxWidth()
        .noRippleClickable {
            onClickedWord()
        }
        .padding(5.dp),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(3.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (!mastered) colorResource(R.color.colorBgWord)
            else colorResource(R.color.masteredWord)
        )
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 15.dp, horizontal = 5.dp)
        ) {
            val (word, star,audio1,audio2,text1,text2,partOfSpeech,definition) = createRefs()
            Text(
                text = vocabularyEntity.word,
                modifier = Modifier.constrainAs(word) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start, margin = 10.dp)
                },
                fontWeight = FontWeight.Normal,
                textDecoration = if(mastered) TextDecoration.LineThrough else TextDecoration.None,
                color = Color.Black
            )

            CustomIcon(
                modifier = Modifier.constrainAs(star) {
                    top.linkTo(parent.top)
                    start.linkTo(word.end, margin = 8.dp)
                },
                colorTint = if (!mastered) Color.Black.copy(0.6f) else colorResource(R.color.masteredWord),
                colorTintClicked = if (mastered) colorResource(R.color.masteredWord) else Color.Black.copy(
                    0.6f
                ),
                colorBg = Color.White,
                imageVector = if (!mastered) Icons.Outlined.StarOutline else Icons.Filled.Star,
                imageVectorClicked = if (mastered) Icons.Filled.Star else Icons.Outlined.StarOutline
            ) {
                mastered = !mastered
                onClickMastered(mastered)
            }

            //cai nay neu co data thi moi hien
            Card(
                modifier = Modifier
                    .wrapContentSize()
                    .constrainAs(partOfSpeech) {
                        top.linkTo(parent.top)
                        end.linkTo(parent.end, margin = 10.dp)
                    },
                colors = CardDefaults.cardColors(
                    containerColor = colorResource(R.color.purple_300)
                ),
                shape = RoundedCornerShape(10.dp),
                elevation = CardDefaults.cardElevation(1.dp),
            ) {
                Text(
                    modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp),
                    text = vocabularyEntity.partOfSpeech,
                    fontWeight = FontWeight.Normal,
                    color = Color.White
                )
            }

            CustomIcon(
                modifier = Modifier.constrainAs(audio1) {
                    top.linkTo(word.bottom, margin = 5.dp)
                    start.linkTo(parent.start, margin = 10.dp)
                },
                colorTint = Color.White,
                colorTintClicked = Color.White,
                colorBg = Color.Green,
                imageVector = Icons.Default.VolumeUp,
                imageVectorClicked = Icons.Default.VolumeUp
            ) {
                if(vocabularyEntity.phonetics.isNotEmpty()){
                    PlayAudio.playAudio(vocabularyEntity.phonetics[0].audio.toString(),context)
                }
            }

            Text(
                modifier = Modifier.constrainAs(text1){
                    start.linkTo(audio1.end, margin = 6.dp)
                    top.linkTo(audio1.top)
                    bottom.linkTo(audio1.bottom)
                },
                text = if(vocabularyEntity.phonetics.isNotEmpty()){
                    vocabularyEntity.phonetics[0].text ?: "  "
                }else "  ",
                fontWeight = FontWeight.Normal,
                color = Color.Black
            )

            CustomIcon(
                modifier = Modifier.constrainAs(audio2) {
                    top.linkTo(word.bottom, margin = 5.dp)
                    start.linkTo(text1.end, margin = 10.dp)
                },
                colorTint = Color.White,
                colorTintClicked = Color.White,
                colorBg = colorResource(R.color.purple_300),
                imageVector = Icons.Default.VolumeUp,
                imageVectorClicked = Icons.Default.VolumeUp
            ) {
                //audio 2
                if(vocabularyEntity.phonetics.size > 1){
                    PlayAudio.playAudio(vocabularyEntity.phonetics[1].audio.toString(),context)
                }
            }

            Text(
                modifier = Modifier.constrainAs(text2){
                    start.linkTo(audio2.end, margin = 6.dp)
                    top.linkTo(audio2.top)
                    bottom.linkTo(audio2.bottom)
                },
                text = if(vocabularyEntity.phonetics.size > 1){
                    vocabularyEntity.phonetics[1].text ?: "  "
                }else "  ",
                fontWeight = FontWeight.Normal,
                color = Color.Black
            )

            //definition
            Text(
                modifier = Modifier
                    .constrainAs(definition) {
                        top.linkTo(audio1.bottom, margin = 6.dp)
                        start.linkTo(parent.start, margin = 10.dp)
                        bottom.linkTo(parent.bottom)
                    }
                    .padding(5.dp),
                text = vocabularyEntity.definition,
                fontWeight = FontWeight.Normal,
                color = Color.Black
            )
        }
    }
}