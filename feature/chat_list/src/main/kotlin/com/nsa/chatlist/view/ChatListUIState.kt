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

import com.nsa.domain.model.ChatList
import com.nsa.domain.model.ChatListData
import com.nsa.ui.state.UIState

/**
 * UI state for [com.nsa.chatlist.view.ChatListScreen]
 *
 * Created on Feb 04, 2023.
 *
 */
sealed interface ChatListUIState:UIState {

    object Empty : ChatListUIState

    object Loading : ChatListUIState

    class Success(val chatList : ChatListData) : ChatListUIState

    class Fail(val throwable: Throwable?) : ChatListUIState
}