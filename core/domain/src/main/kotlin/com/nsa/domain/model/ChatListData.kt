package com.nsa.domain.model

data class ChatListData(
    val storiesData:List<StoryData>,
    val messageSnapshots:List<MessageSnapshot>
)


val fakeChatListData = ChatListData(
    fakeStoriesData,
    fakeMessageSnapshots
)