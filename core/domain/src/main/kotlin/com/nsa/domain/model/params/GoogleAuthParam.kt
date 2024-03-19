package com.nsa.domain.model.params

import android.content.Context
import androidx.activity.result.ActivityResultRegistry

data class GoogleAuthParam(
    val context: Context,
    val registry: ActivityResultRegistry
)
