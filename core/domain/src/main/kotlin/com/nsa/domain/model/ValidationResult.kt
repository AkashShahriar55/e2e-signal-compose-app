package com.nsa.domain.model

import androidx.annotation.StringRes
import com.nsa.domain.R

data class ValidationResult(
    val successful: Boolean = false,
    @StringRes val errorMessage: Int = R.string.empty
)