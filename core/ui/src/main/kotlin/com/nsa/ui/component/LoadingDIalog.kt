package com.nsa.ui.component

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun LoadingDialog( message:String, dismissOnBackPress: Boolean = false, dismissOnClickOutside: Boolean = false) {
    Dialog(
        onDismissRequest = {

        },
        DialogProperties(
            dismissOnBackPress = dismissOnBackPress,
            dismissOnClickOutside = dismissOnClickOutside
        )
    ) {
        DialogContent(message)
    }
}

@Composable
fun DialogContent(message:String) {

    Surface(
        shape = RoundedCornerShape(10.dp),
        shadowElevation = 10.dp
    ) {
        Row(
            modifier = Modifier.padding(10.dp),
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            CircularProgressIndicator()
            Text(
                modifier = Modifier,
                text = message
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSomeDialogContent() {
    DialogContent("Loading ...")
}