package com.nsa.people.profile.view

import com.nsa.ui.event.UIEvent

/**
 * Login Screen Events
 */


sealed class PeopleProfileUiEvent:UIEvent {
    class MakeFavorite(val id:Int):PeopleProfileUiEvent()
    class SayHi(val id:Int):PeopleProfileUiEvent()

}