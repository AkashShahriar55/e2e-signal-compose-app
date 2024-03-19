package com.nsa.socket

import android.util.Log
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.client.Socket.EVENT_CONNECT
import io.socket.client.Socket.EVENT_CONNECTING
import io.socket.client.Socket.EVENT_DISCONNECT
import io.socket.client.Socket.EVENT_ERROR
import java.net.URISyntaxException


class SocketManager private constructor(val url:String){



    private var socket: Socket? = null

    init {
        try {
            socket = IO.socket(url)
            socket?.on(EVENT_CONNECTING){
                val message = if(it.size>0) it[0] as String else ""
                Log.d(TAG, " connecting $message")
            }

            socket?.on(EVENT_CONNECT){
                val message = if(it.size>0) it[0] as String else ""
                Log.d(TAG, " connected $message")
                socket?.emit("ping","ping from abcd")
            }

            socket?.on(EVENT_DISCONNECT){
                val message = if(it.size>0) it[0] as String else ""
                Log.d(TAG, " disconnected $message")
            }

            socket?.on(EVENT_ERROR){
                val message = if(it.size>0) it[0] as String else ""
                Log.d(TAG, " error $message")
            }






        } catch (e: URISyntaxException) {
            Log.d(TAG, "error: $e")
            e.printStackTrace()
        }
    }

    fun connect() {
        socket?.connect()
    }

    fun disconnect() {
        socket?.disconnect()
    }

    fun isConnected(): Boolean {
        return socket?.connected() ?: false
    }

    fun onMessageReceived(listener: (String) -> Unit) {
        socket?.on("message") { args ->
            val message = args[0] as String
            listener.invoke(message)
        }
    }

    fun sendMessage(message: String) {
        socket?.emit("message", message)
    }

    companion object {
        const val TAG = "socket-io"
        fun build(url:String):SocketManager{
            return SocketManager(url)
        }
    }

}