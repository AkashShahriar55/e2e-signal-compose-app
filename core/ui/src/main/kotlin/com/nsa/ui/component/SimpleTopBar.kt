package com.nsa.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.nsa.ui.theme.AppTheme


@Composable
fun SimpleTopBar(
    modifier: Modifier = Modifier,
    title:String,
    searchEnabled:Boolean = false
) {

    Box(
        modifier = modifier
            .fillMaxWidth()
    ) {
        RoundedBackButton(){

        }
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = title,
            color = Color(0xff252527)
        )

        if(searchEnabled){
            RoundedSearchButton(
                modifier = Modifier.align(Alignment.CenterEnd),
            ) {

            }
        }

    }
}


@Preview
@Composable
fun PeopleListPreview() {
    AppTheme {
        ScreenBackground(color = MaterialTheme.colorScheme.primary) {
            Row {
                SimpleTopBar(
                    title = "Some",
                    searchEnabled =  true)

            }
        }

    }
}