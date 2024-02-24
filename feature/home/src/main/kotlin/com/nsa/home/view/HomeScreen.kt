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
package com.nsa.home.view

import android.widget.StackView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import com.nsa.ui.component.ScreenBackground

@Composable
fun HomeScreen(
    nestedNavGraph: @Composable () -> Unit,
    bottomBar: @Composable () -> Unit,
    topBar:@Composable () -> Unit = {},
) {



    Scaffold(
        topBar = topBar,
        bottomBar = bottomBar
    ) {
        ScreenBackground(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)

        ) {
            nestedNavGraph.invoke()
        }
    }
}