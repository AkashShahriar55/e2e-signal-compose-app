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
package com.nsa.people.profile.view

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowColumn
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.add
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.boundsInParent
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.fastForEachIndexed
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import coil.transform.CircleCropTransformation
import com.guru.fontawesomecomposelib.FaIcon
import com.guru.fontawesomecomposelib.FaIcons

import com.nsa.domain.model.PeopleProfile
import com.nsa.domain.model.fakePeopleProfileList
import com.nsa.people.profile.R
import com.nsa.ui.component.Chips
import com.nsa.ui.component.GallaryImage

import com.nsa.ui.component.ProfileContent
import com.nsa.ui.component.ProfilePicture
import com.nsa.ui.component.ProfilePictureSize
import com.nsa.ui.component.RoundedBackButton
import com.nsa.ui.component.ScreenBackground
import com.nsa.ui.theme.AppTheme
import kotlinx.coroutines.channels.ReceiveChannel
import kotlin.math.ceil


/**
 * Profile screen for selected person from People list
 *
 * Created on Feb 03, 2023.
 *
 */

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PeopleProfileScreen(
    profile: PeopleProfile,
    onBackPressed: () -> Unit,
    onOpenChatWith: (profile: PeopleProfile) -> Unit
) {


    val backgroundBoxTopOffset = remember { mutableIntStateOf(0) }


    Scaffold(
        floatingActionButton = { FloatingButton()},
        floatingActionButtonPosition = FabPosition.Center
    ) {
        ScreenBackground(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .verticalScroll(rememberScrollState())
        ) {

            Box(modifier = Modifier.fillMaxWidth()){
                with(LocalDensity.current){
                    Image(
                        painter = rememberAsyncImagePainter(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(profile.photo)
                                .size(Size.ORIGINAL) // Set the target size to load the image at.
                                .build()
                        ),
                        contentDescription = "Profile image",
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(bottomStart = 48.dp, bottomEnd = 48.dp))
                            .background(Color.Gray)
                            .height(backgroundBoxTopOffset.intValue.toDp() + 20.dp),
                        contentScale = ContentScale.Crop
                    )
                }

            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
                    .padding(bottom = 70.dp)
                ,
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RoundedBackButton{
                        onBackPressed()
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Chips(
                        text = "12.3 km away",
                        containerColor = Color(0xB3252527),
                        contentColor = Color.White
                    )
                }

                ProfilePicture(
                    photoUri = profile.photo ?: Uri.EMPTY,
                    onlineStatus = profile.status,
                    size = ProfilePictureSize.LARGE,
                    modifier = Modifier
                        .padding(10.dp, 50.dp, 10.dp, 10.dp)
                        .align(Alignment.CenterHorizontally)
                        .onGloballyPositioned {
                            val rect = it.boundsInParent()
                            backgroundBoxTopOffset.intValue =
                                rect.topCenter.y.toInt() + (rect.bottomCenter.y - rect.topCenter.y).toInt() / 2

                        }
                )

                Text(
                    text = profile.name,
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 10.dp),
                    text = "About",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = profile.about,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xff8A91A8)
                )

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 10.dp),
                    text = "Interest",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )

                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    profile.interest.forEach {
                        Chips(
                            text = it
                        )
                    }
                }

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 10.dp),
                    text = "Gallery",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )



                GallaryPreview(images = profile.gallary)



            }

        }



    }





}



@Composable
fun FloatingButton(){
    Row(
        modifier = Modifier
            .background(Color.DarkGray, RoundedCornerShape(50))
            .padding(5.dp),
        horizontalArrangement = Arrangement.spacedBy(30.dp)
    ) {
        Button(
            contentPadding = PaddingValues(),
            modifier = Modifier
                .width(66.dp)
                .aspectRatio(1f),
            onClick = { /*TODO*/ },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.onPrimary,
                contentColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Icon(
                imageVector = Icons.Filled.Clear,
                contentDescription = ""
            )
        }

        Button(
            contentPadding = PaddingValues(),
            modifier = Modifier
                .width(66.dp)
                .aspectRatio(1f),
            onClick = { /*TODO*/ },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )

        ) {
            Icon(
                imageVector = Icons.Filled.Favorite,
                contentDescription = ""
            )
        }

        Button(
            contentPadding = PaddingValues(),
            modifier = Modifier
                .width(66.dp)
                .aspectRatio(1f),
            onClick = { /*TODO*/ },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.onPrimary,
                contentColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Text("\uD83D\uDC4B", fontSize = 26.sp)
        }
    }
}



@OptIn(ExperimentalLayoutApi::class)
@Composable
fun GallaryPreview(images:List<Uri>){
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        images.getOrNull(0)?.apply {
            GallaryImage(
                modifier = Modifier.weight(2f),
                image = this
            )
        }
        Column(
            modifier = Modifier.weight(1f),
        ) {

            images.getOrNull(1)?.apply {
                GallaryImage(
                    modifier = Modifier.fillMaxWidth(),
                    image = this
                )
            }
            images.getOrNull(2)?.apply {
                GallaryImage(
                    modifier = Modifier.fillMaxWidth(),
                    image = this
                )
            }
        }
    }



    var size by remember { mutableStateOf(IntSize.Zero) }

    FlowRow(
        modifier = Modifier
            .fillMaxWidth()
            .onSizeChanged {
                size = it
            }
    ) {
        images.takeIf { it.size > 3 }?.drop(3)?.forEach { uri ->
            with(LocalDensity.current){
                GallaryImage(
                    modifier = Modifier.width((size.width/3f).toDp()),
                    image = uri
                )
            }
        }


    }
}


@Preview()
@Composable
fun PeopleProfilePreview() {
    AppTheme(darkTheme = false) {
        fakePeopleProfileList.find { it.id == 2 }?.let {
            PeopleProfileScreen(
                it,
                onBackPressed = {},
                onOpenChatWith = {}
            )
        }
    }
}