package com.nsa.peoplelist.view

import com.nsa.ui.event.UIEvent

/**
 * Login Screen Events
 */


sealed class PeopleListUiEvent:UIEvent {
    object MakeFavorite:PeopleListUiEvent()
    object SayHi:PeopleListUiEvent()

}