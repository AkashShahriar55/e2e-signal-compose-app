package com.nsa.chatlist.view

import com.nsa.ui.event.UIEvent

/**
 * Login Screen Events
 */


sealed class ChatListUiEvent:UIEvent {
    class ShowStory(val storyId:Int):ChatListUiEvent()
    class ShowConversation(val conversationId:Int):ChatListUiEvent()

}