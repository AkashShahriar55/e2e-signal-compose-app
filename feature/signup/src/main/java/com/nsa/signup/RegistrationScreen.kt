package com.nsa.signup

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nsa.signup.event.RegistrationUiEvent
import com.nsa.signup.state.RegistrationUiState
import com.nsa.ui.component.EmailTextField
import com.nsa.ui.component.LoadingDialog
import com.nsa.ui.component.NameTextField
import com.nsa.ui.component.PasswordTextField
import com.nsa.ui.theme.AppTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RegistrationScreen(
    registrationViewModel: RegistrationViewModel = viewModel(),
    onNavigateBack: () -> Unit,
    onNavigateToAuthenticatedRoute: () -> Unit
) {


    val scrollableState = rememberScrollState()
    val bringIntoViewRequester = remember { BringIntoViewRequester() }

    val registrationState by registrationViewModel.registrationState.collectAsStateWithLifecycle()

    val uiState by registrationViewModel.uiState.collectAsStateWithLifecycle()

    val snackbarHostState = remember { SnackbarHostState() }

    val scope = rememberCoroutineScope()

    when(uiState){
        is RegistrationUiState.Error -> {
            (uiState as RegistrationUiState.Error).throwable.message?.let {
                LaunchedEffect(true){
                    scope.launch {
                        snackbarHostState.showSnackbar(it)
                    }
                }
            }
        }
        RegistrationUiState.Loading -> LoadingDialog(message = "Registering ...")
        else -> {}
    }

    if (uiState is RegistrationUiState.RegistrationSuccess) {
        LaunchedEffect(key1 = true) {
            onNavigateToAuthenticatedRoute.invoke()
        }
    } else {
        // Full Screen Content
        Scaffold(
            snackbarHost = {
                SnackbarHost(hostState = snackbarHostState)
            }
        ){ it ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .navigationBarsPadding()
                    .imePadding()
                    .padding(it)
                    .padding(20.dp, 30.dp)
                    .verticalScroll(scrollableState)
                ,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    modifier = Modifier
                        .padding(0.dp, 30.dp)
                        .align(Alignment.Start)
                        .clickable {
                            onNavigateBack()
                        },
                    painter = painterResource(id = com.nsa.ui.R.drawable.back), // Replace "your_vector_asset" with the actual name of your vector asset resource
                    contentDescription = "back Icon" // Provide a description for accessibility
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally

                ) {

                    Text(
                        modifier = Modifier,
                        text = "Sign up with Email",
                        style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    )

                    Text(
                        modifier = Modifier
                            .padding(16.dp,16.dp,16.dp,60.dp),
                        text = stringResource(R.string.get_chatting_with_friends_and_family_today_by_signing_up_for_our_chat_app),
                        style = TextStyle(
                            fontSize = 14.sp,
                            textAlign = TextAlign.Center,
                            color = Color(0xff797C7B)
                        )
                    )


                    NameTextField(
                        modifier = Modifier.fillMaxWidth()
                            .onFocusEvent {
                                focusState ->
                                if(focusState.isFocused){
                                    scope.launch{
                                        bringIntoViewRequester.bringIntoView()
                                    }
                                }
                            },

                        onValueChange = { value->
                            registrationViewModel.onUiEvent(RegistrationUiEvent.NameChanged(value))
                        },
                        value = registrationState.name,
                        label = "Your name",
                        isError = registrationState.errorState.nameErrorState.hasError,
                        errorText = stringResource(id = registrationState.errorState.nameErrorState.errorMessage)
                    )


                    EmailTextField(
                        modifier = Modifier.fillMaxWidth()
                            .onFocusEvent {
                                    focusState ->
                                if(focusState.isFocused){
                                    scope.launch{
                                        bringIntoViewRequester.bringIntoView()
                                    }
                                }
                            },
                        onValueChange = { value->
                            registrationViewModel.onUiEvent(RegistrationUiEvent.EmailChanged(value))
                        },
                        value = registrationState.email,
                        label = "Your email",
                        isError = registrationState.errorState.emailIdErrorState.hasError,
                        errorText = stringResource(id = registrationState.errorState.emailIdErrorState.errorMessage)
                    )

                    PasswordTextField(
                        modifier = Modifier.fillMaxWidth()
                            .onFocusEvent {
                                    focusState ->
                                if(focusState.isFocused){
                                    scope.launch{
                                        bringIntoViewRequester.bringIntoView()
                                    }
                                }
                            },
                        onValueChange = { value->
                            registrationViewModel.onUiEvent(RegistrationUiEvent.PasswordChanged(value))
                        },
                        value = registrationState.password,
                        label = "Password",
                        isError = registrationState.errorState.passwordErrorState.hasError,
                        errorText = stringResource(id = registrationState.errorState.passwordErrorState.errorMessage)
                    )

                    PasswordTextField(
                        modifier = Modifier.fillMaxWidth()
                            .onFocusEvent {
                                    focusState ->
                                if(focusState.isFocused){
                                    scope.launch{
                                        bringIntoViewRequester.bringIntoView()
                                    }
                                }
                            },
                        onValueChange = { value->
                            registrationViewModel.onUiEvent(RegistrationUiEvent.ConfirmPasswordChanged(value))
                        },
                        value = registrationState.confirmPassword,
                        label = "Confirm Password",
                        isError = registrationState.errorState.confirmPasswordErrorState.hasError,
                        errorText = stringResource(id = registrationState.errorState.confirmPasswordErrorState.errorMessage)
                    )





                    Spacer(modifier = Modifier.weight(1f))
                    Button(
                        enabled = false,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp)
                            .height(48.dp),
                        shape = RoundedCornerShape(16.dp),
                        onClick = {
                            registrationViewModel.onUiEvent(RegistrationUiEvent.Submit)
                        }
                    ) {
                        Text(
                            text = "Create an account",
                            style = MaterialTheme.typography.titleMedium
                        )
                    }


                }



            }
        }

    }

}

@Preview(showBackground = true)
@Composable
fun PreviewRegistrationScreen() {
    AppTheme {
        RegistrationScreen(onNavigateBack = {}, onNavigateToAuthenticatedRoute = {})
    }
}