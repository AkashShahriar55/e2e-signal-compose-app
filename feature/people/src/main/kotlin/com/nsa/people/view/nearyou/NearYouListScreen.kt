package com.nsa.people.view.nearyou

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nsa.peoplelist.view.PeopleListScreen
import com.nsa.peoplelist.view.PeopleListUiEvent
import com.nsa.peoplelist.view.PeopleListViewModel
import usecases.findpeopleusecases.FetchPeopleListKey

@Composable
fun NearYouListScreen(
    onNavigateToProfile: (profileId: Int) -> Unit
){
    val viewModel: PeopleListViewModel = viewModel()

    viewModel.observePeopleList(FetchPeopleListKey.NearYou)

    PeopleListScreen(
        stateFlow = viewModel.uiState,
        onNavigateToProfile = onNavigateToProfile,
        makeFavorite = {
            viewModel.onUiEvent(PeopleListUiEvent.MakeFavorite(it))
        },
        sayHi = {
            viewModel.onUiEvent(PeopleListUiEvent.SayHi(it))
        }
    )
}