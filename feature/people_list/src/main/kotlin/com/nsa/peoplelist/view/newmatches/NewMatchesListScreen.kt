package com.nsa.peoplelist.view.newmatches

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nsa.peoplelist.view.PeopleListScreen
import com.nsa.peoplelist.view.PeopleListViewModel
import usecases.findpeopleusecases.FetchPeopleListKey


@Composable
fun NewMatchesListScreen(
    onNavigateToProfile: (profileId: Int) -> Unit
){
    val viewModel: PeopleListViewModel = viewModel()

    viewModel.observePeopleList(FetchPeopleListKey.NewMatches)

    PeopleListScreen(
        stateFlow = viewModel.uiState,
        onNavigateToProfile = onNavigateToProfile
    )
}