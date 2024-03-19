package mm.com.atom.eagle.data.remote.interceptor

import android.util.Log
import com.nsa.network.model.response.AuthResponse
import com.nsa.network.model.request.RefreshTokenRequest
import com.nsa.network.services.AuthService
import com.nsa.setting.managers.AuthManager
import com.nsa.setting.model.Token
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

class TokenAuthenticator @Inject constructor(
    private val authManager: AuthManager,
    private val authService: AuthService,
) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        // We need to have a token in order to refresh it.
        val result = runBlocking {
            val token = authManager.getToken()
            if(authManager.isLoggedIn()){
                val initialRequest = response.request
                val currentToken = token.accessToken
                if (isTokenAlreadyUpdated(initialRequest, currentToken)) {
                    //looks like some other request has already updated the token.
                    // Let's retry with updated token

                    initialRequest.updateCredentials(currentToken)

                } else if (!isAccessTokenTimeExpired(token.accessTokenExpireAt)) {
                    //access token time is not expired but we got HTTP Code 401.
                    // We will force logout the user here.
                    forceLogout()
                    null

                } else {
                    // Access token time is expired and we got 401. Let's try to refresh the token
                    Log.d(TAG, "authenticate: ")
                    Log.d(TAG,"Calling refresh token")
                    val refreshTokenResponse = refreshToken(token,authManager.getUserId())

                    if (refreshTokenResponse != null) {
                        Log.d(TAG,"Refresh token call succeed.")
                        initialRequest.updateCredentials(refreshTokenResponse.accessToken)
                    } else {
                        Log.d(TAG,"Refresh token call failed.")
                        forceLogout()
                        null
                    }
                }
            } else null
        }

        return result
    }

    private fun Request.updateCredentials(accessToken: String?): Request {
        val newRequestBuilder = this.newBuilder()
        newRequestBuilder.header(AuthHeaderName, "Bearer $accessToken")

        return newRequestBuilder.build()
    }

    private suspend fun refreshToken(tokenCredentials: Token,userId:String): AuthResponse? {
        val tokenRequestModel = RefreshTokenRequest(
            accessToken = tokenCredentials.accessToken,
            refreshToken = tokenCredentials.refreshToken,
            userId = userId
        )

        return try {
            authService.refreshToken(tokenRequestModel)?.data?.also {
//                authUtils.updateTokens(it)
            }
        } catch (e: java.lang.Exception) {
            null
        }

    }

    private fun forceLogout() {
//        eventUtils.postForceLogoutEvent()
    }

    private fun isTokenAlreadyUpdated(request: Request, currentToken: String?): Boolean {
        val tokenInHeader = request.header(AuthHeaderName)?.extractBearerToken()
        return tokenInHeader != currentToken
    }

    private fun String.extractBearerToken() = substringAfter(" ")
    private fun isAccessTokenTimeExpired(previousTime: Long?): Boolean {
        val currentTimeStamp = (System.currentTimeMillis() - 10) / 1000
        return currentTimeStamp > (previousTime ?: 0)
    }

    companion object {
        private const val AuthHeaderName = "Authorization"

        private const val TAG = "Authenticator"
    }

}
