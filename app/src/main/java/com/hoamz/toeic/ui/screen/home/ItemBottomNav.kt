package com.hoamz.toeic.ui.screen.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


data class ItemBottomNav(
    val title: String,
    val route : String,
    val icon: ImageVector
)

@Composable
fun BottomMenuItem(
    modifier: Modifier = Modifier,
    selected : Boolean,
    icon : ImageVector,
    text : String,
    onItemClick :(() -> Unit)? = null
) {
    Column(
        modifier = modifier
            .clickable { onItemClick?.invoke() },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(5.dp))
        Icon(imageVector = icon,
            tint = Color.Black.copy(0.8f),
            modifier = Modifier.size(22.dp),
            contentDescription = null)
        Text(
            text = text,
            fontSize = 10.sp,
            color = Color.Black.copy(0.8f),
            fontWeight = FontWeight.SemiBold
        )
    }
}