package com.nsa.network

import android.util.Log
import androidx.annotation.Keep
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Response

fun Any.toJson(): String = Gson().toJson(this)

@Throws(IllegalStateException::class)
@Keep
inline fun <reified T> handleApiResponse(response: Response<T>): T {
    val finalResponse: T? = if (response.isSuccessful) response.body()
    else {
        val json = response.errorBody()?.string()
        val parsed: T? = fromJson(json)
        parsed
    }
    if (finalResponse == null) throw IllegalStateException("Unable to format given response")
    return finalResponse
}


@Keep
inline fun <reified T> fromJson(json: String?): T? {
    return try {
        val type = object : TypeToken<T>() {}.type
        Gson().fromJson(json, type)
    } catch (e: Exception) {
        e.debugStackTrace()
        null
    }
}

fun Throwable.debugStackTrace() {
    stackTraceToString().debugLog()
}

fun <T> T.debugLog() = this.apply { Log.d("network-debug",toString()) }
