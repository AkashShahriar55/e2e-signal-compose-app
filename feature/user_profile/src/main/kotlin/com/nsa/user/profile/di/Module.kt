/*
 * Copyright 2023 | Dmitri Chernysh | https://mobile-dev.pro
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
package com.nsa.user.profile.di

import com.nsa.user.profile.data.local.ImplUserProfileLocalSource
import com.nsa.user.profile.data.local.UserProfileLocalSource
import com.nsa.user.profile.data.repository.ImplUserProfileRepository
import com.nsa.user.profile.data.repository.UserProfileRepository
import com.nsa.user.profile.domain.interactor.ImplUserProfileInteractor
import com.nsa.user.profile.domain.interactor.UserProfileInteractor
import com.nsa.user.profile.view.vm.ProfileViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

/**
 * User Profile screen module
 *
 * Created on Jul 22, 2023.
 *
 */

val featureUserProfileModule = module {

    scope<ProfileViewModel> {
        viewModelOf(::ProfileViewModel)

        scoped<UserProfileInteractor>{
            ImplUserProfileInteractor(
                repository = get()
            )
        }

        scoped<UserProfileRepository> {
            ImplUserProfileRepository(
                localSource = get()
            )
        }

        scoped<UserProfileLocalSource> {
            ImplUserProfileLocalSource(

            )
        }
    }
}