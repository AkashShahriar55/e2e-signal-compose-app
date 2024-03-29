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

import com.nsa.domain.model.PeopleProfile
import com.nsa.ui.state.UIState

/**
 * UI state for [com.nsa.peoplelist.view.PeopleListScreen]
 *
 * Created on Feb 04, 2023.
 *
 */


sealed class PeopleProfileUIState():UIState {



    object Empty : PeopleProfileUIState()

    object Loading : PeopleProfileUIState()

    class Success(val profile : PeopleProfile?) : PeopleProfileUIState()

    class Fail(val throwable: Throwable?) : PeopleProfileUIState()
}


