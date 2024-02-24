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
package com.nsa.peoplelist.view

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.nsa.ui.vm.BaseViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import usecases.findpeopleusecases.FetchPeopleListKey
import usecases.findpeopleusecases.FetchPeopleListUseCase
import usecases.findpeopleusecases.MakeFavoriteUseCase


class PeopleListViewModel : BaseViewModel<PeopleListUIState, PeopleListUiEvent>() {

    private val fetchPeopleListUseCase = FetchPeopleListUseCase()
    private val makeFavoriteUseCase = MakeFavoriteUseCase()

    override fun initUIState(): PeopleListUIState {
        return PeopleListUIState.Empty
    }

    override fun onUiEvent(event: PeopleListUiEvent) {
        when(event){
            is PeopleListUiEvent.MakeFavorite -> makeFavorite(event.id)
            is PeopleListUiEvent.SayHi -> TODO()
        }
    }

    private fun makeFavorite(id: Int) {
        viewModelScope.launch{
            val data = makeFavoriteUseCase.execute(id)
            Log.d("check_date", "makeFavorite: ${data.getOrNull()} ${data.exceptionOrNull()}")
            data.onSuccess {
                _uiState.update {
                    PeopleListUIState.Success(
                            it
                            .profileList
                            .toMutableList()
                            .map { profile->
                                if(profile.id == id){
                                    profile
                                        .copy(
                                            isFavorite = data.getOrDefault(false)
                                        )
                                }
                                else{
                                    profile
                                }
                            }
                    )
                }
            }

            data.onFailure {

            }

        }
    }


    fun observePeopleList(key:FetchPeopleListKey) {
        viewModelScope.launch {

            _uiState.update { PeopleListUIState.Loading }

            // _uiState.update { PeopleProfileUIState.Empty }
            //   _uiState.update { PeopleProfileUIState.Fail(Throwable("Test error")) }

            _uiState.update {
                val data = fetchPeopleListUseCase.execute(key)
                if(data.isSuccess){
                    PeopleListUIState.Success(data.getOrDefault(listOf()))
                }else{
                    PeopleListUIState.Fail(data.exceptionOrNull())
                }

            }
        }
    }

}