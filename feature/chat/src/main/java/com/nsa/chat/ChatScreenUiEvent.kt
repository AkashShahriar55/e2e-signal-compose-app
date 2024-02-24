package com.nsa.chat

import com.nsa.ui.event.UIEvent

/**
 * Login Screen Events
 */


sealed class ChatScreenUiEvent:UIEvent {
    class MakeFavorite(val id:Int):ChatScreenUiEvent()
    class SayHi(val id:Int):ChatScreenUiEvent()

}