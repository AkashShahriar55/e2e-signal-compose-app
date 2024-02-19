package com.nsa.signin.state

import com.nsa.domain.model.UiText
import com.nsa.signin.R
import com.nsa.ui.state.ErrorState


val emailOrMobileEmptyErrorState = ErrorState(
    hasError = true,
    errorMessage = R.string.login_error_msg_empty_email_mobile
)

val passwordEmptyErrorState = ErrorState(
    hasError = true,
    errorMessage = R.string.login_error_msg_empty_password
)