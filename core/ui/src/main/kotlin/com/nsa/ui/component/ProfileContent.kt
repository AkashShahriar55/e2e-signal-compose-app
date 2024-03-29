/*
 * Copyright 2022 | Dmitri Chernysh | https://mobile-dev.pro
 *
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.nsa.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nsa.domain.model.PeopleProfile
import com.nsa.ui.R
import com.nsa.ui.theme.HeartIcon
import com.nsa.ui.theme.LocatonIcon
import com.nsa.ui.theme.WaveIcon
import com.nsa.ui.theme.favorite_button_container_color
import com.nsa.ui.theme.favorite_button_content_color
import com.nsa.ui.theme.favorite_button_selected_container_color
import com.nsa.ui.theme.favorite_button_selected_content_color
import com.nsa.ui.theme.wave_button_container_color
import com.nsa.ui.theme.wave_button_content_color

/**
 * For People list
 *
 * Created on Feb 05, 2023.
 *
 */
@Composable
fun ProfileContent(
    profile:PeopleProfile,
    alignment: Alignment.Horizontal,
    modifier: Modifier = Modifier,
    makeFavorite: () -> Unit,
    sayHi: () -> Unit
) {
    Column(
        modifier = Modifier
            .background(Color.White)
            .padding(14.dp)
            .fillMaxWidth()
            ,
        horizontalAlignment = alignment
    ) {


        Text(
            text = "${profile.name},${profile.age}",
            style = MaterialTheme.typography.titleMedium
        )

        Row(
            modifier = Modifier.padding(0.dp,8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            LocatonIcon()
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = profile.location,
                style = MaterialTheme.typography.bodySmall
            )
        }
        
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                contentPadding = PaddingValues(0.dp),
                modifier = Modifier
                    .height(25.dp)
                    .weight(1f),
                onClick = makeFavorite,
                colors = if(profile.isFavorite){
                    ButtonDefaults.buttonColors(
                        containerColor = favorite_button_selected_container_color,
                        contentColor = favorite_button_selected_content_color,
                        disabledContainerColor = favorite_button_selected_container_color,
                        disabledContentColor = favorite_button_selected_content_color
                    )
                }else{
                    ButtonDefaults.buttonColors(
                        containerColor = favorite_button_container_color,
                        contentColor = favorite_button_content_color,
                        disabledContainerColor = favorite_button_container_color,
                        disabledContentColor = favorite_button_content_color
                    )
                }

            ) {
                HeartIcon()
            }
            
            Spacer(modifier = Modifier.width(6.dp))

            Button(
                contentPadding = PaddingValues(0.dp),
                modifier = Modifier
                    .height(25.dp)
                    .weight(1f),
                onClick = sayHi,
                colors = ButtonDefaults.buttonColors(
                    containerColor = wave_button_container_color,
                    contentColor = wave_button_content_color,
                    disabledContainerColor = wave_button_container_color,
                    disabledContentColor = wave_button_content_color
                )
            ) {
                Text("\uD83D\uDC4B", fontSize = 10.sp)
            }
        }

       


    }

}