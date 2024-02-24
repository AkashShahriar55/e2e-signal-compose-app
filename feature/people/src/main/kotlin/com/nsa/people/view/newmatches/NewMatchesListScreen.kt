package com.nsa.people.view.newmatches

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nsa.peoplelist.view.PeopleListScreen
import com.nsa.peoplelist.view.PeopleListUiEvent
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
        onNavigateToProfile = onNavigateToProfile,
        {
            viewModel.onUiEvent(PeopleListUiEvent.MakeFavorite(it))
        },
        {
            viewModel.onUiEvent(PeopleListUiEvent.SayHi(it))
        }
    )
}