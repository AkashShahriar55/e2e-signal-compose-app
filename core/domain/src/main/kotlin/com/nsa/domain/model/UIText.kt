package com.nsa.domain.model

import android.content.Context
import androidx.annotation.StringRes

sealed class UiText {
    data class DynamicString(val value: String) : UiText()
    class StringResource(
        @StringRes val resId: Int,
        vararg val args: Any
    ) : UiText()

//    @Composable
//    fun asString(): String {
//        return when (this) {
//            is DynamicString -> value
//            is StringResource -> stringResource(resId, *args)
//        }
//    }


    fun getStringResourceId():Int {
        return when(this){
            is DynamicString -> 0
            is StringResource -> resId
        }
    }

    fun asString(context: Context): String {
        return when (this) {
            is DynamicString -> value
            is StringResource -> context.getString(resId, *args)
        }
    }
}