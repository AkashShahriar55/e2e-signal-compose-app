package com.nsa.domain.model

import java.sql.Timestamp

enum class MessageStatus{
    Sending,
    Sent,
    Seen,
    Error,
    Recieved
}

data class Message(
    val chatId:Int,
    val message:BaseMessage,
    val time:Timestamp,
    val messageStatus:MessageStatus,
    val isLastMessage:Boolean = false,
    val shouldShowTime:Boolean = false
)


sealed interface BaseMessage



data class TextMessage(
    val message:String
):BaseMessage

data class AudioMessage(
    val link:String
):BaseMessage

data class PhotoMessage(
    val links:List<String>
):BaseMessage



val fakeConversation = listOf(
    Message(
        chatId = 1,
        message = TextMessage("Hey! How have you been?"),
        time = Timestamp.valueOf("2024-02-22 12:15:13"),
        messageStatus = MessageStatus.Recieved
    ),
    Message(
        chatId = 2,
        message = TextMessage("Wanna catch up for a beer?"),
        time = Timestamp.valueOf("2024-02-22 12:15:56"),
        messageStatus = MessageStatus.Recieved,
        shouldShowTime = true,
    ),
    Message(
        chatId = 3,
        message = TextMessage("Awesome! Let’s meet up"),
        time = Timestamp.valueOf("2024-02-22 12:20:23"),
        messageStatus = MessageStatus.Seen
    ),
    Message(
        chatId = 4,
        message = TextMessage("Can I also get my cousin along?\u2028Will that be okay?"),
        time = Timestamp.valueOf("2024-02-22 12:20:45"),
        messageStatus = MessageStatus.Seen,
        shouldShowTime = true,

    ),
    Message(
        chatId = 5,
        message = TextMessage("Yeah sure! get him too."),
        time = Timestamp.valueOf("2024-02-22 12:22:34"),
        messageStatus = MessageStatus.Recieved,
        shouldShowTime = true,
    ),
    Message(
        chatId = 6,
        message = TextMessage("Alright! See you soon!"),
        time = Timestamp.valueOf("2024-02-22 12:25:23"),
        messageStatus = MessageStatus.Seen,
        shouldShowTime = true,
    ),
    Message(
        chatId = 7,
        message = AudioMessage(""),
        time = Timestamp.valueOf("2024-02-22 12:25:55"),
        messageStatus = MessageStatus.Recieved,
        shouldShowTime = true,
    ),
    Message(
        chatId = 8,
        message = TextMessage("okay sure!"),
        time = Timestamp.valueOf("2024-02-22 12:27:55"),
        messageStatus = MessageStatus.Sent,
        shouldShowTime = true,
    ),
    Message(
        chatId = 9,
        message = TextMessage("Hey where are you ?"),
        time = Timestamp.valueOf("2024-02-22 12:35:55"),
        messageStatus = MessageStatus.Error
    ),
    Message(
        chatId = 10,
        message = TextMessage("Hey where are you ?"),
        time = Timestamp.valueOf("2024-02-22 12:35:55"),
        messageStatus = MessageStatus.Sending,
        shouldShowTime = true,
        isLastMessage = true
    )
)

var fakeMessages = listOf(
    Message(
        chatId = 0,
        message = TextMessage("Hi! How are you doing today?"),
        time = Timestamp.valueOf("2024-02-22 12:15:13"),
        messageStatus = MessageStatus.Recieved
    ),
    Message(
        chatId = 0,
        message = TextMessage("It's not always easy, but it's definitely worth it."),
        time = Timestamp.valueOf("2024-02-22 12:15:56"),
        messageStatus = MessageStatus.Sending
    ),
    Message(
        chatId = 0,
        message = TextMessage("Sounds good! See you then!"),
        time = Timestamp.valueOf("2024-02-22 12:20:23"),
        messageStatus = MessageStatus.Sent
    ),
    Message(
        chatId = 0,
        message = TextMessage("I'm doing good."),
        time = Timestamp.valueOf("2024-02-22 12:27:55"),
        messageStatus = MessageStatus.Seen
    ),
    Message(
        chatId = 0,
        message = TextMessage("okay sure!!"),
        time = Timestamp.valueOf("2024-02-22 12:35:55"),
        messageStatus = MessageStatus.Error
    ),
    Message(
        chatId = 0,
        message = AudioMessage(""),
        time = Timestamp.valueOf("2024-02-22 12:36:55"),
        messageStatus = MessageStatus.Error
    ),
    Message(
        chatId = 0,
        message = PhotoMessage(listOf("","","")),
        time = Timestamp.valueOf("2024-02-22 12:25:55"),
        messageStatus = MessageStatus.Sending
    )
)
