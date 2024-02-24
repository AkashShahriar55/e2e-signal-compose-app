package com.nsa.find_people

import androidx.lifecycle.viewModelScope
import com.nsa.domain.model.fakeUserProfile
import com.nsa.ui.vm.BaseViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import usecases.userprofileusecases.FetchUserProfileUseCase

class FindPeopleViewModel: BaseViewModel<FindPeopleUiState, FindPeopleUiEvent>() {


    val fetchUserProfileUseCase = FetchUserProfileUseCase()

    init {
        observeUserProfile()
    }

    private fun observeUserProfile() {
        viewModelScope.launch{
            _uiState.update{
                FindPeopleUiState.Loading
            }

            _uiState.update{
                val data = fetchUserProfileUseCase.execute()
                if(data.isSuccess){
                    FindPeopleUiState.Success(data.getOrDefault(fakeUserProfile))
                }else{
                    FindPeopleUiState.Fail(data.exceptionOrNull())
                }
            }
        }
    }

    override fun initUIState(): FindPeopleUiState {
        return FindPeopleUiState.Empty
    }

    override fun onUiEvent(event: FindPeopleUiEvent) {
        when(event){

            else -> {

            }
        }
    }
}