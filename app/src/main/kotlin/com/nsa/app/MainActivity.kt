package com.nsa.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.registerForActivityResult
import androidx.compose.runtime.getValue
import androidx.core.view.WindowCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nsa.app.ui.MainApp
import com.nsa.socket.SocketManager
import com.nsa.ui.theme.AppTheme
import com.nsa.ui.theme.darkModeState
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var socketManager:SocketManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        socketManager.connect()
        //False - allows to drawing the content "edge-to-edge"
        WindowCompat.setDecorFitsSystemWindows(window, false)



        setContent {
            val darkModeState by darkModeState.collectAsStateWithLifecycle()

            AppTheme(darkTheme = darkModeState) {
                MainApp()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        socketManager.disconnect()
    }
}