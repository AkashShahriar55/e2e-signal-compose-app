package com.nsa.find_people

import com.nsa.ui.event.UIEvent

/**
 * Login Screen Events
 */


sealed class FindPeopleUiEvent:UIEvent {
    object GoToNotification: FindPeopleUiEvent()
}