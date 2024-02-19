package com.nsa.signin

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.nsa.domain.model.ValidationResult
import com.nsa.signin.event.LoginUiEvent
import com.nsa.signin.event.SocialType
import com.nsa.signin.state.LoginErrorState
import com.nsa.signin.state.LoginState
import com.nsa.signin.state.LoginUiState
import com.nsa.signin.state.emailOrMobileEmptyErrorState
import com.nsa.signin.state.passwordEmptyErrorState
import com.nsa.ui.state.ErrorState
import com.nsa.ui.vm.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import usecases.ValidateEmailUseCase
import usecases.ValidatePasswordUseCase

/**
 * ViewModel for Login Screen
 */
class LoginViewModel : BaseViewModel<LoginUiState, LoginUiEvent>() {


    private val validateEmailUseCase = ValidateEmailUseCase()
    private val validatePasswordUseCase = ValidatePasswordUseCase()


    protected val _loginState: MutableStateFlow<LoginState> = MutableStateFlow(LoginState())
    val loginState: StateFlow<LoginState> = _loginState.asStateFlow()













    /**
     * Function called on any login event [LoginUiEvent]
     */
    override fun onUiEvent(event: LoginUiEvent) {
        when (event) {

            is LoginUiEvent.EmailOrMobileChanged -> {
                Log.d("check_input", "onUiEvent: ${event.inputValue} ${loginState.value}")
                _loginState.update {
                    it.copy(
                        email = event.inputValue,
                        errorState = loginState.value.errorState.copy(
                            emailOrMobileErrorState = if (event.inputValue.trim().isNotEmpty())
                                ErrorState()
                            else
                                emailOrMobileEmptyErrorState
                        ),
                        isSubmitButtonEnabled = event.inputValue.trim().isNotEmpty() && it.password.trim().isNotEmpty()

                    )
                }
            }

            // Password changed
            is LoginUiEvent.PasswordChanged -> {
                _loginState.update {
                    it.copy(
                        password = event.inputValue,
                        errorState = loginState.value.errorState.copy(
                            passwordErrorState = if (event.inputValue.trim().isNotEmpty())
                                ErrorState()
                            else
                                passwordEmptyErrorState
                        ),
                        isSubmitButtonEnabled = event.inputValue.trim().isNotEmpty() && it.email.trim().isNotEmpty()
                    )
                }
            }

            // Submit Login
            is LoginUiEvent.Submit -> {
                viewModelScope.launch {
                    val inputsValidated = validateInputs()
                    if (inputsValidated) {
                        // TODO Trigger login in authentication flow
                        loginWithEmail()

                    }
                }

            }

            is LoginUiEvent.SocialMediaLogin -> {
                loginWithSocial(event.socialType)
            }
        }
    }


    private suspend fun validateEmail(): Boolean {
        val emailResult = validateEmailUseCase.execute(loginState.value.email)
        val validationResult = emailResult.getOrDefault(ValidationResult())

        _loginState.update {
            it.copy(
                errorState = LoginErrorState(
                    emailOrMobileErrorState = ErrorState(
                        !validationResult.successful,
                        validationResult.errorMessage
                    )
                )
            )
        }
        return validationResult.successful
    }

    private suspend fun validatePassword(): Boolean {
        val passwordResult = validatePasswordUseCase.execute(loginState.value.password)
        val validationResult = passwordResult.getOrDefault(ValidationResult())
        _loginState.update {
            loginState.value.copy(
                errorState = LoginErrorState(
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
        if(validateEmail() && validatePassword()){
            _loginState.update {
                it.copy(errorState = LoginErrorState())
            }
            return true
        }
        return false
    }



    private fun loginWithSocial(socialType: SocialType){
        viewModelScope.launch{

            _uiState.update {
                LoginUiState.Loading
            }
            delay(2000)



            if(socialType == SocialType.Apple){
                _uiState.update {
                    LoginUiState.Error(Throwable(message = "Sorry can't login"))
                }
            }else{
                _uiState.update {
                    LoginUiState.LoginSuccess()
                }
            }
        }
    }


    private fun loginWithEmail(){
        viewModelScope.launch{

            _uiState.update {
                LoginUiState.Loading
            }
            delay(2000)


            if(loginState.value.email == "sudo@gmail.com" && loginState.value.password == "root1234"){
                _uiState.update {
                    LoginUiState.LoginSuccess()
                }
            }else{
                _uiState.update {
                    LoginUiState.Error(Throwable(message = "Sorry can't login. Please check your email or password"))
                }
            }



        }
    }

    override fun initUIState(): LoginUiState {
        return LoginUiState.Empty
    }

}