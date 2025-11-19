package com.hoamz.toeic.ui.screen.vocabulary.screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.statusBarsPadding
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
import androidx.compose.material.icons.outlined.VolumeUp
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
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import com.hoamz.toeic.R
import com.hoamz.toeic.base.BannerAdView
import com.hoamz.toeic.baseviewmodel.MainViewModel
import com.hoamz.toeic.data.local.Word
import com.hoamz.toeic.data.remote.VocabDisplay
import com.hoamz.toeic.data.remote.Vocabulary
import com.hoamz.toeic.ui.screen.vocabulary.AppDictionaryViewModel
import com.hoamz.toeic.ui.screen.vocabulary.viewmodel.SelectWordsViewmodel
import com.hoamz.toeic.utils.AppToast
import com.hoamz.toeic.utils.CustomIcon
import com.hoamz.toeic.utils.ModifierUtils
import com.hoamz.toeic.utils.PlayAudio
import java.lang.Appendable
import kotlin.collections.emptyList

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ShowNewWords(
    modifier: Modifier = Modifier,
    mainViewModel: MainViewModel,
    navController: NavController,
    selectWordsViewmodel: SelectWordsViewmodel,
    appDictionaryViewModel: AppDictionaryViewModel
) {
    //words truyen qua man hinh nay se dc nhan tai day
    val words : List<Word> by selectWordsViewmodel.words.collectAsState()
    //keyboard controller
    val localKeyboard = LocalSoftwareKeyboardController.current

    var content by remember {
        mutableStateOf("")
    }

    var filteredListWord by rememberSaveable {
        mutableStateOf(words)
    }

    LaunchedEffect(content) {
        val tmp : MutableList<Word> = mutableListOf()
        words.forEach { word ->
            if(word.word.contains(content)){
                tmp+=word
            }
        }
        filteredListWord = tmp
    }

    Column(
        modifier = Modifier.fillMaxSize()
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
                    Icon(Icons.Default.Search,
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
            visible = isSearch,
            enter = expandVertically( // bung xuống từ trên
                expandFrom = Alignment.Top
            ) + fadeIn(),
            exit = shrinkVertically( // thu lên trên
                shrinkTowards = Alignment.Top
            ) + fadeOut()
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
                    if(content.isEmpty()){
                        null
                    }
                    else{
                        IconButton(
                            onClick = {
                                content = ""
                            },
                            modifier = Modifier.clip(CircleShape)
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
                .padding(start = 10.dp,end = 10.dp, bottom = 10.dp, top = 5.dp)
                .navigationBarsPadding(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(vertical = 5.dp)
            ) {
                items (filteredListWord.size){index ->
                    if(index == 3){
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            BannerAdView()
                        }
                    }
                    AWord(
                        _word = filteredListWord[index]
                    ) {isMastered ->
                        if(isMastered){
                            selectWordsViewmodel.masteredWord(filteredListWord[index])
                        }
                        else{
                            selectWordsViewmodel.unMasteredWord(filteredListWord[index])
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AWord(
    modifier: Modifier = Modifier,
    _word: Word,
    onClickWord :(isMastered : Boolean) -> Unit
) {

    var mastered by rememberSaveable {
        mutableStateOf(_word.isMastered)
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(3.dp),
        colors = CardDefaults.cardColors(
            containerColor = if(!mastered) colorResource(R.color.colorBgWord) else colorResource(R.color.masteredWord)
        )
    ) {
        ConstraintLayout(
           modifier = Modifier
               .fillMaxWidth()
               .padding(vertical = 15.dp, horizontal = 5.dp)
        ) {
            val (word,star) = createRefs()
            Text(
                text = _word.word,
                modifier = Modifier.constrainAs(word){
                    top.linkTo(parent.top)
                    start.linkTo(parent.start, margin = 30.dp)
                    bottom.linkTo(parent.bottom)
                },
                fontWeight = FontWeight.Normal,
                color = Color.Black
            )

            CustomIcon(
                modifier = Modifier.constrainAs(star) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end, margin = 30.dp)
                    bottom.linkTo(parent.bottom)
                },
                colorTint = if(!mastered) Color.Black.copy(0.6f) else colorResource(R.color.masteredWord),
                colorTintClicked = if(mastered) colorResource(R.color.masteredWord) else Color.Black.copy(0.6f),
                colorBg = Color.White,
                imageVector = if(!mastered) Icons.Outlined.StarOutline else Icons.Filled.Star,
                imageVectorClicked = if(mastered) Icons.Filled.Star else Icons.Outlined.StarOutline
            ){
                mastered = !mastered
                onClickWord(mastered)
            }
        }
    }
}