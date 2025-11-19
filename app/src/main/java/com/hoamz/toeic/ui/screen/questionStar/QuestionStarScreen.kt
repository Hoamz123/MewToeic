package com.hoamz.toeic.ui.screen.questionStar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.hoamz.toeic.R
import com.hoamz.toeic.baseviewmodel.MainViewModel
import com.hoamz.toeic.data.local.QuestionStar
import com.hoamz.toeic.ui.screen.questionStar.component.AskDialog
import com.hoamz.toeic.utils.CustomIcon
import com.hoamz.toeic.utils.ModifierUtils
import com.hoamz.toeic.utils.ModifierUtils.noRippleClickable

@Composable
fun QuestionStarScreen(
    modifier: Modifier = Modifier,
    mainViewModel: MainViewModel,
    navController: NavController
) {

    val listQuestionStar : List<QuestionStar> by mainViewModel.questionStars.collectAsState()
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 5.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Star Question",
                fontWeight = FontWeight.Normal,
            )
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize()
                .padding(5.dp),
            contentPadding = PaddingValues(5.dp)
        ) {
            items(listQuestionStar.size) {index->
                ViewQuestionStar(
                    questionStar = listQuestionStar[index],
                    mainViewModel = mainViewModel,
                    onClick = {
                        // qua man hinh lam bai
                        mainViewModel.sendQuestionStar(questionStar = listQuestionStar[index])
                        navController.navigate("explain")
                    }
                )
            }

            item{
                Box(
                    modifier = Modifier.size(10.dp,100.dp)
                ) { }
            }

        }
    }
}

@Composable
fun ViewQuestionStar(
    modifier: Modifier = Modifier,
    questionStar: QuestionStar,
    mainViewModel: MainViewModel,
    onClick :() -> Unit,
) {
    var isShowDialog by remember {
        mutableStateOf(false)
    }

    Card(
        modifier = Modifier.fillMaxWidth()
            .padding(10.dp)
            .noRippleClickable{
                onClick()
            },
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = colorResource(R.color.selected_color)
        ),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
                .padding(10.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.weight(3f)
                        .padding(end = 10.dp),
                    text = questionStar.question,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black
                )

                Box(
                    modifier = Modifier.weight(0.4f),
                    contentAlignment = Alignment.Center
                ) {
                    CustomIcon(
                        colorTint = colorResource(R.color.masteredWord),
                        colorTintClicked = colorResource(R.color.masteredWord),
                        colorBg = Color.White,
                        imageVector = Icons.Filled.Star,
                        imageVectorClicked = Icons.Filled.Star
                    ){
                        //hien thi dialog -> yes -> xoa luon
                        isShowDialog = true
                    }
                }
            }

            ModifierUtils.SpaceHeigh(10.dp)

            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(10.dp)
            ) {
                Text(
                    text = questionStar.answer,
                    fontWeight = FontWeight.Normal,
                    color = Color.Black.copy(0.6f)
                )
            }
        }

        if(isShowDialog){
            AskDialog(questionStar = questionStar,
                mainViewModel = mainViewModel) {
                isShowDialog = false
            }
        }

    }
}



