package com.hoamz.toeic.utils

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer

object PlayAudio {
    fun playAudio(url : String,context: Context){
        if(url.isEmpty()) return
        val mediaPlayer = MediaPlayer()
        try{
            mediaPlayer.setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build()
            )
            mediaPlayer.setDataSource(url)
            mediaPlayer.setOnPreparedListener { it.start() }
            mediaPlayer.setOnCompletionListener { it.release() }
            mediaPlayer.prepareAsync()
        }catch (e : Exception){
            AppToast.showToast(context,"Something wrong")
            mediaPlayer.release()//giai phong
        }
    }
}
