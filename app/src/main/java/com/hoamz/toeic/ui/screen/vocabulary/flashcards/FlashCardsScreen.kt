package com.hoamz.toeic.ui.screen.vocabulary.flashcards

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.RotateLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import com.hoamz.toeic.R
import com.hoamz.toeic.base.BannerAdView
import com.hoamz.toeic.ui.screen.vocabulary.AppDictionaryViewModel
import com.hoamz.toeic.ui.screen.vocabulary.FlashCard
import com.hoamz.toeic.ui.screen.vocabulary.Vocab
import com.hoamz.toeic.ui.screen.vocabulary.getPhonetic
import com.hoamz.toeic.utils.ModifierUtils

@Composable
fun FlashCardsScreen(
    modifier: Modifier = Modifier,
    dictionaryViewModel: AppDictionaryViewModel,
    navController: NavController
) {
    Column(
    modifier = Modifier.fillMaxSize()
    .statusBarsPadding()
    ) {
        val vocabList = listOf(
            Vocab("Ephemeral", "Ngắn ngủi, thoáng qua"),
            Vocab("Ubiquitous", "Có mặt khắp nơi"),
            Vocab("Inevitable", "Không thể tránh khỏi"),
            Vocab("Ambiguous", "Mơ hồ, khó hiểu"),
            Vocab("Meticulous", "Tỉ mỉ, kỹ lưỡng"),
            Vocab("Scrutinize", "Xem xét kỹ lưỡng"),
            Vocab("Conspicuous", "Dễ thấy, nổi bật"),
            Vocab("Impeccable", "Hoàn hảo, không tì vết"),
            Vocab("Tenacious", "Kiên trì, bền bỉ"),
            Vocab("Eloquent", "Hùng hồn, có tài hùng biện"),
            Vocab("Pragmatic", "Thực dụng, thực tế"),
            Vocab("Arduous", "Khó khăn, gian khổ"),
            Vocab("Obsolete", "Lỗi thời, không còn dùng"),
            Vocab("Candid", "Thẳng thắn, thật thà"),
            Vocab("Notorious", "Khét tiếng (theo nghĩa xấu)"),
            Vocab("Prolific", "Phong phú, dồi dào (tác giả, cây cối...)"),
            Vocab("Ambivalent", "Vừa yêu vừa ghét, mâu thuẫn trong cảm xúc")
        )

        var index by rememberSaveable {
            mutableIntStateOf(0)
        }

        val respond = dictionaryViewModel.getDescriptionOfWord(vocabList[index].en).collectAsState()


        ConstraintLayout (
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ){
            val (flashCard,titleVocab,btnControl) = createRefs()
            val guideV02 = createGuidelineFromTop(0.2f)
            val guideV06 = createGuidelineFromTop(0.6f)
            Text(text = "FlashCard",
                color = Color.Black,
                modifier = Modifier.constrainAs(titleVocab){
                    top.linkTo(parent.top, margin = 10.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
                fontWeight = FontWeight.SemiBold)

            Box(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .constrainAs(flashCard) {
                        start.linkTo(parent.start, margin = 16.dp)
                        end.linkTo(parent.end, margin = 16.dp)
                        top.linkTo(guideV02)
                        bottom.linkTo(guideV06)
                        height = Dimension.fillToConstraints
                    },
                contentAlignment = Alignment.Center
            ) {
                AnimatedContent(
                    targetState = index,
                    transitionSpec = {
                        //neu nhu nhan next -> qua cau tiep theo
                        if(targetState > initialState){
                            ContentTransform(
                                slideInHorizontally { it } + fadeIn(),
                                slideOutHorizontally { -it } + fadeOut()
                            )
                        }
                        //back ve
                        else{
                            ContentTransform(
                                slideInHorizontally { - it } + fadeIn(),
                                slideOutHorizontally { it } + fadeOut()
                            )
                        }
                    }
                ) { id ->
                    FlashCard(
                        modifier = Modifier.fillMaxSize(),
                        front = vocabList[id].en,
                        back = getPhonetic(respond.value)
                    )
                }
            }

            Row (
                modifier = Modifier.constrainAs(btnControl){
                    top.linkTo(flashCard.bottom, margin = 20.dp)
                    start.linkTo(parent.start, margin = 16.dp)
                    end.linkTo(parent.end, margin = 16.dp)
                    width = Dimension.fillToConstraints
                },
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ){
                Button(
                    modifier = Modifier.weight(1f),
                    onClick = {
                        if(index > 0) index--
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Red
                    ),
                    elevation = ButtonDefaults.elevatedButtonElevation(1.5.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {

                    Icon(
                        Icons.Rounded.RotateLeft,
                        contentDescription = null,
                        tint = Color.White
                    )

                    Text(
                        modifier = Modifier.padding(horizontal = 10.dp),
                        text = "Check Back",
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                ModifierUtils.SpaceWidth(10.dp)

                Button(
                    modifier = Modifier.weight(1f),
                    onClick = {
                        if(index < vocabList.size - 1) index++
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(R.color.teal_200)
                    ),
                    elevation = ButtonDefaults.elevatedButtonElevation(1.5.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {

                    Icon(
                        Icons.Rounded.Check,
                        contentDescription = null,
                        tint = Color.White
                    )

                    Text(
                        modifier = Modifier.padding(horizontal = 10.dp),
                        text = "Mastered",
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }

        Box (
            modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding(),
            contentAlignment = Alignment.BottomCenter
        ){
            //quang cao o day
            BannerAdView()
        }
    }
}