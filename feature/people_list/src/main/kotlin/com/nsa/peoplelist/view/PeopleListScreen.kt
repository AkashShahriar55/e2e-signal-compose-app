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
package com.nsa.peoplelist.view

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nsa.domain.model.PeopleProfile
import com.nsa.domain.model.fakePeopleProfileList
import com.nsa.peoplelist.view.component.ProfileCard
import com.nsa.ui.component.ScreenBackground
import com.nsa.ui.theme.AppTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow









@Composable
fun PeopleListScreen(
    stateFlow: StateFlow<PeopleListUIState>,
    onNavigateToProfile: (profileId: Int) -> Unit,
    makeFavorite: (profileId: Int) -> Unit,
    sayHi: (profileId: Int) -> Unit
) {

    val uiState by stateFlow.collectAsStateWithLifecycle()
    val context = LocalContext.current

    ScreenBackground(
        modifier = Modifier
            .fillMaxSize()
    ) {

        when (uiState) {
            is PeopleListUIState.Loading -> Loading()
            is PeopleListUIState.Empty -> NoPeopleFound()
            is PeopleListUIState.Success ->
                PeopleList(
                    list = uiState.profileList,
                    onProfileClick = onNavigateToProfile,
                    makeFavorite,
                    sayHi
                )

            is PeopleListUIState.Fail -> {
                NoPeopleFound()
                LaunchedEffect(Unit) {
                    Toast.makeText(
                        context,
                        (uiState as PeopleListUIState.Fail).throwable?.localizedMessage,
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

        }

    }
}

@Composable
private fun NoPeopleFound() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "No people found",
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.Center),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
private fun Loading() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Loading...", modifier = Modifier
                .padding(16.dp)
                .align(Alignment.Center),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
private fun PeopleList(
    list: List<PeopleProfile>,
    onProfileClick: (profileId: Int) -> Unit,
    makeFavorite: (profileId: Int) -> Unit,
    sayHi: (profileId: Int) -> Unit
) {


    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
    ) {
        itemsIndexed(
            items = list,
            key = { _, item -> item.listKey() }
        ) { index, profile ->
            ProfileCard(
                modifier = Modifier.fillMaxHeight(),
                item = profile,
                onClick = { onProfileClick(profile.id) },
                { makeFavorite(profile.id) },
                { sayHi(profile.id) }
            )
        }
    }
}


@Preview
@Composable
fun PeopleListPreview() {
    AppTheme {
        PeopleListScreen(
            stateFlow = MutableStateFlow(PeopleListUIState.Success(fakePeopleProfileList)),
            onNavigateToProfile = { },
            {},
            {}
        )
    }
}