package com.nsa.signup.event

import com.nsa.ui.event.UIEvent

/**
 * Registration Screen Events
 */
sealed class RegistrationUiEvent:UIEvent {
    data class NameChanged(val inputValue: String) : RegistrationUiEvent()
    data class EmailChanged(val inputValue: String) : RegistrationUiEvent()
    data class PasswordChanged(val inputValue: String) : RegistrationUiEvent()
    data class ConfirmPasswordChanged(val inputValue: String) : RegistrationUiEvent()
    object Submit : RegistrationUiEvent()
}