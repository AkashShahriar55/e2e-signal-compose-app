package com.nsa.signup

import androidx.lifecycle.viewModelScope
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
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import usecases.ValidateEmailUseCase
import usecases.ValidatePasswordUseCase

class RegistrationViewModel  : BaseViewModel<RegistrationUiState, RegistrationUiEvent>() {


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

                        )
                    )
                }
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

                        )
                    )
                }
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

                        )
                    )
                }
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
                        )
                    )
                }
            }


            // Submit Registration event
            is RegistrationUiEvent.Submit -> {
                viewModelScope.launch {
                    val inputsValidated = validateInputs()
                    if (inputsValidated) {
                        // TODO Trigger registration in authentication flow
                        _registrationState.update{
                            registrationState.value.copy(isRegistrationSuccessful = true)
                        }

                    }
                }

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
            delay(2000)


            _uiState.update {
                RegistrationUiState.RegistrationSuccess()
            }



        }
    }

    override fun initUIState(): RegistrationUiState {
        return RegistrationUiState.Empty
    }
}