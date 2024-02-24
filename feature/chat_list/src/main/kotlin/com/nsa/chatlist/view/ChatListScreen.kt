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
import android.icu.text.SimpleDateFormat
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
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
import com.nsa.domain.model.AudioMessage
import com.nsa.domain.model.ChatList
import com.nsa.domain.model.ChatListData
import com.nsa.domain.model.MessageSnapshot
import com.nsa.domain.model.PeopleProfile
import com.nsa.domain.model.PhotoMessage
import com.nsa.domain.model.StoryData
import com.nsa.domain.model.TextMessage
import com.nsa.domain.model.fakeChatList
import com.nsa.domain.model.fakeChatListData
import com.nsa.domain.model.fakePeopleProfileList
import com.nsa.ui.component.ProfilePicture
import com.nsa.ui.component.ProfilePictureSize
import com.nsa.ui.component.RoundedBackButton
import com.nsa.ui.component.ScreenBackground
import com.nsa.ui.component.SimpleTopBar
import com.nsa.ui.ext.dp
import com.nsa.ui.ext.getFormatedText
import com.nsa.ui.ext.statusBarHeight
import com.nsa.ui.theme.AppTheme
import com.nsa.ui.theme.shimmerColor
import com.valentinilk.shimmer.shimmer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@Composable
fun ChatListScreen(
    state: StateFlow<ChatListUIState>,
    onClick: (conversationId:Int) -> Unit
) {

    val uiState by state.collectAsStateWithLifecycle()
    val context = LocalContext.current


    Scaffold(
        topBar = {
            SimpleTopBar(
                modifier = Modifier.padding(24.dp),
                title = "Chat",
                true,
                {

                },
                {

                }
                )
        },
        containerColor = MaterialTheme.colorScheme.tertiaryContainer,
        contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
        contentWindowInsets = WindowInsets.statusBars
    ) {
        ScreenBackground(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            color = MaterialTheme.colorScheme.tertiaryContainer
        ) {

            when (uiState) {
                is ChatListUIState.Loading -> Loading()
                is ChatListUIState.Empty -> NoChatFound()
                is ChatListUIState.Success ->
                    ChatList(
                        chatList = (uiState as ChatListUIState.Success).chatList,
                        onClick = onClick
                    )

                is ChatListUIState.Fail -> {
                    NoChatFound()
                    LaunchedEffect(Unit) {
                        Toast.makeText(
                            context,
                            (uiState as ChatListUIState.Fail).throwable?.localizedMessage,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

            }


        }
    }


}


@Composable
fun ChatList(
    chatList: ChatListData,
    onClick: (conversationId:Int) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()

    ) {
        LazyRow(
            modifier = Modifier,
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        )
        {
            itemsIndexed(
                chatList.storiesData,
                key = { index,item-> item.storyId }
            ) {index,item->
                UserStory(
                    person = item,
                    modifier = Modifier.padding(start = if(index==0) 14.dp else 0.dp )
                )

            }
        }

        Surface(
            shape = RoundedCornerShape(
                topStart = 30.dp,
                topEnd = 30.dp
            ),
            modifier = Modifier.padding(top = 20.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
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
                    items(chatList.messageSnapshots, key = { it.conversationId }) {
                        UserEachRow(person = it,onClick)
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
    person: MessageSnapshot,
    onClick:(conversationId:Int)->Unit
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .clickable { onClick(person.conversationId) }
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
                            style = MaterialTheme.typography.titleSmall,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.height(5.dp))

                        when(person.message.message){
                            is AudioMessage -> {
                                Text(
                                    text = "Sent an Audio \uD83C\uDFA4",
                                    style = MaterialTheme.typography.bodyMedium,
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                            is PhotoMessage -> {
                                val count = (person.message.message as PhotoMessage).links.size
                                val countText:String = if(count == 0 ) "a" else "$count"
                                Text(
                                    text = "Sent $countText photo \uD83D\uDCF7",
                                    style = MaterialTheme.typography.bodyMedium,
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                            is TextMessage -> {
                                val message = (person.message.message as TextMessage).message
                                Text(
                                    text = message,
                                    style = MaterialTheme.typography.bodyMedium,
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }


                    }

                }

                Text(
                    modifier = Modifier.padding(start = 20.dp),
                    text = person.message.time.getFormatedText("hh:mm a"),
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
    person: StoryData,
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
fun UserStoryShimmer(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .width(IntrinsicSize.Min)
            .shimmer(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        

        Box(modifier = Modifier
            .size(60.dp)
            .background(MaterialTheme.colorScheme.shimmerColor, RoundedCornerShape(50)))
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .background(MaterialTheme.colorScheme.shimmerColor, RoundedCornerShape(5.dp)),
            text = "Name",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Transparent
        )

    }
}


@Composable
fun ChatRowShimmer() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(horizontal = 20.dp, vertical = 5.dp)
            .shimmer(),
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
                    Box(modifier = Modifier
                        .size(60.dp)
                        .background(MaterialTheme.colorScheme.shimmerColor, RoundedCornerShape(50)))
                    Spacer(modifier = Modifier.width(10.dp))
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Text(
                            modifier = Modifier
                                .align(Alignment.Start)
                                .background(
                                    MaterialTheme.colorScheme.shimmerColor,
                                    RoundedCornerShape(5.dp)
                                ),
                            text = "Fake Name",
                            style = MaterialTheme.typography.titleSmall,
                            color = Color.Transparent
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        Text(
                            modifier = Modifier
                                .align(Alignment.Start)
                                .background(
                                    MaterialTheme.colorScheme.shimmerColor,
                                    RoundedCornerShape(5.dp)
                                ),
                            text = "Some fake message here",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Transparent
                        )
                    }

                }
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
    Column(
        modifier = Modifier
            .fillMaxSize()

    ) {
        LazyRow(
            modifier = Modifier
                .padding(vertical = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        )
        {
            items(5, key = { it }){
                UserStoryShimmer(modifier = Modifier.padding(start = if(it==0) 14.dp else 0.dp ))
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
                items(5, key = { it }) {
                    ChatRowShimmer()
                }
            }
        }
    }
}

@Composable
@Preview
fun ChatListScreenPreview() {
    AppTheme {
        ChatListScreen(
            state = MutableStateFlow(ChatListUIState.Success(fakeChatListData)),
            onClick = {}
        )
    }
}


@Composable
@Preview
fun ChatListScreenLoadingPreview() {
    AppTheme {
        ChatListScreen(
            state = MutableStateFlow(ChatListUIState.Loading),
            onClick = {}
        )
    }
}

@Composable
@Preview
fun UserStoryShimmerPreview() {
    UserStoryShimmer()
}

@Composable
@Preview
fun ChatRowShimmerPreview() {
    ChatRowShimmer()
}