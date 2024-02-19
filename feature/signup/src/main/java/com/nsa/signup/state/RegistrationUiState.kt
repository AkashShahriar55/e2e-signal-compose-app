package com.nsa.signup.state

import com.nsa.ui.state.UIState

sealed interface RegistrationUiState:UIState {

    object Empty: RegistrationUiState

    object Loading: RegistrationUiState

    class RegistrationSuccess() : RegistrationUiState

    class Error(val throwable: Throwable) : RegistrationUiState

}