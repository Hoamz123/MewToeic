package com.hoamz.toeic.ui.screen.vocabulary.screen.learnvocab

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.hoamz.toeic.R
import com.hoamz.toeic.data.local.VocabularyCard
import com.hoamz.toeic.ui.component.TopBar
import com.hoamz.toeic.ui.screen.navigation.HomeNavScreen
import com.hoamz.toeic.ui.screen.vocabulary.AppDictionaryViewModel
import com.hoamz.toeic.ui.screen.vocabulary.component.FlashCardDetail
import com.hoamz.toeic.ui.screen.vocabulary.component.LinearProgress
import com.hoamz.toeic.ui.screen.vocabulary.screen.Vocab
import com.hoamz.toeic.ui.screen.vocabulary.viewmodel.VocabularyViewModel
import com.hoamz.toeic.utils.ModifierUtils

@Composable
fun FlashCard(
    modifier: Modifier = Modifier,
    navController: NavController,
    flashCardViewModel: FlashCardViewModel,
    appDictionaryViewModel: AppDictionaryViewModel,
    vocabularyViewModel: VocabularyViewModel
    //du lieu rieng custom cho flashcard
) {

//    //fake data
//    val vocabList = listOf(
//        Vocab("Ephemeral", "Ngắn ngủi, thoáng qua"),
//        Vocab("Ubiquitous", "Có mặt khắp nơi"),
//        Vocab("Inevitable", "Không thể tránh khỏi"),
//        Vocab("Ambiguous", "Mơ hồ, khó hiểu"),
//        Vocab("Meticulous", "Tỉ mỉ, kỹ lưỡng"),
//        Vocab("Scrutinize", "Xem xét kỹ lưỡng"),
//        Vocab("Conspicuous", "Dễ thấy, nổi bật"),
//        Vocab("Impeccable", "Hoàn hảo, không tì vết"),
//        Vocab("Tenacious", "Kiên trì, bền bỉ"),
//        Vocab("Eloquent", "Hùng hồn, có tài hùng biện"),
//        Vocab("Pragmatic", "Thực dụng, thực tế"),
//        Vocab("Arduous", "Khó khăn, gian khổ"),
//        Vocab("Obsolete", "Lỗi thời, không còn dùng"),
//        Vocab("Candid", "Thẳng thắn, thật thà"),
//        Vocab("Notorious", "Khét tiếng (theo nghĩa xấu)"),
//        Vocab("Prolific", "Phong phú, dồi dào (tác giả, cây cối...)"),
//        Vocab("Ambivalent", "Vừa yêu vừa ghét, mâu thuẫn trong cảm xúc")
//    )

    //nhan du lieu tu man hinh khac gui den
    val flashCardData : List<VocabularyCard> by flashCardViewModel.vocabsCard.collectAsState()

    Column(
        Modifier
            .fillMaxSize()
            .background(color = Color.White.copy(0.8f))
            .verticalScroll(rememberScrollState()),
    ) {

        var index by rememberSaveable {
            mutableIntStateOf(0)
        }

        var isFinished by rememberSaveable {
            mutableStateOf(false)
        }

        TopBar(
            nameTab = "FlashCard"
        ) {
            navController.popBackStack()//back va thoat khoi chuong trinh on luyen tu vgun thep flashcard
        }

        //progress
        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 20.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            LinearProgress(
                count = index + 1, maxCount = flashCardData.size
            )
        }

        ModifierUtils.SpaceHeigh(5.dp)

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Words ${index + 1} of ${flashCardData.size}",
            color = colorResource(R.color.colorText),
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Center
        )

        ModifierUtils.SpaceHeigh(15.dp)

        //card
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(360.dp)
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            AnimatedContent(
                targetState = index,
                transitionSpec = {
                    if (targetState > initialState) {
                        ContentTransform(slideInHorizontally { it } + fadeIn(),
                            slideOutHorizontally { -it } + fadeOut())
                    } else {
                        //target < initiaState
                        ContentTransform(slideInHorizontally { -it } + fadeIn(),
                            slideOutHorizontally { it } + fadeOut())
                    }
                }
            ) { id ->
                FlashCardDetail(
                    front = flashCardData[id].word + " (${flashCardData[id].partOfSpeech})",
                    back = flashCardData[id].definition
                )
            }
        }

        ModifierUtils.SpaceHeigh(40.dp)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 5.dp),
                onClick = {
                    if (index > 0) {
                        index--
                        isFinished = false
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.selected_color)
                ),
                shape = RoundedCornerShape(10.dp),
                elevation = ButtonDefaults.elevatedButtonElevation(2.dp),
            ) {
                Text(
                    text = "Previous",
                    fontWeight = FontWeight.Normal,
                    color = colorResource(R.color.colorText)
                )
            }

            ModifierUtils.SpaceWidth(5.dp)
            Card(
                modifier = Modifier //card ko dung background o day
                    .size(50.dp)
                    .graphicsLayer{
                        rotationX = 180f
                    },
                shape = CircleShape, colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ), elevation = CardDefaults.cardElevation(3.dp)
            ) {
                IconButton(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape),
                    onClick = {
                        appDictionaryViewModel.setUpDescriptionOfWords(flashCardData[index].word)
                        navController.navigate(HomeNavScreen.WordDetail.route)
                    },
                    //Marked Words : Thay the cho learned words
                ) {
                    Icon(
                        modifier = Modifier.fillMaxSize(),
                        painter = painterResource(R.drawable.ic_arrow_drop),
                        contentDescription = null,
                        tint = colorResource(R.color.progressColor)
                    )
                }
            }

            ModifierUtils.SpaceWidth(5.dp)

            Button(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 5.dp),
                onClick = {
                    if (index < flashCardData.size - 1) {
                        index++
                        if(index == flashCardData.size - 1){
                            isFinished = true
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if(!isFinished) colorResource(R.color.selected_color)
                        else Color.Green
                ),
                shape = RoundedCornerShape(10.dp),
                elevation = ButtonDefaults.elevatedButtonElevation(2.dp),
            ) {
                Text(
                    text = if(!isFinished) "Next" else "Finish",
                    fontWeight = FontWeight.Normal,
                    color = if(!isFinished) colorResource(R.color.colorText)
                        else Color.White
                )
            }
        }

        ModifierUtils.SpaceHeigh(30.dp)

        Button(
            onClick = {
                vocabularyViewModel.masteredVocab(flashCardData[index].id)
                if(index < flashCardData.size - 1) index++
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(10.dp),
            elevation = ButtonDefaults.elevatedButtonElevation(2.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.progressColor)
            )
        ) {
            Text(
                text = "Mastered",
                fontWeight = FontWeight.Normal,
                color = colorResource(R.color.bg_btn_cancel)
            )
        }

        ModifierUtils.SpaceHeigh(20.dp)

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
        ) {}
    }
}