package com.nsa.ui.state

import androidx.annotation.StringRes
import com.nsa.ui.R

data class ErrorState(
    val hasError: Boolean = false,
    @StringRes val errorMessageStringResource: Int = R.string.empty_string
):UIState