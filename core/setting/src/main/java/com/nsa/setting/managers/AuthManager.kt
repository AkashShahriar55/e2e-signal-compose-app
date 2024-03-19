package com.nsa.setting.managers

import android.content.Context
import android.health.connect.datatypes.AppInfo
import androidx.activity.result.ActivityResultRegistry
import androidx.datastore.core.DataStore
import com.nsa.datastore.Credentials
import com.nsa.setting.model.Token
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class AuthManager @Inject constructor(
    private val authPreference: DataStore<Credentials>,
)
{


    val isLoggedInFlow = authPreference.data.map { it.isLoggedIn }



    val tokenFlow = authPreference.data.map {
        Token(
            it.accessToken,
            it.refreshToken,
            it.expiryTime,
        )
    }


    val userIdFlow = authPreference.data.map { it.userId }






    fun isLoggedIn() = runBlocking {
        isLoggedInFlow.first()
    }


    fun getToken() = runBlocking {
        tokenFlow.first()
    }


    fun getUserId() = runBlocking {
        userIdFlow.first()
    }










    suspend fun logOut(){
        authPreference.updateData {credentials ->
            credentials.toBuilder().setAccessToken(null).build()
            credentials.toBuilder().setUserId(null).build()
            credentials.toBuilder().setRefreshToken(null).build()
            credentials.toBuilder().setExpiryTime(0).build()
            credentials.toBuilder().setIsLoggedIn(false).build()
        }
    }






    suspend fun loginWithGoogle(context: Context,registry: ActivityResultRegistry){
        GoogleSignInManager.build(context,registry).signIn()
    }

    suspend fun loginUser(userId:String,token: Token) {
        authPreference.updateData { credentials->
            credentials.toBuilder().setAccessToken(token.accessToken).build()
            credentials.toBuilder().setRefreshToken(token.refreshToken).build()
            credentials.toBuilder().setExpiryTime(token.accessTokenExpireAt).build()
            credentials.toBuilder().setUserId(userId).build()
            credentials.toBuilder().setIsLoggedIn(true).build()
        }
    }


}