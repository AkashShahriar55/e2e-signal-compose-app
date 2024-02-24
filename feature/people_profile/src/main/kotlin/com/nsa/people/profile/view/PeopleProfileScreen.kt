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
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.boundsInParent
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size

import com.nsa.domain.model.PeopleProfile
import com.nsa.domain.model.fakePeopleProfileList
import com.nsa.ui.component.Chips
import com.nsa.ui.component.GallaryImage

import com.nsa.ui.component.ProfilePicture
import com.nsa.ui.component.ProfilePictureSize
import com.nsa.ui.component.RoundedBackButton
import com.nsa.ui.component.ScreenBackground
import com.nsa.ui.theme.AppTheme
import com.nsa.ui.theme.shimmerColor
import com.valentinilk.shimmer.shimmer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


/**
 * Profile screen for selected person from People list
 *
 * Created on Feb 03, 2023.
 *
 */

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PeopleProfileScreen(
    stateFlow: StateFlow<PeopleProfileUIState>,
    onBackPressed: () -> Unit,
    onOpenChatWith: (profile: PeopleProfile) -> Unit
) {




    val uiState by stateFlow.collectAsStateWithLifecycle()



    when(uiState){
        PeopleProfileUIState.Empty -> TODO()
        is PeopleProfileUIState.Fail -> TODO()
        PeopleProfileUIState.Loading -> ProfileView(fakePeopleProfileList[0],onBackPressed,true)
        is PeopleProfileUIState.Success -> (uiState as PeopleProfileUIState.Success).profile?.let { ProfileView(it,onBackPressed) }
    }


}





@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ProfileView(
    profile:PeopleProfile?,
    onBackPressed: () -> Unit,
    isShimmer:Boolean = false
){

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

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .then(
                        if(isShimmer)
                            Modifier.shimmer()
                        else
                            Modifier
                    )
            ){
                with(LocalDensity.current){
                    Image(
                        painter = rememberAsyncImagePainter(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(if(isShimmer) Uri.EMPTY else profile?.photo)
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
                        modifier = Modifier
                            .then(
                                if(isShimmer)
                                    Modifier.shimmer()
                                else
                                    Modifier
                            ),
                        text = "${profile?.distance} away",
                        containerColor = Color(0xB3252527),
                        contentColor = if(isShimmer){
                            Color.Transparent
                        }else{
                            Color.White
                             },
                    )
                }

                ProfilePicture(
                    photoUri = if(isShimmer) Uri.EMPTY else profile?.photo,
                    onlineStatus = profile?.status ?: false,
                    size = ProfilePictureSize.LARGE,
                    modifier = Modifier
                        .padding(10.dp, 50.dp, 10.dp, 10.dp)
                        .then(
                            if(isShimmer)
                                Modifier.shimmer()
                            else
                                Modifier
                        )
                        .align(Alignment.CenterHorizontally)
                        .onGloballyPositioned {
                            val rect = it.boundsInParent()
                            backgroundBoxTopOffset.intValue =
                                rect.topCenter.y.toInt() + (rect.bottomCenter.y - rect.topCenter.y).toInt() / 2

                        }
                )

                Text(
                    modifier = Modifier
                        .then(
                            if(isShimmer)
                                Modifier
                                    .shimmer()
                                    .background(
                                        MaterialTheme.colorScheme.shimmerColor,
                                        RoundedCornerShape(5.dp)
                                    )
                            else
                                Modifier
                        ),
                    text = profile?.name.toString(),
                    style = MaterialTheme.typography.titleLarge,
                    color = if(isShimmer) Color.Transparent else Color.Unspecified
                )

                profile?.about?.takeIf { it.isNotBlank() }?.let {
                    Text(
                        modifier = Modifier
                            .padding(0.dp, 10.dp)
                            .align(Alignment.Start)
                            .then(
                                if(isShimmer)
                                    Modifier
                                        .shimmer()
                                        .background(
                                            MaterialTheme.colorScheme.shimmerColor,
                                            RoundedCornerShape(5.dp)
                                        )
                                else
                                    Modifier
                            ),
                        text = "About",
                        style = MaterialTheme.typography.titleMedium,
                        color = if(isShimmer) Color.Transparent else Color.Unspecified
                    )

                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .then(
                                if(isShimmer)
                                    Modifier
                                        .shimmer()
                                        .background(
                                            MaterialTheme.colorScheme.shimmerColor,
                                            RoundedCornerShape(5.dp)
                                        )
                                else
                                    Modifier
                            ),
                        text = it,
                        style = MaterialTheme.typography.bodyMedium,
                        color = if(isShimmer) Color.Transparent else Color.Unspecified
                    )
                }




                profile?.interest?.takeIf { it.isNotEmpty() }?.let {
                    Text(
                        modifier = Modifier
                            .align(Alignment.Start)
                            .padding(0.dp, 10.dp)
                            .then(
                                if(isShimmer)
                                    Modifier
                                        .shimmer()
                                        .background(
                                            MaterialTheme.colorScheme.shimmerColor,
                                            RoundedCornerShape(5.dp)
                                        )
                                else
                                    Modifier
                            ),
                        text = "Interest",
                        style = MaterialTheme.typography.titleMedium,
                        color = if(isShimmer) Color.Transparent else Color.Unspecified
                    )

                    FlowRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        it.forEach {
                            Chips(
                                text = it,
                                modifier = Modifier
                                    .then(
                                        if(isShimmer)
                                            Modifier
                                                .shimmer()
                                        else
                                            Modifier
                                    ),
                                containerColor = if(isShimmer) MaterialTheme.colorScheme.shimmerColor else Color.Unspecified,
                                contentColor = if(isShimmer) Color.Transparent else Color.Unspecified
                            )
                        }
                    }
                }


                profile?.gallary?.takeIf { it.isNotEmpty() }?.let{
                    Text(
                        modifier = Modifier
                            .align(Alignment.Start)
                            .padding(0.dp, 10.dp)
                            .then(
                                if(isShimmer)
                                    Modifier
                                        .shimmer()
                                        .background(
                                            MaterialTheme.colorScheme.shimmerColor,
                                            RoundedCornerShape(5.dp)
                                        )
                                else
                                    Modifier
                            ),
                        text = "Gallery",
                        style = MaterialTheme.typography.titleMedium,
                        color = if(isShimmer) Color.Transparent else Color.Unspecified
                    )

                    GallaryPreview(images = it,isShimmer)

                }




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
fun GallaryPreview(images:List<Uri>,isShimmer:Boolean = false){
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        images.getOrNull(0)?.apply {
            GallaryImage(
                modifier = Modifier
                    .weight(2f)
                    .then(
                        if(isShimmer)
                            Modifier
                                .shimmer()
                        else
                            Modifier
                    )
                ,
                image = if(isShimmer) Uri.EMPTY else this
            )
        }
        Column(
            modifier = Modifier.weight(1f),
        ) {

            images.getOrNull(1)?.apply {
                GallaryImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .then(
                            if(isShimmer)
                                Modifier
                                    .shimmer()
                            else
                                Modifier
                        )
                    ,
                    image = if(isShimmer) Uri.EMPTY else this
                )
            }
            images.getOrNull(2)?.apply {
                GallaryImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .then(
                            if(isShimmer)
                                Modifier
                                    .shimmer()
                            else
                                Modifier
                        ),
                    image = if(isShimmer) Uri.EMPTY else this
                )
            }
        }
    }


    if(!isShimmer){
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



}


@Preview()
@Composable
fun PeopleProfilePreview() {
    AppTheme(darkTheme = false) {
        fakePeopleProfileList.find { it.id == 2 }?.let {
            PeopleProfileScreen(
                MutableStateFlow(PeopleProfileUIState.Success(it)).asStateFlow(),
                onBackPressed = {}
            ) {}
        }
    }
}


@Preview()
@Composable
fun PeopleProfileShimmerPreview() {
    AppTheme(darkTheme = false) {
        fakePeopleProfileList.find { it.id == 2 }?.let {
            PeopleProfileScreen(
                MutableStateFlow(PeopleProfileUIState.Loading).asStateFlow(),
                onBackPressed = {}
            ) {}
        }
    }
}