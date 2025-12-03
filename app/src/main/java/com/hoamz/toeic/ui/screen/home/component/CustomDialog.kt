package com.hoamz.toeic.ui.screen.home.component

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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.constraintlayout.compose.ConstraintLayout
import com.hoamz.toeic.R
import com.hoamz.toeic.utils.ModifierUtils
import com.hoamz.toeic.utils.ModifierUtils.noRippleClickable

@Composable
fun CustomDialog(
    modifier: Modifier = Modifier,
    isShow : Boolean,
    title : String,
    message : String,
    yes : String,
    no : String,
    onDismiss: () -> Unit,
    onClickedYes: () -> Unit,
    onClickedNo: () -> Unit
) {

    if (isShow) {
        Dialog(
            onDismissRequest = {
                onDismiss()
            }, properties = DialogProperties(
                usePlatformDefaultWidth = false,
                decorFitsSystemWindows = false
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .noRippleClickable{
                        onDismiss()
                    }
                    .background(color = Color.White.copy(0.4f)),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ConstraintLayout(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(horizontal = 30.dp)
                ) {
                    val (contentCard, circleCustom) = createRefs()

                    Card(modifier = Modifier
                        .fillMaxWidth()
                        .constrainAs(contentCard) {
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            top.linkTo(parent.top, margin = 50.dp)
                            bottom.linkTo(parent.bottom)
                        }
                        .height(220.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        ),
                        shape = RoundedCornerShape(20.dp),
                        elevation = CardDefaults.cardElevation(3.dp)) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 10.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            ModifierUtils.SpaceHeigh(60.dp)

                            Text(
                                text = title,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,
                                fontSize = 18.sp,
                                color = colorResource(R.color.bg_btn_cancel)
                            )

                            ModifierUtils.SpaceHeigh(8.dp)

                            Text(
                                text = message,
                                fontWeight = FontWeight.Normal,
                                fontSize = 15.sp,
                                textAlign = TextAlign.Center,
                                color = colorResource(R.color.black)
                            )

                            ModifierUtils.SpaceHeigh(20.dp)

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 20.dp, end = 20.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Button(
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(horizontal = 5.dp),
                                    onClick = {
                                        onClickedNo()
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = colorResource(R.color.bg_btn_cancel)
                                    ),
                                    shape = RoundedCornerShape(10.dp)
                                ) {
                                    Text(
                                        text = no,
                                        fontWeight = FontWeight.Light,
                                        color = colorResource(R.color.color_text_btn_dialog)
                                    )
                                }
                                ModifierUtils.SpaceWidth(5.dp)

                                Button(
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(horizontal = 5.dp),
                                    onClick = {
                                        onClickedYes()
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = colorResource(R.color.bg_btn_yes)
                                    ),
                                    shape = RoundedCornerShape(10.dp)
                                ) {
                                    Text(
                                        text = yes,
                                        fontWeight = FontWeight.Light,
                                        color = colorResource(R.color.color_text_btn_dialog)
                                    )
                                }
                            }
                        }
                    }

                    Box(modifier = Modifier
                        .size(80.dp)
                        .constrainAs(circleCustom) {
                            top.linkTo(contentCard.top)
                            bottom.linkTo(contentCard.top)
                            start.linkTo(contentCard.start)
                            end.linkTo(contentCard.end)
                        }
                        .shadow(5.dp, CircleShape)
                        .clip(CircleShape)
                        .background(color = Color.White)
                        .padding(10.dp),
                        contentAlignment = Alignment.Center) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(CircleShape)
                                .background(color = Color.LightGray.copy(0.2f))
                                .padding(14.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painterResource(R.drawable.v_ellipse_178),
                                tint = Color.Unspecified,
                                modifier = Modifier.fillMaxSize(),
                                contentDescription = null
                            )

                            Text(
                                modifier = Modifier.padding(3.dp),
                                text = "!",
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}