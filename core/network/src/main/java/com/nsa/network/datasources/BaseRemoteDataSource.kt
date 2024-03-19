package com.nsa.network.datasources

import com.nsa.network.handleApiResponse
import com.nsa.network.model.ApiResponse
import retrofit2.Response
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

abstract class BaseRemoteDataSource {



    protected suspend inline fun <reified V> callApi(
        noinline apiCall: suspend () -> Response<ApiResponse<V>>
    ): ApiResponse<V>{
        return try {
            handleApiResponse(apiCall.invoke())
        } catch (e:Exception){
            parseException<V>(e)
        }
    }


    protected inline fun <reified T> parseException(exception: Exception): ApiResponse<T> {

        when (exception) {
            is UnknownHostException,
            is SocketTimeoutException,
            is ConnectException -> {
                return ApiResponse(
                    false,
                    null,
                    null,
                    -1,
                    null
                )
            }

            else -> {
                return ApiResponse(
                    false,
                    null,
                    null,
                    -2,
                    null
                )
            }
        }
    }

}