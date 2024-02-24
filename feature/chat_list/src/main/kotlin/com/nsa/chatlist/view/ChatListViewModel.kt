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
package com.nsa.chatlist.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nsa.domain.model.fakeChatList
import com.nsa.ui.vm.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import usecases.messagesUseCases.FetchChatListUseCase


class ChatListViewModel : BaseViewModel<ChatListUIState, ChatListUiEvent>() {

    val fetchChatListUseCase = FetchChatListUseCase()

    override fun initUIState(): ChatListUIState {
        return ChatListUIState.Empty
    }

    override fun onUiEvent(event: ChatListUiEvent) {
        when(event){
            is ChatListUiEvent.ShowConversation -> TODO()
            is ChatListUiEvent.ShowStory -> TODO()
        }
    }

    init {
        observeChatList()
    }

    private fun observeChatList() {
        viewModelScope.launch {

            _uiState.update { ChatListUIState.Loading }

            val data = fetchChatListUseCase.execute()

            data.onSuccess{chatList->
                _uiState.update {
                    ChatListUIState.Success(chatList)
                }
            }

            data.onFailure{throwable ->
                _uiState.update {
                    ChatListUIState.Fail(throwable)
                }
            }


        }
    }


}