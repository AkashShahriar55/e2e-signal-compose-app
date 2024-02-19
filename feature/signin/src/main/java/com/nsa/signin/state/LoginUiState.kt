package com.nsa.signin.state

import com.nsa.ui.state.UIState

sealed interface LoginUiState:UIState {

    object Empty: LoginUiState

    object Loading: LoginUiState

    class LoginSuccess() : LoginUiState

    class Error(val throwable: Throwable) : LoginUiState

}