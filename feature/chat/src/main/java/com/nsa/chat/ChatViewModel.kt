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
package com.nsa.chat

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.nsa.chat.args.ChatArgs
import com.nsa.domain.model.UserProfile
import com.nsa.ui.vm.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import usecases.messagesUseCases.FetchConversationUseCase
import usecases.userprofileusecases.FetchUserProfileUseCase

/**
 * Profile screen for selected person from People list
 *
 * Created on Feb 04, 2023.
 *
 */
class ChatViewModel(
    savedStateHandle: SavedStateHandle
) : BaseViewModel<ChatScreenUIState, ChatScreenUiEvent>() {

    private val conversationId : Int =  ChatArgs(savedStateHandle).conversationId

    val fetchConversationUseCase = FetchConversationUseCase()


    val fetchUserProfileUseCase = FetchUserProfileUseCase()


    protected val _userProfileState: MutableStateFlow<UserProfile?> = MutableStateFlow(null)
    val userProfileState: StateFlow<UserProfile?> = _userProfileState.asStateFlow()



    init {
        Log.d("navigation", "PeopleProfileViewModel: args = $conversationId")
        getProfile()
        getUserProfile()
    }

    private fun getUserProfile() {
        viewModelScope.launch {
            val data = fetchUserProfileUseCase.execute()
            data.onSuccess {profile ->
                _userProfileState.update {
                    profile
                }
            }
            data.onFailure {throwable ->

            }
        }
    }

    private fun getProfile() {
        viewModelScope.launch {
            _uiState.update {
                ChatScreenUIState.Loading
            }
            val data = fetchConversationUseCase.execute(conversationId)
            data.onSuccess {profile ->
                _uiState.update {
                    ChatScreenUIState.Success(profile)
                }
            }
            data.onFailure {throwable ->
                _uiState.update {
                    ChatScreenUIState.Fail(throwable)
                }

            }
        }
    }
    override fun initUIState(): ChatScreenUIState {
        return ChatScreenUIState.Empty
    }

    override fun onUiEvent(event: ChatScreenUiEvent) {

    }


}