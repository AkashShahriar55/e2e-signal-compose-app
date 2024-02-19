package com.nsa.ui.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun ShowError(
    message:String,

){
    Snackbar(
        dismissAction = {
            TextButton(onClick = {}) {
                Text("Ok")
            }
        },
        modifier = Modifier.padding(8.dp)
    ) { Text(text = message) }
}


@Preview
@Composable
fun errorPreview(){
    ShowError("Sorry can't login")
}