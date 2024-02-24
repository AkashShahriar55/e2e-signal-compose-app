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
package com.nsa.people.profile.view

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nsa.domain.model.PeopleProfile
import com.nsa.domain.model.fakePeopleProfileList
import com.nsa.people.profile.view.args.PeopleProfileArgs
import com.nsa.ui.vm.BaseViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import usecases.findpeopleusecases.FetchPeopleProfileUseCase

/**
 * Profile screen for selected person from People list
 *
 * Created on Feb 04, 2023.
 *
 */
class PeopleProfileViewModel(
    savedStateHandle: SavedStateHandle
) : BaseViewModel<PeopleProfileUIState, PeopleProfileUiEvent>() {

    private val profileId : Int =  PeopleProfileArgs(savedStateHandle).peopleProfileId

    val fetchPeopleProfileUseCase = FetchPeopleProfileUseCase()



    init {
        Log.d("navigation", "PeopleProfileViewModel: args = $profileId")
        getProfile()
    }

    private fun getProfile() {
        viewModelScope.launch {
            _uiState.update {
                PeopleProfileUIState.Loading
            }
            val data = fetchPeopleProfileUseCase.execute(profileId)
            data.onSuccess {profile ->
                _uiState.update {
                    PeopleProfileUIState.Success(profile)
                }
            }
            data.onFailure {throwable ->
                _uiState.update {
                    PeopleProfileUIState.Fail(throwable)
                }

            }
        }
    }
    override fun initUIState(): PeopleProfileUIState {
        return PeopleProfileUIState.Empty
    }

    override fun onUiEvent(event: PeopleProfileUiEvent) {

    }


}