package com.nsa.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nsa.ui.theme.AppTheme


@Composable
fun Chips(
    text:String,
    modifier: Modifier = Modifier,
    containerColor:Color = Color.Unspecified,
    contentColor:Color = Color.Unspecified
){

    val container = if(containerColor == Color.Unspecified) Color(0xffFFEDED) else containerColor
    val content = if(contentColor == Color.Unspecified) Color(0xffFF3578) else contentColor

    Box(
        modifier = modifier.height(35.dp).background(container,RoundedCornerShape(50)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier.padding(20.dp,0.dp),
            text = text,
            color = content
        )
    }
}


@Preview
@Composable
fun chipsPreview(){

    AppTheme {
        Chips("Art")
    }
}