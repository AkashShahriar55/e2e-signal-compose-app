package com.nsa.setting.managers

import android.content.Context
import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat.startIntentSenderForResult
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.nsa.setting.BuildConfig.GOOGLE_SERVER_CLIENT_ID
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update


const val REQ_ONE_TAP = ""

class GoogleSignInManager(
    context: Context,
    registry : ActivityResultRegistry
) {

    var getContent  =
        registry.register("Some key", ActivityResultContracts.StartIntentSenderForResult()){

        }



    private val request = getGoogleSignInRequest()
    private val googleSignInClient = Identity.getSignInClient(context)

    private fun getGoogleSignInRequest(): BeginSignInRequest {
        return BeginSignInRequest.builder()
            .setPasswordRequestOptions(
                BeginSignInRequest.PasswordRequestOptions.builder()
                    .setSupported(true)
                    .build())
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    // Your server's client ID, not your Android client ID.
                    .setServerClientId(GOOGLE_SERVER_CLIENT_ID)
                    // Only show accounts previously used to sign in.
                    .setFilterByAuthorizedAccounts(true)
                    .build())
            // Automatically sign in when exactly one credential is retrieved.
            .setAutoSelectEnabled(true)
            .build();
    }


    fun signIn(){

        val signIn = googleSignInClient.beginSignIn(request)

        signIn.addOnSuccessListener {result ->
            Log.d("checking", "signIn: $result")
            val intentSender = result.pendingIntent.intentSender
            val intentSenderRequest = IntentSenderRequest.Builder(intentSender).build()
            getContent.launch(intentSenderRequest)
        }.addOnFailureListener {
            Log.d("checking", "signIn: $it")
        }.addOnCanceledListener {

        }
    }


    companion object{
        fun build(context: Context,registry : ActivityResultRegistry):GoogleSignInManager{
            return GoogleSignInManager(context,registry)
        }
    }
}