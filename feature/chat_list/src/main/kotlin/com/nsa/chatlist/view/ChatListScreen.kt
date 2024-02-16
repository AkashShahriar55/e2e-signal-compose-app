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
package com.nsa.chatlist.view

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.R
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType.Companion.Uri
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.nsa.domain.model.ChatList
import com.nsa.domain.model.PeopleProfile
import com.nsa.domain.model.fakeChatList
import com.nsa.domain.model.fakePeopleProfileList
import com.nsa.ui.component.ProfilePicture
import com.nsa.ui.component.ProfilePictureSize
import com.nsa.ui.component.RoundedBackButton
import com.nsa.ui.component.ScreenBackground
import com.nsa.ui.component.SimpleTopBar
import com.nsa.ui.ext.dp
import com.nsa.ui.ext.statusBarHeight
import com.nsa.ui.theme.AppTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


@Composable
fun ChatListScreen(
    state: StateFlow<ChatListUIState>,
    onClick: () -> Unit
) {

    val uiState by state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    ScreenBackground(
        modifier = Modifier
            .fillMaxSize()
    ) {

        when (uiState) {
            is ChatListUIState.Loading -> Loading()
            is ChatListUIState.Empty -> NoChatFound()
            is ChatListUIState.Success ->
                ChatList(
                    chatList = (uiState as ChatListUIState.Success).profileList,
                    onClick = onClick
                )

            is ChatListUIState.Fail -> {
                NoChatFound()
                LaunchedEffect(Unit) {
                    Toast.makeText(
                        context,
                        (uiState as ChatListUIState.Fail).throwable.localizedMessage,
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

        }


    }
}


@Composable
fun ChatList(
    chatList: List<ChatList>,
    onClick: () -> Unit
) {

    ScreenBackground(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.primary)

        ) {
            Header()
            LazyRow(
                modifier = Modifier
                    .padding(vertical = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            )
            {
                itemsIndexed(
                    fakePeopleProfileList,
                    key = { index,item-> item.id }
                ) {index,item->
                    UserStory(person = item,modifier = Modifier.padding(start = if(index==0) 14.dp else 0.dp ))

                }
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(
                        RoundedCornerShape(
                            topStart = 30.dp,
                            topEnd = 30.dp
                        )
                    )
                    .background(Color.White)
            ) {
                RoundedCorner(modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 10.dp)
                )
                LazyColumn(
                    modifier = Modifier
                        .padding(
                            top = 10.dp
                        )
                ) {
                    items(fakePeopleProfileList, key = { it.id }) {
                        UserEachRow(person = it){
                            onClick()
                        }
                    }
                }
            }
        }

    }

}


@Composable
fun RoundedCorner(
    modifier: Modifier
) {

    Box(
        modifier = modifier
            .width(90.dp)
            .height(5.dp)
            .clip(RoundedCornerShape(90.dp))
            .background(
                Color.Gray
            )
    )
}


@Composable
fun UserEachRow(
    person: PeopleProfile,
    onClick:()->Unit
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .clickable { onClick() }
            .padding(horizontal = 20.dp, vertical = 5.dp),
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    ProfilePicture(
                        photoUri = person.photo ?: android.net.Uri.EMPTY,
                        onlineStatus = person.status,
                        size = ProfilePictureSize.MEDIUM,
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Text(
                            text = person.name,
                            style = TextStyle(
                                color = Color.Black,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        Text(
                            text = "Okay sj djd askfaskd fas dflkjas dfa sdlf laksd flkas dlkf laks dflk askldflkas dfklaskdf k",
                            style = TextStyle(
                                color = Color.Gray,
                                fontSize = 14.sp,
                            ),
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                }
                Text(
                    modifier = Modifier.padding(start = 20.dp),
                    text = "12:23 PM",
                    style = TextStyle(
                        color = Color.Gray,
                        fontSize = 12.sp,
                    )
                )
            }
            Spacer(modifier = Modifier.height(15.dp))
            Divider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 1.dp,
                color = Color(0xffEFF0F1)
            )
        }
    }

}

@Composable
fun UserStory(
    person: PeopleProfile,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .width(IntrinsicSize.Min),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        ProfilePicture(
            photoUri = person.photo ?: android.net.Uri.EMPTY,
            onlineStatus = person.status,
            size = ProfilePictureSize.MEDIUM,
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = person.name,
            style = TextStyle(
                color = Color.White,
                fontSize = 13.sp
            ),
            maxLines = 1,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            overflow = TextOverflow.Ellipsis
        )

    }
}


@Composable
fun Header() {

    Row {
        SimpleTopBar(
            modifier = Modifier.padding(24.dp),
            title = "Chat",
            true)

    }

}


@Composable
private fun NoChatFound() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "No chat found",
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.Center),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
private fun Loading() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Loading...", modifier = Modifier
                .padding(16.dp)
                .align(Alignment.Center),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
@Preview
fun ChatListScreenPreview() {
    AppTheme {
        ChatListScreen(
            state = MutableStateFlow(ChatListUIState.Success(fakeChatList)),
            onClick = {}
        )
    }
}