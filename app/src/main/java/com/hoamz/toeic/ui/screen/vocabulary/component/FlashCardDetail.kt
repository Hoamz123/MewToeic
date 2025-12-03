package com.hoamz.toeic.ui.screen.vocabulary.component

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hoamz.toeic.R
import com.hoamz.toeic.utils.ModifierUtils.noRippleClickable

@Composable
fun FlashCardDetail(
    modifier: Modifier = Modifier,
    front: String,
    back: String
) {
    var flipped by rememberSaveable {
        mutableStateOf(false)
    }

    val rotation by animateFloatAsState(
        targetValue = if (flipped) 180f else 0f,
        animationSpec = tween(
            durationMillis = 500,
            easing = LinearOutSlowInEasing
        )
    )

    Box(
        modifier = modifier.noRippleClickable {
            flipped = !flipped
        },
        contentAlignment = Alignment.Center
    ) {
        //card
        Card(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    rotationY = rotation
                    cameraDistance = 12f * density
                }, colors = CardDefaults.cardColors(
                containerColor = colorResource(R.color.selected_color)
            ), elevation = CardDefaults.cardElevation(
                defaultElevation = 3.dp
            ), shape = RoundedCornerShape(10.dp)
        ) {
            if (rotation <= 90f) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = colorResource(R.color.selected_color)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = front,
                        fontSize = 23.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = colorResource(R.color.colorText)
                    )
                }
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = colorResource(R.color.selected_color)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        modifier = Modifier.graphicsLayer(
                            rotationY = 180f
                        ),
                        text = back,
                        fontSize = 23.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = colorResource(R.color.colorText)
                    )
                }
            }
        }
    }
}