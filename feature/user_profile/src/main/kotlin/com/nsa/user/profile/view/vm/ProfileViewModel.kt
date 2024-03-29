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
package com.nsa.user.profile.view.vm

import androidx.lifecycle.viewModelScope
import com.nsa.domain.model.fakeUser
import com.nsa.ui.event.UIEvent
import com.nsa.ui.vm.BaseViewModel
import com.nsa.user.profile.domain.interactor.UserProfileInteractor
import com.nsa.user.profile.view.UserProfileUIEvent
import com.nsa.user.profile.view.state.UserProfileUIState
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val interactor: UserProfileInteractor
) : BaseViewModel<UserProfileUIState,UserProfileUIEvent>() {

    override fun initUIState(): UserProfileUIState = UserProfileUIState.Empty
    override fun onUiEvent(event: UserProfileUIEvent) {

    }

    init {
        observeUserProfile()
    }

    private fun observeUserProfile() {
        viewModelScope.launch {

            //TODO: call repository here


            _uiState.update {
                UserProfileUIState.Success(fakeUser)
            }
        }
    }
}