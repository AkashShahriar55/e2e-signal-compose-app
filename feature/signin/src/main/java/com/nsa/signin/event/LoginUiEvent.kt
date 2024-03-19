package com.nsa.signin.event

import android.content.Context
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultRegistry
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

    data class SocialMediaLogin(val socialType: SocialType,val context: Context,val registry: ActivityResultRegistry): LoginUiEvent()
    data class EmailOrMobileChanged(val inputValue: String) : LoginUiEvent()
    data class PasswordChanged(val inputValue: String) : LoginUiEvent()
    object Submit : LoginUiEvent()


}