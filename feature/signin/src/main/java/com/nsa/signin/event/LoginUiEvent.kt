package com.nsa.signin.event

import com.nsa.ui.event.UIEvent

/**
 * Login Screen Events
 */


enum class SocialType{
    Facebook,
    Google,
    Apple
}

sealed class LoginUiEvent:UIEvent {

    data class SocialMediaLogin(val socialType: SocialType): LoginUiEvent()
    data class EmailOrMobileChanged(val inputValue: String) : LoginUiEvent()
    data class PasswordChanged(val inputValue: String) : LoginUiEvent()
    object Submit : LoginUiEvent()
}