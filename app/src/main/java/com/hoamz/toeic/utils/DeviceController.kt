package com.hoamz.toeic.utils

import android.view.View
import android.view.Window
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat


@Composable
fun DeviceController(
    modifier: Modifier = Modifier,
    view : View,
    window : Window?
) {
    LaunchedEffect(Unit) {
        window?.let {
            val controller = WindowInsetsControllerCompat(it,view)
            val insets = ViewCompat.getRootWindowInsets(view)
            val  hasNavBar : Boolean = insets?.isVisible(WindowInsetsCompat.Type.navigationBars()) == true

            //neu trc do co dung navBar
            if(hasNavBar){
                controller.hide(WindowInsetsCompat.Type.navigationBars())
                controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        }
    }

    //khi huy view thi hien thi lai thanh dieu huong
    DisposableEffect(Unit) {
        onDispose {
            window?.let{
                val controller = WindowInsetsControllerCompat(it,view)
                controller.show(WindowInsetsCompat.Type.navigationBars())
            }
        }
    }
}