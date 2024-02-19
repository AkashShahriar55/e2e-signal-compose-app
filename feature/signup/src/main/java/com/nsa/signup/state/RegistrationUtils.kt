package com.nsa.signup.state

import com.nsa.domain.model.UiText
import com.nsa.signup.R
import com.nsa.ui.state.ErrorState


val emailEmptyErrorState = ErrorState(
    hasError = true,
    errorMessage =R.string.registration_error_msg_empty_email
)

val nameEmptyErrorState = ErrorState(
    hasError = true,
    errorMessage = R.string.registration_error_msg_empty_name
)

val passwordEmptyErrorState = ErrorState(
    hasError = true,
    errorMessage = R.string.registration_error_msg_empty_password
)

val confirmPasswordEmptyErrorState = ErrorState(
    hasError = true,
    errorMessage = R.string.registration_error_msg_empty_confirm_password

)

val passwordMismatchErrorState = ErrorState(
    hasError = true,
    errorMessage = R.string.registration_error_msg_password_mismatch
)
