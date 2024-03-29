package com.nsa.signup

import androidx.lifecycle.viewModelScope
import com.nsa.data.model.SignUpData
import com.nsa.data.repository.AuthRepository
import com.nsa.domain.model.ValidationResult
import com.nsa.signup.state.RegistrationErrorState
import com.nsa.signup.state.RegistrationState
import com.nsa.signup.event.RegistrationUiEvent
import com.nsa.signup.state.RegistrationUiState
import com.nsa.signup.state.confirmPasswordEmptyErrorState
import com.nsa.signup.state.emailEmptyErrorState
import com.nsa.signup.state.nameEmptyErrorState
import com.nsa.signup.state.passwordEmptyErrorState
import com.nsa.signup.state.passwordMismatchErrorState
import com.nsa.ui.state.ErrorState
import com.nsa.ui.vm.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import usecases.ValidateEmailUseCase
import usecases.ValidatePasswordUseCase
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel  @Inject constructor(
    private val authRepository: AuthRepository
)  : BaseViewModel<RegistrationUiState, RegistrationUiEvent>() {


    private val validateEmailUseCase = ValidateEmailUseCase()
    private val validatePasswordUseCase = ValidatePasswordUseCase()

    protected val _registrationState: MutableStateFlow<RegistrationState> = MutableStateFlow(RegistrationState())
    val registrationState: StateFlow<RegistrationState> = _registrationState.asStateFlow()


    /**
     * Function called on any login event [RegistrationUiEvent]
     */
    override fun onUiEvent(event: RegistrationUiEvent) {
        when (event) {

            // Email id changed event
            is RegistrationUiEvent.NameChanged -> {
                _registrationState.update {
                    it.copy(
                        name = event.inputValue,
                        errorState = it.errorState.copy(
                            nameErrorState = if (event.inputValue.trim().isEmpty()) {
                                // Email id empty state
                                nameEmptyErrorState
                            } else {
                                // Valid state
                                ErrorState()
                            }

                        ),
                    )
                }
                checkSubmitButtonEnabled()
            }

            // Mobile Number changed event
            is RegistrationUiEvent.EmailChanged -> {
                _registrationState.update{
                    it.copy(
                        email = event.inputValue,
                        errorState = it.errorState.copy(
                            emailIdErrorState = if (event.inputValue.trim()
                                    .isEmpty()
                            ) {
                                // Mobile Number Empty state
                                emailEmptyErrorState
                            } else {
                                // Valid state
                                ErrorState()
                            }

                        ),
                    )
                }
                checkSubmitButtonEnabled()
            }

            // Password changed event
            is RegistrationUiEvent.PasswordChanged -> {
                _registrationState.update{
                    it.copy(
                        password = event.inputValue,
                        errorState = it.errorState.copy(
                            passwordErrorState = if (event.inputValue.trim().isEmpty()) {
                                // Password Empty state
                                passwordEmptyErrorState
                            } else {
                                // Valid state
                                ErrorState()
                            }

                        ),
                    )
                }
                checkSubmitButtonEnabled()
            }

            // Confirm Password changed event
            is RegistrationUiEvent.ConfirmPasswordChanged -> {
                _registrationState.update{
                    it.copy(
                        confirmPassword = event.inputValue,
                        errorState = it.errorState.copy(
                            confirmPasswordErrorState = when {

                                // Empty state of confirm password
                                event.inputValue.trim().isEmpty() -> {
                                    confirmPasswordEmptyErrorState
                                }

                                // Password is different than the confirm password
                                it.password.trim() != event.inputValue -> {
                                    passwordMismatchErrorState
                                }

                                // Valid state
                                else -> ErrorState()
                            }
                        ),
                    )
                }
                checkSubmitButtonEnabled()
            }


            // Submit Registration event
            is RegistrationUiEvent.Submit -> {
                viewModelScope.launch {
                    val inputsValidated = validateInputs()
                    if (inputsValidated) {
                        // TODO Trigger registration in authentication flow
                        registration()

                    }
                }

            }
        }
    }


    private fun checkSubmitButtonEnabled(){
        if(_registrationState.value.name.isNotEmpty()
            &&  _registrationState.value.email.isNotEmpty()
            &&  _registrationState.value.password.isNotEmpty()
            &&  _registrationState.value.confirmPassword.isNotEmpty()){
            _registrationState.update{
                it.copy(
                    submitButtonEnabled = true
                )
            }
        }else{
            _registrationState.update{
                it.copy(
                    submitButtonEnabled = false
                )
            }
        }
    }

    private suspend fun validateEmail(): Boolean {
        val emailResult = validateEmailUseCase.execute(registrationState.value.email)
        val validationResult = emailResult.getOrDefault(ValidationResult())

        _registrationState.update {
            it.copy(
                errorState = RegistrationErrorState(
                    emailIdErrorState = ErrorState(
                        !validationResult.successful,
                        validationResult.errorMessage
                    )
                )
            )
        }
        return validationResult.successful
    }

    private suspend fun validatePassword(): Boolean {
        val passwordResult = validatePasswordUseCase.execute(registrationState.value.password)
        val validationResult = passwordResult.getOrDefault(ValidationResult())
        _registrationState.update {
            it.copy(
                errorState = RegistrationErrorState(
                    passwordErrorState = ErrorState(
                        !validationResult.successful,
                        validationResult.errorMessage
                    )
                )
            )
        }
        return validationResult.successful
    }



    /**
     * Function to validate inputs
     * Ideally it should be on domain layer (usecase)
     * @return true -> inputs are valid
     * @return false -> inputs are invalid
     */
    private suspend fun validateInputs(): Boolean {
        val passwordString = registrationState.value.password.trim()
        val confirmPasswordString = registrationState.value.confirmPassword.trim()



        return when {

            // Email empty
            !validateEmail() -> {
                _registrationState.update{
                    it.copy(
                        errorState = RegistrationErrorState(
                            emailIdErrorState = emailEmptyErrorState
                        )
                    )
                }
                false
            }


            //Password Empty
            !validatePassword() -> {
                _registrationState.update{
                    it.copy(
                        errorState = RegistrationErrorState(
                            passwordErrorState = passwordEmptyErrorState
                        )
                    )
                }
                false
            }


            // Password and Confirm Password are different
            passwordString != confirmPasswordString -> {
                _registrationState.update{
                    it.copy(
                        errorState = RegistrationErrorState(
                            confirmPasswordErrorState = passwordMismatchErrorState
                        )
                    )
                }
                false
            }

            // No errors
            else -> {
                // Set default error state
                _registrationState.update{
                    registrationState.value.copy(errorState = RegistrationErrorState())
                }

                true
            }
        }
    }

    private fun registration(){
        viewModelScope.launch{

            _uiState.update {
                RegistrationUiState.Loading
            }

            authRepository.signUpWithEmail(
                registrationState.value.name,
                registrationState.value.name,
                registrationState.value.email,
                registrationState.value.password,
            ).collectLatest {result ->
                if(result.isSuccess){
                    _uiState.update {
                        RegistrationUiState.RegistrationSuccess()
                    }
                }else{
                    _uiState.update {
                        RegistrationUiState.Error(result.exceptionOrNull())
                    }
                }
            }






        }
    }

    override fun initUIState(): RegistrationUiState {
        return RegistrationUiState.Empty
    }
}