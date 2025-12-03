package com.hoamz.toeic.ui.screen.vocabulary.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.NotificationsNone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import com.hoamz.toeic.R
import com.hoamz.toeic.base.BaseSharePref
import com.hoamz.toeic.data.remote.Vocabulary
import com.hoamz.toeic.ui.screen.navigation.HomeNavScreen
import com.hoamz.toeic.ui.screen.vocabulary.AppDictionaryViewModel
import com.hoamz.toeic.ui.screen.vocabulary.component.StatisticsChart
import com.hoamz.toeic.ui.screen.vocabulary.viewmodel.SelectWordsViewmodel
import com.hoamz.toeic.ui.screen.vocabulary.viewmodel.VocabularyViewModel
import com.hoamz.toeic.utils.AppToast
import com.hoamz.toeic.utils.Contains
import com.hoamz.toeic.utils.ModifierUtils
import com.hoamz.toeic.utils.ModifierUtils.noRippleClickable

@Composable
fun Vocabulary(
    modifier: Modifier = Modifier,
    selectWordsViewmodel: SelectWordsViewmodel,
    appDictionaryViewModel: AppDictionaryViewModel,
    vocabularyViewModel: VocabularyViewModel,
    navController: NavController
) {
    //lay ra danh sach thong ke so tu da luu trong 3 thang gan nhat
    val dataChartIn3Month by vocabularyViewModel.dataForChart.collectAsState()

    //current context
    val context = LocalContext.current

    val xChart = remember { mutableStateListOf(0) }
    val yChart = remember { mutableStateListOf<Number>(1) }

    LaunchedEffect(dataChartIn3Month) {
        dataChartIn3Month.forEachIndexed { index, it ->
            yChart.add(it.cnt)
            xChart.add(index + 1)
        }
    }

    //lay ra danh sach tu da luu
//    val storedWords by selectWordsViewmodel.wordsStored.collectAsState() //-> so luong tu (ok)
    val storedWords by vocabularyViewModel.vocabsStored.collectAsState()

    LaunchedEffect(storedWords) {
        //selectWordsViewmodel.setUpWordsToSend(storedWords)
        vocabularyViewModel.setUpVocabsToSend(storedWords)
    }

    //shuffle() -> take(10) phan tu

    //lay ra danh sach tu da hoc master roi
    val masteredWords by vocabularyViewModel.masteredVocabs.collectAsState()
//    //lay ra ds tu trc day user da nhin thay trong My Words roi
    val reviewedWords by vocabularyViewModel.reviewedVocabs.collectAsState()
    //state reminder
    var stateReminder by remember {
        mutableStateOf(BaseSharePref.checkRemind())
    }

    //expand dropdown menu period
    var expandDropMenu by remember {
        mutableStateOf(false)
    }

    //period selected period
    var periodSelected : Int by remember {
        mutableIntStateOf(BaseSharePref.getPeriod())
    }

    //expand dropdown menu number words
    var expandDropMenuNumberWords by remember {
        mutableStateOf(false)
    }

    //period selected number words
    var numberWordsSelected : Int by remember {
        mutableIntStateOf(BaseSharePref.getNumberWordNeedRemind())
    }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White.copy(0.8f))
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        val (chart, titleVocab, rowDescription, btnReminderAndSave, tvReminder, rowSetupRemind,
            dropDownMenuPeriod, dropDownMenuNumberWords, rowBtnFilterWords,
            rowDesChart) = createRefs()

        val guide1 = createGuidelineFromTop(0.3f)

        Text(
            text = "Vocabulary", color = Color.Black, modifier = Modifier.constrainAs(titleVocab) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )

        Card(
            modifier = Modifier
                .padding(10.dp)
                .constrainAs(chart) {
                    top.linkTo(titleVocab.bottom, margin = 15.dp)
                    bottom.linkTo(guide1)
                    start.linkTo(parent.start, margin = 6.dp)
                    end.linkTo(parent.end, margin = 6.dp)
                    height = Dimension.fillToConstraints
                },
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(3.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            //do du lieu thong ke nen do thi mien
            StatisticsChart.ChartProgressLearnVocab(
                x = xChart,
                y = yChart,
                modifier = Modifier.padding(10.dp),
            )
        }

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
                .constrainAs(rowDesChart) {
                    top.linkTo(chart.bottom, margin = 3.dp)
                    end.linkTo(parent.end, margin = 6.dp)
                    start.linkTo(parent.start, margin = 6.dp)
                    width = Dimension.fillToConstraints
                },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            Row (
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(1.dp)
            ){}

            Text(
                modifier = Modifier.padding(horizontal = 10.dp),
                text = "Daily vocabulary growth",
                fontWeight = FontWeight.Normal,
                color = Color.Black,
                fontSize = 14.sp
            )
        }

        //description
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
                .constrainAs(rowDescription) {
                    top.linkTo(rowDesChart.bottom, margin = 20.dp)
                    start.linkTo(parent.start, margin = 6.dp)
                    end.linkTo(parent.end, margin = 6.dp)
                },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Button(
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 5.dp),
                onClick = {
                    //selectWordsViewmodel.setUpWordsToSend(storedWords)
                    navController.navigate(HomeNavScreen.ShowNewWords.route)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White
                ),
                elevation = ButtonDefaults.elevatedButtonElevation(3.dp),
                shape = RoundedCornerShape(12.dp),
                contentPadding = PaddingValues(horizontal = 5.dp)
            ) {

                Icon(
                    painter = painterResource(R.drawable.ic_words),
                    contentDescription = null,
                    tint = Color.Unspecified
                )

                Text(
                    modifier = Modifier.padding(horizontal = 10.dp),
                    text = "My words : ${storedWords.size}",
                    color = Color.Black,
                    fontWeight = FontWeight.Normal
                )
            }

            ModifierUtils.SpaceWidth(8.dp)

            Button(
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 5.dp),
                onClick = {
                    vocabularyViewModel.setUpVocabsToSend(masteredWords)
                    navController.navigate(HomeNavScreen.ShowNewWords.route)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White
                ),
                elevation = ButtonDefaults.elevatedButtonElevation(3.dp),
                shape = RoundedCornerShape(12.dp),
                contentPadding = PaddingValues(horizontal = 5.dp)
            ) {

                Icon(
                    Icons.Rounded.Check,
                    contentDescription = null, tint = Color.Green
                )

                Text(
                    modifier = Modifier.padding(horizontal = 10.dp),
                    text = "Mastered : ${masteredWords.size}",
                    color = Color.Black,
                    fontWeight = FontWeight.Normal
                )
            }
        }

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(tvReminder) {
                    top.linkTo(rowDescription.bottom, margin = 20.dp)
                    start.linkTo(parent.start, margin = 6.dp)
                    end.linkTo(parent.end, margin = 6.dp)
                },
            text = "Set up reminders to review your words",
            color = Color.Black.copy(0.5f),
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Center,
            fontSize = 13.sp
        )

        //reminder
        Button(
            modifier = Modifier
                .constrainAs(btnReminderAndSave) {
                    top.linkTo(tvReminder.bottom, margin = 10.dp)
                    start.linkTo(parent.start, margin = 6.dp)
                    end.linkTo(
                        if (!stateReminder) parent.end else rowSetupRemind.start,
                        margin = if (stateReminder) 2.dp else 6.dp
                    )
                    if (!stateReminder) width = Dimension.fillToConstraints
                }
                .padding(vertical = 5.dp, horizontal = 10.dp),
            onClick = {
                //neu dang la trang thai set up cho remind -> Save
                if(stateReminder) {
                    if(periodSelected != 0 && numberWordsSelected != 0){
                        BaseSharePref.savePeriod(periodSelected)
                        BaseSharePref.saveNumberWordNeedRemind(numberWordsSelected)

                        val toastMessage =
                            "$numberWordsSelected words set. 1 word will notify in $periodSelected min"
                        AppToast.showToast(context,toastMessage)

                    }
                    else{
                        AppToast.showToast(context, Contains.MISS_INFO_REMIND)
                    }
                }
                else{
                    //tu remind -> save
                    stateReminder = true
                    BaseSharePref.saveRemind()
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White
            ),
            elevation = ButtonDefaults.elevatedButtonElevation(3.dp),
            shape = RoundedCornerShape(12.dp),
            contentPadding = if(stateReminder) PaddingValues(horizontal = 10.dp) else {PaddingValues()}
        ) {
            if (!stateReminder) {
                Icon(
                    Icons.Rounded.NotificationsNone, contentDescription = null, tint = Color.Green
                )
            }

            ModifierUtils.SpaceWidth(5.dp)

            Text(
                modifier = Modifier.padding(horizontal = 10.dp),
                text = if (stateReminder) "Save" else "Remind Me",
                color = Color.Black,
                fontWeight = FontWeight.Normal
            )
        }

        ModifierUtils.SpaceWidth(5.dp)

        AnimatedVisibility(
            modifier = Modifier.constrainAs(rowSetupRemind) {
                end.linkTo(parent.end, margin = 10.dp)
                top.linkTo(tvReminder.bottom, margin = 10.dp)
                start.linkTo(btnReminderAndSave.end, margin = 2.dp)
                width = Dimension.fillToConstraints
            }, visible = stateReminder, enter = expandHorizontally( // bung ra tu end
                expandFrom = Alignment.End
            ) + fadeIn(), exit = shrinkHorizontally( // thu vao ben end
                tween(2000), shrinkTowards = Alignment.End
            ) + fadeOut()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {

                Button(
                    modifier = Modifier.padding(vertical = 5.dp),
                    onClick = {
                        stateReminder = !stateReminder
                        BaseSharePref.cancelRemind()
                        periodSelected = 0
                        numberWordsSelected = 0
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White
                    ),
                    elevation = ButtonDefaults.elevatedButtonElevation(3.dp),
                    shape = RoundedCornerShape(12.dp),
                    contentPadding = PaddingValues(horizontal = 5.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_wrong_answer),
                        contentDescription = null,
                        tint = Color.Unspecified
                    )
                }

                Button(
                    modifier = Modifier.padding(vertical = 5.dp),
                    onClick = {
                        expandDropMenuNumberWords = !expandDropMenuNumberWords
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White
                    ),
                    elevation = ButtonDefaults.elevatedButtonElevation(3.dp),
                    shape = RoundedCornerShape(12.dp),
                    contentPadding = PaddingValues(horizontal = 6.dp)
                ) {
                    Text(
                        modifier = Modifier.padding(start = 5.dp),
                        text = if (numberWordsSelected != 0) "$numberWordsSelected" else "words",
                        color = Color.Black,
                        fontWeight = FontWeight.Normal
                    )

                    Icon(
                        Icons.Default.ArrowDropDown,
                        contentDescription = null,
                        tint = Color.LightGray
                    )
                }

                Button(
                    modifier = Modifier.padding(vertical = 5.dp),
                    onClick = {
                        expandDropMenu = !expandDropMenu
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White
                    ),
                    elevation = ButtonDefaults.elevatedButtonElevation(3.dp),
                    shape = RoundedCornerShape(12.dp),
                    contentPadding = PaddingValues(horizontal = 6.dp)
                ) {
                    Text(
                        modifier = Modifier.padding(start = 5.dp),
                        text = if (periodSelected != 0) "$periodSelected mins" else "after",
                        color = Color.Black,
                        fontWeight = FontWeight.Normal
                    )

                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = null,
                        tint = Color.LightGray
                    )
                }
            }
        }

        //drop down menu
        Column(
            modifier = Modifier
                .constrainAs(dropDownMenuPeriod) {
                    end.linkTo(parent.end, margin = 16.dp)
                    top.linkTo(rowSetupRemind.bottom)
                }
                .wrapContentWidth()
                .shadow(elevation = 3.dp, shape = RoundedCornerShape(10.dp))
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            DropdownMenu(
                modifier = Modifier
                    .background(color = Color.White)
                    .width(60.dp)
                    .height(150.dp),
                expanded = expandDropMenu,
                onDismissRequest = {
                    expandDropMenu = false
                },
                shape = RoundedCornerShape(10.dp)
            ) {
                Contains.listDropPeriod.forEach {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 5.dp)
                            .noRippleClickable {
                                periodSelected = it
                                expandDropMenu = false
                            },
                        text = it.toString(),
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        //drop down menu number word
        Column(
            modifier = Modifier
                .constrainAs(dropDownMenuNumberWords) {
                    end.linkTo(rowSetupRemind.end)
                    start.linkTo(rowSetupRemind.start)
                    top.linkTo(rowSetupRemind.bottom)
                }
                .width(60.dp)
                .shadow(elevation = 3.dp, shape = RoundedCornerShape(10.dp))
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            DropdownMenu(
                modifier = Modifier
                    .background(color = Color.White)
                    .width(60.dp)
                    .height(150.dp),
                expanded = expandDropMenuNumberWords,
                onDismissRequest = {
                    expandDropMenuNumberWords = false
                },
                shape = RoundedCornerShape(10.dp)
            ) {
                Contains.listDropNumberWords.forEach {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 5.dp)
                            .noRippleClickable {
                                numberWordsSelected = it
                                expandDropMenuNumberWords = false
                            },
                        text = it.toString(),
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        //state shuffle
        var isShuffle by remember {
            mutableStateOf(false)
        }
        /*
        filter words to learn
         */
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
                .constrainAs(rowBtnFilterWords) {
                    top.linkTo(btnReminderAndSave.bottom, margin = 35.dp)
                    start.linkTo(parent.start, margin = 6.dp)
                    end.linkTo(parent.end, margin = 6.dp)
                },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Button(
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 5.dp),
                onClick = {
                    isShuffle = !isShuffle
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.progressColor)
                ),
                elevation = ButtonDefaults.elevatedButtonElevation(3.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_shuffle),
                    contentDescription = null,
                    tint = Color.Unspecified
                )

                ModifierUtils.SpaceWidth(5.dp)

                Text(
                    modifier = Modifier.padding(horizontal = 10.dp),
                    text = "Shuffle",
                    color = Color.White,
                    fontWeight = FontWeight.Normal
                )
            }

            ModifierUtils.SpaceWidth(10.dp)

            Button(
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 5.dp),
                onClick = {
                    //next to...
                    //khi user da tung ban=m vao tu de hoc thi danh dau reviewed
                    //khi do neu user nhan vao thi se hien thi nhung tu do
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Green
                ),
                elevation = ButtonDefaults.elevatedButtonElevation(3.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_brain),
                    contentDescription = null,
                    tint = Color.White
                )

                ModifierUtils.SpaceWidth(5.dp)

                Text(
                    modifier = Modifier.padding(horizontal = 10.dp),
                    text = "Learned Words",
                    color = Color.White,
                    fontWeight = FontWeight.Normal
                )
            }
        }

        val vPractice = createRef()
        AnimatedVisibility(
            visible = isShuffle,
            enter = expandVertically( // bung xuống từ trên
                expandFrom = Alignment.Top
            ) + fadeIn(),
            exit = shrinkVertically( // thu lên trên
                shrinkTowards = Alignment.Top
            ) + fadeOut(),
            modifier = Modifier.constrainAs(vPractice){
                top.linkTo(rowBtnFilterWords.bottom, margin = 10.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Button(
                    modifier = Modifier
                        .weight(1f)
                        .padding(vertical = 5.dp),
                    onClick = {
                        navController.navigate(HomeNavScreen.FlashCard.route)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(R.color.masteredWord)
                    ),
                    elevation = ButtonDefaults.elevatedButtonElevation(3.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    ModifierUtils.SpaceWidth(5.dp)

                    Text(
                        modifier = Modifier.padding(horizontal = 10.dp),
                        text = "FlashCard",
                        color = Color.Black,
                        fontWeight = FontWeight.Normal
                    )
                }

                ModifierUtils.SpaceWidth(10.dp)

                Button(
                    modifier = Modifier
                        .weight(1f)
                        .padding(vertical = 5.dp),
                    onClick = {
                        //next to...
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(R.color.masteredWord)
                    ),
                    elevation = ButtonDefaults.elevatedButtonElevation(3.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    ModifierUtils.SpaceWidth(5.dp)
                    Text(
                        modifier = Modifier.padding(horizontal = 10.dp),
                        text = "Quiz",
                        color = Color.Black,
                        fontWeight = FontWeight.Normal
                    )
                }
            }
        }
    }
}


//neu nhu API tr ve phonetic thi ok,
// con neu nhu ko co phonetic,this phai thay = text trong phonetics
fun getPhonetic(vocabulary: Vocabulary?): String {
    var phonetic = ""
    if (vocabulary?.phonetic != "" && vocabulary?.phonetic != null) phonetic = vocabulary.phonetic
    else {
        vocabulary?.phonetics?.let { it ->
            for (i in it) {
                if (i.text != "" && i.text != null) {
                    phonetic = i.text
                    break
                }
            }
        }
    }
    return phonetic
}


data class Vocab(
    val en: String, val vi: String
)

