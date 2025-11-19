package com.hoamz.toeic.utils

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.hoamz.toeic.R

@Composable
fun CustomIcon(
    modifier: Modifier = Modifier,
    colorBg: Color,
    colorTint : Color,
    colorTintClicked : Color,
    imageVector: ImageVector,
    imageVectorClicked: ImageVector,
    onClick : () -> Unit
) {

    var clicked by rememberSaveable {
        mutableStateOf(false)
    }

    Card(
        elevation = CardDefaults.cardElevation(1.2.dp),
        shape = RoundedCornerShape(8.dp),
        modifier = modifier.size(24.dp)
            .clip(RoundedCornerShape(8.dp))
            .clickable{
                onClick()
                clicked = !clicked
            },
        colors = CardDefaults.cardColors(
            containerColor = colorBg
        )
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = if(!clicked) imageVector else imageVectorClicked,
                contentDescription = null,
                modifier = Modifier.size(16.dp),
                tint = if(!clicked) colorTint else colorTintClicked
            )
        }
    }
}
