package com.hoamz.toeic.ui.screen.questionStar.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.hoamz.toeic.baseviewmodel.MainViewModel
import com.hoamz.toeic.data.local.QuestionStar
import com.hoamz.toeic.utils.Contains


@Composable
fun AskDialog(
    modifier: Modifier = Modifier,
    questionStar: QuestionStar,
    mainViewModel: MainViewModel,
    onDismiss :() -> Unit
) {

    var isShow by remember {
        mutableStateOf(true)
    }
    if(isShow){
        Dialog(
            onDismissRequest = {
                isShow = false
                onDismiss()
            },
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            Card(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 26.dp)
                    .wrapContentHeight(),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.elevatedCardElevation(
                    defaultElevation = 5.dp
                ),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Icon(
                            Icons.Filled.QuestionMark,
                            contentDescription = null,
                            tint = Color.Red.copy(0.8f)
                        )
                        Text(
                            text = "Delete", fontWeight = FontWeight.Bold, fontSize = 18.sp
                        )
                    }

                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        text = "Are you sure delete this question?",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Normal
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(
                            modifier = Modifier.size(width = 80.dp, height = 35.dp),
                            contentPadding = PaddingValues(vertical = 8.dp),
                            onClick = {
                                mainViewModel.deleteQuestionStar(questionStar)
                                isShow = false
                            },
                            shape = RoundedCornerShape(5.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Blue.copy(0.4f)
                            )
                        ) {
                            Text(
                                text = "Delete",
                                color = Color.White,
                                fontWeight = FontWeight.Normal
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Button(
                            modifier = Modifier.size(width = 80.dp, height = 35.dp),
                            contentPadding = PaddingValues(vertical = 8.dp),
                            onClick = {
                                isShow = false
                                onDismiss()
                            },
                            shape = RoundedCornerShape(5.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.White
                            ),
                            border = BorderStroke(width = 0.8.dp, color = Color.Gray)
                        ) {
                            Text(
                                text = "Cancel",
                                color = Color.Black.copy(0.8f),
                                fontWeight = FontWeight.Normal
                            )
                        }
                    }
                }
            }
        }
    }

}