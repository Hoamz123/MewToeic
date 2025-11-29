package com.hoamz.toeic.ui.screen.vocabulary.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.hoamz.toeic.R
import com.hoamz.toeic.data.remote.Phonetic
import com.hoamz.toeic.utils.CustomIcon
import com.hoamz.toeic.utils.ModifierUtils
import com.hoamz.toeic.utils.PlayAudio

@Composable
fun AudioCard(
    modifier: Modifier = Modifier,
    //co the khong co cai audio nao (phu thuoc vao API) ;;;((((
    audios : List<Phonetic>?
) {

    if(!audios.isNullOrEmpty()){
        val context = LocalContext.current
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = colorResource(R.color.colorBgWord)
            ),
            shape = RoundedCornerShape(10.dp),
            elevation = CardDefaults.cardElevation(1.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
                    .padding(vertical = 14.dp, horizontal = 10.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                audios.forEachIndexed { index,audio  ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        CustomIcon(
                            colorTint = Color.White,
                            colorTintClicked = Color.White,
                            colorBg = if(index == 0) Color.Green else colorResource(R.color.purple_300),
                            imageVector = Icons.Default.VolumeUp,
                            imageVectorClicked = Icons.Default.VolumeUp
                        ) {
                            if(audio.audio != null){
                                PlayAudio.playAudio(audio.audio,context)
                            }
                        }

                        ModifierUtils.SpaceWidth(10.dp)

                        Text(
                            text = audio.text ?: " ",
                            fontWeight = FontWeight.Normal,
                            color = colorResource(R.color.black)
                        )
                    }

                    if(index == 0 && audios.size > 1){
                        ModifierUtils.SpaceHeigh(10.dp)
                    }
                }
            }
        }
    }
}