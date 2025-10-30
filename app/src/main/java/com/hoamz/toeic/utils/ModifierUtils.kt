package com.hoamz.toeic.utils

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

object ModifierUtils {
    @Composable
    fun Modifier.noRippleClickable(onclick :() -> Unit) : Modifier {
        return this.clickable(
            indication = null,
            interactionSource = remember { MutableInteractionSource() }
        ) {onclick()}
    }

    @Composable
    fun SpaceHeigh(
        value : Dp
    ){
        Spacer(modifier = Modifier.height(value))
    }

    @Composable
    fun SpaceWidth(
        value : Dp
    ){
        Spacer(modifier = Modifier.width(value))
    }

}