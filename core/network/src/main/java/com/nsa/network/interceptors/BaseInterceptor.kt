package mm.com.atom.eagle.data.remote.interceptor


import com.nsa.network.HttpStatusCodes
import com.nsa.network.model.ApiResponse
import com.nsa.network.toJson
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody

abstract class BaseInterceptor : Interceptor {
    fun Response.createEmptyResponse(): Response {
        val apiResponse = ApiResponse(
            false,
            null,
            null,
            HttpStatusCodes.ERR_NO_POPUP,
            null
        )
        val newResponseBody = apiResponse.toJson().toResponseBody(body?.contentType())

        return newBuilder()
            .body(newResponseBody)
            .build()
    }
}