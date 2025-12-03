package com.hoamz.toeic.ui.screen.vocabulary.component

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.hoamz.toeic.R

@Composable
fun LinearProgress(
    modifier: Modifier = Modifier,
    count : Int,
    maxCount : Int
) {
    val target = count.toFloat() / maxCount

    val progress by animateFloatAsState(
        targetValue = target,
        animationSpec = tween(
            delayMillis = 100,
            easing = LinearOutSlowInEasing
        )
    )

    Box(
        //nen ben duoi
        Modifier.fillMaxWidth()
            .padding(horizontal = 16.dp)
            .height(16.dp)
            .clip(shape = RoundedCornerShape(20.dp))
            .background(color = Color.LightGray.copy(0.2f))
    ) {

        Box(
            //lop phu chay ben tren
            modifier = Modifier.fillMaxHeight()
                .shadow(elevation = 2.dp)
                .fillMaxWidth(progress)
                .background(color = colorResource(R.color.progressColor))
        ) {}

//        LinearProgressIndicator(
//            progress = {
//                progress
//            },
//            modifier = Modifier.fillMaxSize(),
//            color = colorResource(R.color.progressColor),
//            trackColor = Color.LightGray.copy(0.8f)
//        )
    }
}