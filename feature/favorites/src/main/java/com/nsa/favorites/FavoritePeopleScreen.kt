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
package com.nsa.favorites

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nsa.peoplelist.view.PeopleListScreen
import com.nsa.peoplelist.view.PeopleListUIState
import com.nsa.ui.component.SimpleTopBar
import kotlinx.coroutines.flow.StateFlow

/**
 * Host for People list and People profile
 *
 * Created on Feb 04, 2023.
 *
 */
@Composable
fun FavoritePeopleScreen(
    stateFlow: StateFlow<PeopleListUIState>,
    makeFavorite: (profileId: Int) -> Unit,
    sayHi: (profileId: Int) -> Unit,
    onNavigateToProfile: (profileId: Int) -> Unit
) {

    Scaffold(
        topBar = { SimpleTopBar(title = "Favorite",onBackButtonClicked= {},onSearchButtonClicked= {}) },
        contentWindowInsets = WindowInsets.statusBars
    ){
        Box(
            modifier = Modifier.padding(it).padding(24.dp,24.dp,24.dp,0.dp)
        ){
            PeopleListScreen(
                stateFlow,
                onNavigateToProfile = onNavigateToProfile,
                makeFavorite,
                sayHi
            )
        }

    }

}


