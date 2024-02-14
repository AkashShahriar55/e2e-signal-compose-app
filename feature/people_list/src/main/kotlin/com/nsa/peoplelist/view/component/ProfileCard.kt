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
package com.nsa.peoplelist.view.component

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import coil.transform.CircleCropTransformation
import com.nsa.domain.model.PeopleProfile
import com.nsa.ui.component.CardItem
import com.nsa.ui.component.ProfileContent
import com.nsa.ui.component.ProfilePicture
import com.nsa.ui.component.ProfilePictureSize
import com.nsa.ui.theme.AppTheme

/**
 * For People list
 *
 * Created on Feb 05, 2023.
 *
 */
@Composable
internal fun ProfileCard(modifier: Modifier = Modifier, item: PeopleProfile, onClick: () -> Unit) {
    CardItem(
        modifier = modifier
            .clickable { onClick.invoke() }
    ) {

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Image(
                painter = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(item.photo)
                        .size(Size.ORIGINAL) // Set the target size to load the image at.
                        .build()
                ),
                contentDescription = "Profile image",
                modifier = Modifier.fillMaxWidth().aspectRatio(1.26f),
                contentScale = ContentScale.Crop
            )
            ProfileContent(
                userName = item.name,
                subName = null,
                isOnline = item.status,
                alignment = Alignment.Start,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}



@Preview(showBackground = true)
@Composable
fun PreviewProfileCard() {
    AppTheme {
        ProfileCard(
           modifier = Modifier,
            item = PeopleProfile(
                1,
                "Akash Shahriar",
                true,
                Uri.parse("https://images.unsplash.com/photo-1542178243-bc20204b769f?ixid=MXwxMjA3fDB8MHxzZWFyY2h8MTB8fHBvcnRyYWl0fGVufDB8MnwwfA%3D%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60"),
                21,
                "New York,USA"

            ),
            onClick = {}
        )
    }

}
