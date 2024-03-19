package mm.com.atom.eagle.data.remote.interceptor

import com.nsa.setting.managers.AuthManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val authManager: AuthManager,
) : BaseInterceptor() {

    override fun intercept(chain: Interceptor.Chain): Response {
        val initialRequest = chain.request()
        val token = authManager.getToken()
        val newRequest = token.accessToken.let { accessToken ->
            val newRequestBuilder = initialRequest.newBuilder()
            newRequestBuilder.header("Authorization", "Bearer $accessToken")
            newRequestBuilder.build()
        }
        return chain.proceed(newRequest)
    }


}