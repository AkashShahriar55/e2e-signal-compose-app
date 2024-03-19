package com.nsa.signin

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.registerForActivityResult
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.BuildConfig
import com.nsa.data.repository.AuthRepository
import com.nsa.domain.model.ValidationResult
import com.nsa.domain.model.params.GoogleAuthParam
import com.nsa.signin.event.LoginUiEvent
import com.nsa.signin.event.SocialType
import com.nsa.signin.state.LoginErrorState
import com.nsa.signin.state.LoginState
import com.nsa.signin.state.LoginUiState
import com.nsa.signin.state.emailOrMobileEmptyErrorState
import com.nsa.signin.state.passwordEmptyErrorState
import com.nsa.ui.state.ErrorState
import com.nsa.ui.vm.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import usecases.GoogleAuthUseCase
import usecases.ValidateEmailUseCase
import usecases.ValidatePasswordUseCase
import javax.inject.Inject

/**
 * ViewModel for Login Screen
 */
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val googleAuthUseCase: GoogleAuthUseCase
) : BaseViewModel<LoginUiState, LoginUiEvent>() {


    private val validateEmailUseCase = ValidateEmailUseCase()
    private val validatePasswordUseCase = ValidatePasswordUseCase()

    protected val _loginState: MutableStateFlow<LoginState> = MutableStateFlow(LoginState())
    val loginState: StateFlow<LoginState> = _loginState.asStateFlow()






    init {
        viewModelScope.launch {
            authRepository.isLoggedIn.collectLatest { loggedIn->
                if(loggedIn){
                    _uiState.update {
                        LoginUiState.LoginSuccess()
                    }
                }
            }
        }
    }




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
                loginWithSocial(event.socialType,event.context,event.registry)
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



    private fun loginWithSocial(socialType: SocialType,context: Context,registry: ActivityResultRegistry){


        when(socialType){
            SocialType.Facebook -> {

            }
            SocialType.Google -> {
                viewModelScope.launch {
                    googleAuthUseCase.execute(GoogleAuthParam(
                        context,
                        registry
                    ))
                }

            }
            SocialType.Apple -> {

            }
        }

//        viewModelScope.launch{
//
//            _uiState.update {
//                LoginUiState.Loading
//            }
//            delay(2000)
//
//
//
//            if(socialType == SocialType.Apple){
//                _uiState.update {
//                    LoginUiState.Error(Throwable(message = "Sorry can't login"))
//                }
//            }else{
//                _uiState.update {
//                    LoginUiState.LoginSuccess()
//                }
//            }
//        }
    }



    private fun getGoogleSignInRequest():BeginSignInRequest{
        return BeginSignInRequest.builder()
            .setPasswordRequestOptions(
                BeginSignInRequest.PasswordRequestOptions.builder()
                .setSupported(true)
                .build())
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                .setSupported(true)
                // Your server's client ID, not your Android client ID.
                .setServerClientId("854196966349-t30ds85rsoruflrp5etsavruncjas3fo.apps.googleusercontent.com")
                // Only show accounts previously used to sign in.
                .setFilterByAuthorizedAccounts(true)
                .build())
            // Automatically sign in when exactly one credential is retrieved.
            .setAutoSelectEnabled(true)
            .build();
    }


    private fun loginWithEmail(){
        viewModelScope.launch{

            _uiState.update {
                LoginUiState.Loading
            }

            authRepository.loginWithEmail(
                loginState.value.email,
                loginState.value.password
            ).collectLatest {result ->
                result.takeIf { it.isSuccess }?.let {
                    _uiState.update {
                        LoginUiState.LoginSuccess()
                    }
                } ?: run {
                    _uiState.update {
                        LoginUiState.Error(result.exceptionOrNull())
                    }
                }
            }







        }
    }

    override fun initUIState(): LoginUiState {
        return LoginUiState.Empty
    }

}