package com.nsa.ui.state

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.nsa.domain.model.UiText
import com.nsa.ui.R

data class ErrorState(
    val hasError: Boolean = false,
    @StringRes val errorMessage: Int = R.string.empty_string
):UIState{

}