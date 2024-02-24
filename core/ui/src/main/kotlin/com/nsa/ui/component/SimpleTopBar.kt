package com.nsa.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nsa.ui.theme.AppTheme


@Composable
fun SimpleTopBar(
    modifier: Modifier = Modifier,
    title:String,
    searchEnabled:Boolean = false,
    onBackButtonClicked:()->Unit,
    onSearchButtonClicked:()->Unit
) {

    Box(
        modifier = modifier
            .fillMaxWidth()
    ) {
        FilledIconButton(
            modifier = Modifier.align(Alignment.CenterStart),
            onClick = onBackButtonClicked,
            colors = IconButtonDefaults.filledTonalIconButtonColors(),
            shape = RoundedCornerShape(12.dp)
        ){
            Icon(
                modifier = Modifier
                    .size(14.dp),
                imageVector = Icons.Filled.ArrowBackIosNew,
                contentDescription = ""
            )
        }
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = title,
        )

        if(searchEnabled){
            FilledIconButton(
                modifier = Modifier.align(Alignment.CenterEnd),
                onClick = onSearchButtonClicked,
                colors = IconButtonDefaults.filledTonalIconButtonColors(),
                shape = RoundedCornerShape(12.dp)
            ){
                Icon(
                    modifier = Modifier
                        .size(24.dp),
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Search icon"
                )
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
                    searchEnabled =  true,
                    onBackButtonClicked= {},
                    onSearchButtonClicked = {}
                )

            }
        }

    }
}