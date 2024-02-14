/*
 * Copyright 2022 | Dmitri Chernysh | https://mobile-dev.pro
 *
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.nsa.ui.component

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.nsa.ui.R
import com.nsa.ui.theme.AppTheme

/**
 * Common navigation bar for bottom menu
 *
 * Created on Jan 12, 2023.
 *
 */

@Composable
fun AppBottomBar(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit
) {

    MyNavigationBar(
        modifier = modifier,
        contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
        tonalElevation = 4.dp,
        content = content
    )
}

@Composable
fun MyNavigationBar(
    modifier: Modifier = Modifier,
    containerColor: Color = NavigationBarDefaults.containerColor,
    contentColor: Color = MaterialTheme.colorScheme.contentColorFor(containerColor),
    tonalElevation: Dp = NavigationBarDefaults.Elevation,
    windowInsets: WindowInsets = NavigationBarDefaults.windowInsets,
    content: @Composable RowScope.() -> Unit
) {




    Surface(
        color = MaterialTheme.colorScheme.inversePrimary,
        contentColor = contentColor,
        tonalElevation = tonalElevation,
        modifier = modifier
            .background(Color.Transparent)
            .padding(20.dp,0.dp,20.dp,20.dp)
            .windowInsetsPadding(windowInsets)
            .height(76.dp)
            .clipToBounds()
        ,
        shape = RoundedCornerShape(50),
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .selectableGroup(),
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            verticalAlignment = Alignment.CenterVertically,
            content = content
        )
    }
}

@Composable
fun RowScope.AppBottomBarItem(
    selected: Boolean,
    onClick: () -> Unit,
    icon: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    selectedIcon: @Composable () -> Unit = icon,
    enabled: Boolean = true,
) {

    Surface(
        modifier = Modifier
            .weight(1f)
            .aspectRatio(1f)
            .padding(5.dp)
            .selectable(
                selected = selected,
                onClick = onClick,
                enabled = enabled,
                role = Role.Tab,
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
            )
            .clickable {
                onClick()
            },
        shape = RoundedCornerShape(50),
        color = if(selected) MaterialTheme.colorScheme.primary else Color.Transparent
    ) {
        icon()
    }




}


@Preview(showBackground = true)
@Composable
fun PreviewRegistrationScreen() {
    AppTheme(darkTheme = true) {
        AppBottomBar(
            modifier = Modifier.background(Color.Transparent)
        ) {

            listOf("1","2","3","4").forEach { screen ->

                val selected: Boolean = screen == "1"

                AppBottomBarItem(
                    selected = selected,
                    onClick = { },
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.home_icon),
                            contentDescription = null
                        )
                    }
                )
            }
        }
    }
}