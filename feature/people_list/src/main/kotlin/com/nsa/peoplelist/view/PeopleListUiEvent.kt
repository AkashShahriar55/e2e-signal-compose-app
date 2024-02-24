package com.nsa.peoplelist.view

import com.nsa.ui.event.UIEvent

/**
 * Login Screen Events
 */


sealed class PeopleListUiEvent:UIEvent {
    class MakeFavorite(val id:Int):PeopleListUiEvent()
    class SayHi(val id:Int):PeopleListUiEvent()

}