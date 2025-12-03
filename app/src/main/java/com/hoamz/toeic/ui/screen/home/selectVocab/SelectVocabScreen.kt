package com.hoamz.toeic.ui.screen.home.selectVocab

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import com.hoamz.toeic.base.BaseSharePref
import com.hoamz.toeic.baseviewmodel.MainViewModel
import com.hoamz.toeic.data.local.Question
import com.hoamz.toeic.data.local.VocabularyEntity
import com.hoamz.toeic.ui.component.TopBar
import com.hoamz.toeic.ui.screen.navigation.HomeNavScreen
import com.hoamz.toeic.ui.screen.vocabulary.AppDictionaryViewModel
import com.hoamz.toeic.ui.screen.vocabulary.viewmodel.VocabularyViewModel
import com.hoamz.toeic.utils.AppToast
import com.hoamz.toeic.utils.Contains
import com.hoamz.toeic.utils.ModifierUtils
import com.hoamz.toeic.utils.ModifierUtils.noRippleClickable


@Composable
fun SelectVocabScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    mainViewModel: MainViewModel,
    appDictionaryViewModel: AppDictionaryViewModel,
    vocabularyViewModel: VocabularyViewModel
) {

    val context = LocalContext.current

    //lay ra test cau hoi ma user clicked tu ShowAnswer
    val listQuestion: List<Question> by mainViewModel.listQuestion.collectAsState()

    //ds cac tu user da luu trong room
    val storedWords by vocabularyViewModel.vocabsStored.collectAsState()

    val listNewWords = remember {
        mutableStateListOf<String>()
    }

    var indexQ by remember {
        mutableIntStateOf(BaseSharePref.getIndexQuestionSelecting())
    }

    var countWords by remember {
        mutableIntStateOf(0)
    }

    Column(
        modifier = Modifier.fillMaxSize()
            .background(color = Color.White.copy(0.8f))
            .navigationBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopBar(
            nameTab = "Save New Words"
        ) {
            BaseSharePref.saveIndexQuestionSelecting(0)//reset
            navController.popBackStack()
        }

        Text(
            text = "Question ${indexQ + 1}",
            modifier = Modifier.fillMaxWidth().padding(start = 20.dp),
            fontSize = 15.sp,
            textAlign = TextAlign.Start
        )

        ConstraintLayout(
            modifier = Modifier.fillMaxSize()
        ) {
            val (vSelectVocab,btnNextPre,btnSave,btnToVocab) = createRefs()
            val guide1 = createGuidelineFromTop(0.55f)
            SelectVocab(
                modifier = Modifier.constrainAs(vSelectVocab){
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                },
                content = setUpSentence(listQuestion[indexQ]),
                selectedWords = listNewWords,
                vocabStored = storedWords,
            ) {word ->
                listNewWords.add(word)
            }

            Row (
                modifier = Modifier.fillMaxWidth()
                    .padding(16.dp)
                    .constrainAs(btnNextPre){
                        top.linkTo(guide1)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ){
                Button(
                    modifier = Modifier.weight(1f),
                    onClick = {
                        if(indexQ > 0) indexQ--
                        BaseSharePref.saveIndexQuestionSelecting(indexQ)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White
                    ),
                    shape = RoundedCornerShape(12.dp),
                    elevation = ButtonDefaults.elevatedButtonElevation(3.dp)
                ) {
                    Text(
                        text = "Previous",
                        fontSize = 13.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Normal
                    )
                }

                Button(
                    modifier = Modifier.weight(1f),
                    onClick = {
                        if(countWords != listNewWords.size) {
                            AppToast.showToast(context,"Please save")
                        }
                        if(countWords == listNewWords.size){
                            //neu da luu roi -> reset listNewWords
                            listNewWords.clear()
                            if(indexQ < listQuestion.size - 1) {
                                indexQ++
                                BaseSharePref.saveIndexQuestionSelecting(indexQ)
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White
                    ),
                    shape = RoundedCornerShape(12.dp),
                    elevation = ButtonDefaults.elevatedButtonElevation(3.dp)
                ) {
                    Text(
                        text = "Next",
                        fontSize = 13.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Normal
                    )
                }
            }

            Button(
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .constrainAs(btnSave){
                        top.linkTo(btnNextPre.bottom, margin = 20.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                onClick = {
                    //gia lap save
                    if(listNewWords.size <= 100 && listNewWords.isNotEmpty()){
                        appDictionaryViewModel.insertNewVocabs(listNewWords)
                        AppToast.showToast(context,"Vocabulary saved!")
                    }
                    countWords = listNewWords.size
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Green
                ),
                shape = RoundedCornerShape(12.dp),
                elevation = ButtonDefaults.elevatedButtonElevation(3.dp)
            ) {
                Text(
                    text = "Save",
                    fontSize = 13.sp,
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold
                )
            }

            //Practice
            Button(
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .constrainAs(btnToVocab){
                        top.linkTo(btnSave.bottom, margin = 20.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                onClick = {
                    //di den man hinh practice
                    vocabularyViewModel.setUpVocabsToSend(vocabs = storedWords)
                    navController.navigate(HomeNavScreen.ShowNewWords.route)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Green
                ),
                shape = RoundedCornerShape(12.dp),
                elevation = ButtonDefaults.elevatedButtonElevation(3.dp)
            ) {
                Text(
                    text = "Practice",
                    fontSize = 13.sp,
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
fun SelectVocab(
    modifier: Modifier = Modifier,
    content : String,
    selectedWords : List<String>,
    vocabStored : List<VocabularyEntity>,
    onSelectedVocab :(String) -> Unit,
) {
    var words = content.split(" ")
    FlowRow(
        modifier = Modifier.fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp)
            .wrapContentHeight(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        //loc sach du lieu words
        words = words.map { Contains.cleanWord(it) }//tao ra ds moi chua ket qua sau khi bien doi

        words.forEachIndexed {index,word ->
            if(word.isEmpty()) return@forEachIndexed
            val selected = selectedWords.contains(word) || (vocabStored.any { it.word == word })
            Text(
                text = word,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier
                    .shadow(
                        elevation = 2.dp,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .background(color = if(selected) Color.Green else Color.White)
                    .border(width = if(selected) 0.dp else 2.dp, color = Color.LightGray.copy(0.6f), shape = RoundedCornerShape(12.dp))
                    .padding(8.dp)
                    .noRippleClickable{
                        if(!word.isEmpty() && !word.contains("_")){
                            if(!selected){
                                onSelectedVocab(word)
                            }
                            else{
                                (selectedWords as MutableList).remove(word)
                            }
                        }
                    },
                color = if(selected) Color.White else Color.Black
            )

            if(index == words.size - 1){
                ModifierUtils.SpaceHeigh(5.dp)
                Box(
                    modifier = Modifier.fillMaxWidth()
                        .padding(horizontal = 8.dp)
                        .height(2.dp)
                        .background(color = Color.LightGray.copy(0.6f))
                ){}
            }
        }
    }
}

fun setUpSentence(question: Question) : String{
    return question.question + " " + question.A + " " + question.B +
            " " + question.C + " " + question.D
}