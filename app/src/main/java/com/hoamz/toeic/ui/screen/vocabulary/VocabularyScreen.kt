package com.hoamz.toeic.ui.screen.vocabulary

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material.icons.filled.Replay
import androidx.compose.material.icons.outlined.Replay
import androidx.compose.material.icons.outlined.Store
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.NotificationsNone
import androidx.compose.material.icons.rounded.RotateLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCompositionContext
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.colorspace.WhitePoint
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintLayoutBaseScope
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hoamz.toeic.R
import com.hoamz.toeic.base.BannerAdView
import com.hoamz.toeic.data.remote.Vocabulary
import com.hoamz.toeic.utils.Contains
import com.hoamz.toeic.utils.ModifierUtils
import com.hoamz.toeic.utils.ModifierUtils.noRippleClickable

@Composable
fun Vocabulary(
    modifier: Modifier = Modifier,
    dictionaryViewModel: AppDictionaryViewModel = hiltViewModel()
) {

    //state reminder
    var stateReminder by remember {
        mutableStateOf(false)
    }

    //expand dropdown menu period
    var expandDropMenu by remember {
        mutableStateOf(false)
    }

    //period selected period
    var periodSelected by remember {
        mutableIntStateOf(1)
    }

    //expand dropdown menu number words
    var expandDropMenuNumberWords by remember {
        mutableStateOf(false)
    }

    //period selected number words
    var numberWordsSelected by remember {
        mutableIntStateOf(1)
    }
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxSize()
                    .statusBarsPadding()
            ){
                val (chart, titleVocab, rowDescription, btnReminderAndSave, tvReminder, rowSetupRemind, dropDownMenuPeriod, dropDownMenuNumberWords, rowBtnFilterWords) = createRefs()
                val guideH02 = createGuidelineFromEnd(0.2f)
                val guideV06 = createGuidelineFromTop(0.6f)

                Text(
                    text = "Vocabulary",
                    color = Color.Black,
                    modifier = Modifier.constrainAs(titleVocab) {
                        top.linkTo(parent.top, margin = 10.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                    fontWeight = FontWeight.SemiBold
                )

                Card(
                    modifier = Modifier
                        .padding(10.dp)
                        .constrainAs(chart) {
                            top.linkTo(titleVocab.bottom, margin = 30.dp)
                            start.linkTo(parent.start, margin = 6.dp)
                            end.linkTo(parent.end, margin = 6.dp)
                        },
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(3.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    )
                ) {
                    StatisticsChart.JetpackComposeElectricCarSales(
                        modifier = Modifier.padding(10.dp)
                    )
                }

                //description
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp)
                        .constrainAs(rowDescription) {
                            top.linkTo(chart.bottom, margin = 30.dp)
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

                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White
                        ),
                        elevation = ButtonDefaults.elevatedButtonElevation(3.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {

                        Icon(
                            painter = painterResource(R.drawable.ic_words),
                            contentDescription = null,
                            tint = Color.Unspecified
                        )

                        ModifierUtils.SpaceWidth(5.dp)

                        Text(
                            modifier = Modifier.padding(horizontal = 10.dp),
                            text = "My words : ${30}",
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

                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White
                        ),
                        elevation = ButtonDefaults.elevatedButtonElevation(3.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {

                        Icon(
                            Icons.Rounded.Check, contentDescription = null, tint = Color.Green
                        )

                        ModifierUtils.SpaceWidth(5.dp)

                        Text(
                            modifier = Modifier.padding(horizontal = 10.dp),
                            text = "Mastered : ${10}",
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
                    color = Color.LightGray,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Center
                )

                //reminder
                Button(
                    modifier = Modifier
                        .constrainAs(btnReminderAndSave) {
                            top.linkTo(tvReminder.bottom, margin = 20.dp)
                            start.linkTo(parent.start, margin = 6.dp)
                            end.linkTo(
                                if (!stateReminder) parent.end else rowSetupRemind.start,
                                margin = if (stateReminder) 2.dp else 6.dp
                            )
                            if (!stateReminder) width = Dimension.fillToConstraints
                        }
                        .padding(vertical = 5.dp, horizontal = 10.dp),
                    onClick = {
                        stateReminder = !stateReminder
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White
                    ),
                    elevation = ButtonDefaults.elevatedButtonElevation(3.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    if (!stateReminder) {
                        Icon(
                            Icons.Rounded.NotificationsNone,
                            contentDescription = null,
                            tint = Color.Green
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
                            top.linkTo(tvReminder.bottom, margin = 20.dp)
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
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {

                        Button(
                            modifier = Modifier.padding(vertical = 5.dp),
                            onClick = {
                                stateReminder = !stateReminder
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.White
                            ),
                            elevation = ButtonDefaults.elevatedButtonElevation(3.dp),
                            shape = RoundedCornerShape(12.dp)
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
                        ) {
                            Text(
                                text = if (numberWordsSelected != 1) "$numberWordsSelected" else "words",
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
                        ) {
                            Text(
                                text = if (periodSelected != 1) "$periodSelected mins" else "after",
                                color = Color.Black,
                                fontWeight = FontWeight.Normal
                            )

                            Icon(
                                Icons.Default.ArrowDropDown,
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
                        expanded = expandDropMenu, onDismissRequest = {
                            expandDropMenu = false
                        }, shape = RoundedCornerShape(10.dp)
                    ) {
                        Contains.listDropPeriod.forEach { it ->
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
                        Contains.listDropNumberWords.forEach { it ->
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

                /*
                filter words to learn
                 */
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp)
                        .constrainAs(rowBtnFilterWords) {
                            top.linkTo(btnReminderAndSave.bottom, margin = 30.dp)
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
                            text = "Reviewed",
                            color = Color.White,
                            fontWeight = FontWeight.Normal
                        )
                    }
                }
    }
}



//neu nhu API tr ve phonetic thi ok,
// con neu nhu ko co phonetic,this phai thay = text trong phonetics
fun getPhonetic(vocabulary: Vocabulary?) : String {
    var phonetic = ""
    if(vocabulary?.phonetic != "" && vocabulary?.phonetic != null) phonetic = vocabulary.phonetic
    else{
        vocabulary?.phonetics?.let { it->
            for(i in it){
                if(i.text != "" && i.text != null){
                    phonetic = i.text
                    break
                }
            }
        }
    }
    return phonetic
}


data class Vocab(
    val en : String,
    val vi : String
)

@Composable
fun FlashCard(
    modifier: Modifier = Modifier,
    front : String,
    back : String
) {
    var flipped by rememberSaveable{
        mutableStateOf(false)
    }

    val rotation by animateFloatAsState(
        targetValue = if(flipped) 180f else 0f,
        animationSpec = tween(
            durationMillis = 500,
            easing = LinearOutSlowInEasing
        )
    )

    Box(
        modifier = modifier
            .noRippleClickable{
                flipped = !flipped
            },
        contentAlignment = Alignment.Center
    ){
        //card
        Card(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    rotationY = rotation
                    cameraDistance = 12f * density
                },
            colors = CardDefaults.cardColors(
                containerColor = colorResource(R.color.bg_btn)
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 2.dp
            ),
            shape = RoundedCornerShape(10.dp)
        ) {
            if(rotation <= 90f){
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = colorResource(R.color.bg_btn)),
                    contentAlignment = Alignment.Center
                ){
                    Text(
                        text = front,
                        fontSize = 23.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                }
            }
            else{
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = colorResource(R.color.bg_btn)),
                    contentAlignment = Alignment.Center
                ){
                    Text(
                        modifier = Modifier
                            .graphicsLayer(
                                rotationY = 180f
                            ),
                        text = back,
                        fontSize = 23.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                }
            }
        }
    }
}
