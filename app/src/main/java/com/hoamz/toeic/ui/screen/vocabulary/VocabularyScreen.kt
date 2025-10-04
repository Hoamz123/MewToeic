package com.hoamz.toeic.ui.screen.vocabulary

import android.content.Context
import android.widget.Toast
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCompositionContext
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout

@Composable
fun Vocabulary(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier.fillMaxSize()
            .statusBarsPadding()
    ) {

        val context = LocalContext.current

        val vocabList = listOf(
            Vocab("Ephemeral", "Ngắn ngủi, thoáng qua"),
            Vocab("Ubiquitous", "Có mặt khắp nơi"),
            Vocab("Inevitable", "Không thể tránh khỏi"),
            Vocab("Ambiguous", "Mơ hồ, khó hiểu"),
            Vocab("Meticulous", "Tỉ mỉ, kỹ lưỡng"),
            Vocab("Resilient", "Kiên cường, hồi phục nhanh"),
            Vocab("Scrutinize", "Xem xét kỹ lưỡng"),
            Vocab("Conspicuous", "Dễ thấy, nổi bật"),
            Vocab("Impeccable", "Hoàn hảo, không tì vết"),
            Vocab("Tenacious", "Kiên trì, bền bỉ"),
            Vocab("Eloquent", "Hùng hồn, có tài hùng biện"),
            Vocab("Pragmatic", "Thực dụng, thực tế"),
            Vocab("Vindicate", "Minh oan, chứng minh đúng"),
            Vocab("Arduous", "Khó khăn, gian khổ"),
            Vocab("Transient", "Thoáng qua, tạm thời"),
            Vocab("Obsolete", "Lỗi thời, không còn dùng"),
            Vocab("Candid", "Thẳng thắn, thật thà"),
            Vocab("Notorious", "Khét tiếng (theo nghĩa xấu)"),
            Vocab("Prolific", "Phong phú, dồi dào (tác giả, cây cối...)"),
            Vocab("Ambivalent", "Vừa yêu vừa ghét, mâu thuẫn trong cảm xúc")
        )


        ConstraintLayout (
            modifier = Modifier.fillMaxSize()
        ){
            val (flashCard,titleVocab,btnBack,btnNext) = createRefs()
            val guideVertical = createGuidelineFromTop(0.3f)
            Text(text = "Vocabulary",
                color = Color.Black,
                modifier = Modifier.constrainAs(titleVocab){
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
                fontWeight = FontWeight.SemiBold)

            var index by rememberSaveable {
                mutableIntStateOf(0)
            }

            FlashCard(
                front = vocabList[index].en,
                back = vocabList[index].vi,
                modifier = Modifier.constrainAs(flashCard){
                    top.linkTo(guideVertical)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            )

            Button(
                onClick = {
                    if(index > 0) index--
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White
                ),
                modifier = Modifier.constrainAs(btnBack){
                    start.linkTo(parent.start)
                    top.linkTo(flashCard.bottom, margin = 30.dp)
                    end.linkTo(btnNext.start)
                }
            ) {
                Text(
                    text = "Back",
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black
                )
            }

            Button(
                onClick = {
                    if(index < vocabList.size) index++
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Green
                ),
                modifier = Modifier.constrainAs(btnNext){
                    end.linkTo(parent.end)
                    top.linkTo(flashCard.bottom, margin = 30.dp)
                    start.linkTo(btnBack.end)
                }
            ) {
                Text(
                    text = "Next",
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black
                )
            }
        }
    }
}

data class Vocab(
    val en : String,
    val vi : String
)

fun onToast(
    content: String,
    context: Context
){
    Toast.makeText(context,content, Toast.LENGTH_SHORT).show()
}


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
        modifier = modifier.size(300.dp)
            .clickable{
                flipped = !flipped
            },
        contentAlignment = Alignment.Center
    ){
        //card
        Card(
            modifier = Modifier.fillMaxSize()
                .graphicsLayer{
                    rotationX = rotation
                    cameraDistance = 12f * density
                },
            colors = CardDefaults.cardColors(
                containerColor = Color.Magenta
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 1.dp
            ),
            shape = RoundedCornerShape(10.dp)
        ) {
            if(rotation <= 90f){
                Box(
                    modifier = Modifier.fillMaxSize()
                        .background(color = Color.Magenta),
                    contentAlignment = Alignment.Center
                ){
                    Text(
                        text = front,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                }
            }
            else{
                Box(
                    modifier = Modifier.fillMaxSize()
                        .background(color = Color.White),
                    contentAlignment = Alignment.Center
                ){
                    Text(
                        modifier = Modifier
                            .graphicsLayer(
                                rotationX = 180f
                            ),
                        text = back,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black
                    )
                }
            }
        }
    }
}
