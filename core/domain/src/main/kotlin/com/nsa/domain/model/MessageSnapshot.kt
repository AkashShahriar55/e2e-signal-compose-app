package com.nsa.domain.model

import android.net.Uri

data class MessageSnapshot(
    val conversationId:Int,
    val name:String,
    val photo: Uri?,
    val message:Message,
    val unseenMessages:Int,
    val status:Boolean
)



val fakeMessageSnapshots = listOf(
    MessageSnapshot(
        conversationId = 0,
        name = fakePeopleProfileList[0].name,
        photo = fakePeopleProfileList[0].photo,
        message = fakeMessages[0],
        unseenMessages = 2,
        status = fakePeopleProfileList[0].status
    ),
    MessageSnapshot(
        conversationId = 1,
        name = fakePeopleProfileList[1].name,
        photo = fakePeopleProfileList[1].photo,
        message = fakeMessages[1],
        unseenMessages = 0,
        status = fakePeopleProfileList[1].status
    ),
    MessageSnapshot(
        conversationId = 2,
        name = fakePeopleProfileList[2].name,
        photo = fakePeopleProfileList[2].photo,
        message = fakeMessages[2],
        unseenMessages = 1,
        status = fakePeopleProfileList[2].status
    ),
    MessageSnapshot(
        conversationId = 3,
        name = fakePeopleProfileList[3].name,
        photo = fakePeopleProfileList[3].photo,
        message = fakeMessages[3],
        unseenMessages = 50,
        status = fakePeopleProfileList[3].status
    ),
    MessageSnapshot(
        conversationId = 4,
        name = fakePeopleProfileList[4].name,
        photo = fakePeopleProfileList[4].photo,
        message = fakeMessages[4],
        unseenMessages = 67,
        status = fakePeopleProfileList[4].status
    ),
    MessageSnapshot(
        conversationId = 5,
        name = fakePeopleProfileList[5].name,
        photo = fakePeopleProfileList[5].photo,
        message = fakeMessages[5],
        unseenMessages = 0,
        status = fakePeopleProfileList[5].status
    ),
    MessageSnapshot(
        conversationId = 6,
        name = fakePeopleProfileList[6].name,
        photo = fakePeopleProfileList[6].photo,
        message = fakeMessages[6],
        unseenMessages = 3,
        status = fakePeopleProfileList[6].status
    )
)