package com.nsa.ui.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.nsa.ui.theme.AppTheme


@Composable
private fun MyTabIndicator(
    indicatorWidth: Dp,
    indicatorOffset: Dp,
    indicatorColor: Color,
) {
    Box(
        modifier = Modifier
            .fillMaxHeight()
            .width(
                width = indicatorWidth,
            )
            .offset(
                x = indicatorOffset,
            )
            .clip(
                shape = CircleShape,
            )
            .background(
                color = indicatorColor,
            ),
    )
}

@Composable
private fun MyTabItem(
    isSelected: Boolean,
    onClick: () -> Unit,
    tabWidth: Dp,
    text: String,
) {
    val tabTextColor: Color by animateColorAsState(
        targetValue = if (isSelected) {
            White
        } else {
            Black
        },
        animationSpec = tween(easing = LinearEasing),
    )
    Text(
        modifier = Modifier
            .clip(CircleShape)
            .clickable {
                onClick()
            }
            .width(tabWidth)
            .padding(
                vertical = 8.dp,
                horizontal = 12.dp,
            ),
        text = text,
        color = tabTextColor,
        textAlign = TextAlign.Center,
    )
}

@Composable
fun CustomTab(
    selectedItemIndex: Int,
    items: List<String>,
    modifier: Modifier = Modifier,
    onClick: (index: Int) -> Unit,
) {


    var size by remember { mutableStateOf(IntSize.Zero) }

    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(Color.White)
            .height(intrinsicSize = IntrinsicSize.Min)
            .onSizeChanged {
                size = it
            },
    ) {
        with(LocalDensity.current){
            val tabWidth = size.width.toDp()/2
            val indicatorOffset: Dp by animateDpAsState(
                targetValue = tabWidth * selectedItemIndex,
                animationSpec = tween(easing = LinearEasing), label = "",
            )
            MyTabIndicator(
                indicatorWidth = tabWidth,
                indicatorOffset = indicatorOffset,
                indicatorColor = MaterialTheme.colorScheme.primary,
            )
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.clip(CircleShape),
            ) {
                items.mapIndexed { index, text ->
                    val isSelected = index == selectedItemIndex
                    MyTabItem(
                        isSelected = isSelected,
                        onClick = {
                            onClick(index)
                        },
                        tabWidth = tabWidth,
                        text = text,
                    )
                }
            }
        }

    }
}


@Preview
@Composable
fun CustomTabSample() {
    val (selected, setSelected) = remember {
        mutableStateOf(0)
    }

    AppTheme {
        CustomTab(
            modifier = Modifier.fillMaxWidth(),
            items = listOf("Near You", "New Matches"),
            selectedItemIndex = selected,
            onClick = setSelected,
        )
    }

}