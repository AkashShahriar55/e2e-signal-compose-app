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
package com.nsa.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.guru.fontawesomecomposelib.FaIcon
import com.guru.fontawesomecomposelib.FaIcons
import com.nsa.ui.R



@Composable
fun FacebookIcon(modifier: Modifier = Modifier) {

    Image(
        modifier = modifier,
        painter = painterResource(id = R.drawable.facebook), // Replace "your_vector_asset" with the actual name of your vector asset resource
        contentDescription = "Facebook Icon" // Provide a description for accessibility
    )
}

@Composable
fun GoogleIcon(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier,
        painter = painterResource(id = R.drawable.google), // Replace "your_vector_asset" with the actual name of your vector asset resource
        contentDescription = "Google Icon" // Provide a description for accessibility
    )
}



@Composable
fun AppleIconLight(modifier: Modifier = Modifier) {
    Icon(
        modifier = modifier,
        painter = painterResource(id = R.drawable.apple), // Replace "your_vector_asset" with the actual name of your vector asset resource
        contentDescription = "Apple Icon" // Provide a description for accessibility
    )
}

@Composable
fun AppleIconDark(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier,
        painter = painterResource(id = R.drawable.apple_black), // Replace "your_vector_asset" with the actual name of your vector asset resource
        contentDescription = "Apple Icon" // Provide a description for accessibility
    )
}



@Composable
fun BackIcon(modifier: Modifier = Modifier) {
    Icon(
        modifier = modifier,
        painter = painterResource(id = R.drawable.back), // Replace "your_vector_asset" with the actual name of your vector asset resource
        contentDescription = "back Icon" // Provide a description for accessibility
    )
}





@Composable
fun LocatonIcon(modifier: Modifier = Modifier,color: Color = Color(0xff8A91A8)) {
    Icon(
        modifier = modifier,
        painter = painterResource(id = R.drawable.location_icon), // Replace "your_vector_asset" with the actual name of your vector asset resource
        contentDescription = "location Icon", // Provide a description for accessibility,
        tint = color
    )
}

@Composable
fun HeartIcon(modifier: Modifier = Modifier) {
    Icon(
        modifier = modifier,
        painter = painterResource(id = R.drawable.heart), // Replace "your_vector_asset" with the actual name of your vector asset resource
        contentDescription = "location Icon", // Provide a description for accessibility,
    )
}


@Composable
fun WaveIcon(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier
            .width(14.dp)
            .height(14.dp),
        painter = painterResource(id = R.drawable.wave), // Replace "your_vector_asset" with the actual name of your vector asset resource
        contentDescription = "Apple Icon" // Provide a description for accessibility
    )
}



@Composable
fun BellIcon(modifier: Modifier = Modifier,hasNotification:Boolean = false) {
    Image(
        modifier = modifier,
        painter = if(hasNotification){
            painterResource(id = R.drawable.bell_icon_noti)
        }else{
            painterResource(id = R.drawable.bell_icon)
        }
        , // Replace "your_vector_asset" with the actual name of your vector asset resource
        contentDescription = "Bell Icon", // Provide a description for accessibility,
    )
}



@Preview
@Composable
fun PreviewIcon() {


    AppTheme {
        BellIcon(hasNotification = true)
    }
}









