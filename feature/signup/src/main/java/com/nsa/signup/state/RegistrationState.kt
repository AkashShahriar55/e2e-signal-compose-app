package com.nsa.signup.state

import com.nsa.ui.state.ErrorState


/**
 * Registration State holding ui input values
 */
data class RegistrationState(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val errorState: RegistrationErrorState = RegistrationErrorState(),
    val isRegistrationSuccessful: Boolean = false,
    val submitButtonEnabled:Boolean = false
)

/**
 * Error state in registration holding respective
 * text field validation errors
 */
data class RegistrationErrorState(
    val nameErrorState: ErrorState = ErrorState(),
    val emailIdErrorState: ErrorState = ErrorState(),
    val passwordErrorState: ErrorState = ErrorState(),
    val confirmPasswordErrorState: ErrorState = ErrorState()
)