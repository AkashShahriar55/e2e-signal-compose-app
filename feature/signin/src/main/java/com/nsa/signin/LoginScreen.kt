package com.nsa.signin

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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
import com.nsa.signin.event.LoginUiEvent
import com.nsa.signin.event.SocialType
import com.nsa.signin.state.LoginUiState
import com.nsa.ui.component.EmailTextField
import com.nsa.ui.component.LoadingDialog
import com.nsa.ui.component.PasswordTextField
import com.nsa.ui.theme.AppTheme
import com.nsa.ui.theme.AppleIconDark
import com.nsa.ui.theme.AppleIconLight
import com.nsa.ui.theme.FacebookIcon
import com.nsa.ui.theme.GoogleIcon
import kotlinx.coroutines.launch


@Composable
fun SocialButtons(
    isLight:Boolean = true,
    onSocialLogin:(socialType: SocialType)->Unit
){

    val color = if(isLight) Color.Black else Color.White


    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        OutlinedIconButton(
            shape = RoundedCornerShape(50),
            onClick = { onSocialLogin(SocialType.Facebook) }
        ) {
            FacebookIcon()
        }
        OutlinedIconButton(
            shape = RoundedCornerShape(50),
            onClick = { onSocialLogin(SocialType.Google) }
        ) {
            GoogleIcon()
        }

        if(isLight){
            OutlinedIconButton(
                shape = RoundedCornerShape(50),
                onClick = { onSocialLogin(SocialType.Apple) }
            ) {
                AppleIconDark()
            }
        }else{
            OutlinedIconButton(
                shape = RoundedCornerShape(50),
                onClick = { onSocialLogin(SocialType.Apple) }
            ) {
                AppleIconLight()
            }
        }

    }
}


@Composable
fun OrDivider(){

    Row(
        modifier = Modifier.padding(20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Divider(
            modifier = Modifier
                .padding(0.dp, 0.dp, 16.dp, 0.dp)
                .height(1.dp)
                .weight(1f)
        )
        Text("OR")
        Divider(
            modifier = Modifier
                .padding(16.dp, 0.dp, 0.dp, 0.dp)
                .height(1.dp)
                .weight(1f)

        )
    }
}








@Composable
fun  LoginLandingScreen(
    loginViewModel: LoginViewModel = viewModel(),
    onNavigateToRegistration: () -> Unit,
    onNavigateToLoginWithEmail: () -> Unit,
    onNavigateToAuthenticatedRoute: () -> Unit
) {


    val uiState by loginViewModel.uiState.collectAsStateWithLifecycle()

    val snackbarHostState = remember { SnackbarHostState() }

    val scope = rememberCoroutineScope()

    when(uiState){
        is LoginUiState.Error -> {
            (uiState as LoginUiState.Error).throwable.message?.let {
                LaunchedEffect(1){
                    scope.launch {
                        snackbarHostState.showSnackbar(it)
                    }
                }
            }
        }
        LoginUiState.Loading -> LoadingDialog(message = "Loging in ...")
        else -> {}
    }




    if (uiState is LoginUiState.LoginSuccess) {
        /**
         * Navigate to Authenticated navigation route
         * once login is successful
         */
        LaunchedEffect(key1 = true) {
            onNavigateToAuthenticatedRoute.invoke()
        }
    } else {




        Scaffold(
            snackbarHost = {
                SnackbarHost(hostState = snackbarHostState)
            },
            containerColor = MaterialTheme.colorScheme.inverseSurface
        ) {



            Box{
                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(id = com.nsa.ui.R.drawable.top_back),
                    contentDescription = "background shade",
                    contentScale = ContentScale.FillBounds
                )
            }

            // Full Screen Content
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .navigationBarsPadding()
                    .imePadding()
                    .padding(20.dp)
                    .padding(it),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {


                Text(
                    modifier = Modifier.padding(20.dp),
                    text = "Logo Here"
                )


                Text(
                    style = MaterialTheme.typography.displayLarge,
                    text = stringResource(id = R.string.connect_friends),
                )

                Text(
                    modifier = Modifier
                        .padding(top = 30.dp, bottom = 30.dp)
                        .fillMaxWidth(),
                    style = TextStyle(fontSize = 16.sp, textAlign = TextAlign.Start),
                    text = stringResource(R.string.our_chat_app)
                )



                SocialButtons(false,
                    onSocialLogin = {
                        loginViewModel.onUiEvent(LoginUiEvent.SocialMediaLogin(it))
                    })

                OrDivider()




                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                        .height(48.dp),
                    shape = RoundedCornerShape(16.dp),
                    onClick = {
                        onNavigateToRegistration()
                    },
                    colors = ButtonDefaults.elevatedButtonColors()
                ) {
                    Text(
                        text = "Sign up with email",
                        style = MaterialTheme.typography.titleMedium
                    )
                }

                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Row(){
                        Text(
                            text = "Existing account?",
                        )
                        Text(
                            modifier = Modifier.clickable {
                                onNavigateToLoginWithEmail()
                            },
                            text = " Log in",
                            style =  MaterialTheme.typography.titleMedium
                        )
                    }

                }
            }
        }



    }

}





@Composable
fun EmailLoginScreen(
    loginViewModel: LoginViewModel = viewModel(),
    onNavigateToForgotPassword: () -> Unit,
    onNavigateToAuthenticatedRoute: () -> Unit,
    onNavigateBack: () -> Unit
) {

    val loginState by loginViewModel.loginState.collectAsStateWithLifecycle()

    val uiState by loginViewModel.uiState.collectAsStateWithLifecycle()

    val snackbarHostState = remember { SnackbarHostState() }

    val scope = rememberCoroutineScope()





    when(uiState){
        is LoginUiState.Error -> {
            (uiState as LoginUiState.Error).throwable.message?.let {
                LaunchedEffect(1){
                    scope.launch {
                        snackbarHostState.showSnackbar(it)
                    }
                }
            }
        }
        LoginUiState.Loading -> LoadingDialog(message = "Loging in ...")
        else -> {}
    }

    if (uiState is LoginUiState.LoginSuccess) {
        /**
         * Navigate to Authenticated navigation route
         * once login is successful
         */
        LaunchedEffect(key1 = true) {
            onNavigateToAuthenticatedRoute.invoke()
        }
    } else {

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
                    .padding(20.dp)
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
                        text = "Log in to Chatbox",
                        style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    )

                    Text(
                        modifier = Modifier
                            .padding(16.dp),
                        text = "Welcome back! Sign in using your social\n account or email to continue us",
                        style = TextStyle(
                            fontSize = 14.sp,
                            textAlign = TextAlign.Center,
                            color = Color(0xff797C7B)
                        )
                    )

                    SocialButtons{
                        loginViewModel.onUiEvent(LoginUiEvent.SocialMediaLogin(it))
                    }

                    OrDivider()

                    EmailTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = loginState.email,
                        onValueChange = {value ->
                            loginViewModel.onUiEvent(LoginUiEvent.EmailOrMobileChanged(value))
                        },
                        label = "Your email",
                        isError = loginState.errorState.emailOrMobileErrorState.hasError,
                        errorText = stringResource(id = loginState.errorState.emailOrMobileErrorState.errorMessage)
                    )

                    PasswordTextField(
                        modifier = Modifier.fillMaxWidth(),
                        onValueChange = { value-> loginViewModel.onUiEvent(LoginUiEvent.PasswordChanged(value)) },
                        value = loginState.password,
                        label = "Password",
                        isError = loginState.errorState.passwordErrorState.hasError,
                        errorText = stringResource(id = loginState.errorState.passwordErrorState.errorMessage)
                    )







                }



                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ){
                    Spacer(modifier = Modifier.weight(1f))
                    Button(
                        enabled = loginState.isSubmitButtonEnabled,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp)
                            .height(48.dp),
                        shape = RoundedCornerShape(16.dp),
                        onClick = {
                            loginViewModel.onUiEvent(LoginUiEvent.Submit)
                        }
                    ) {
                        Text(
                            text = "Log in",
                            style = MaterialTheme.typography.titleMedium
                        )
                    }

                    Text(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(16.dp)
                            .clickable {
                                onNavigateToForgotPassword()
                            },
                        text = "Forgot password?",
                        style = MaterialTheme.typography.titleMedium
                    )
                }




            }
        }

    }








}





@Preview(showBackground = true)
@Composable
fun PreviewLoginScreen() {
    AppTheme() {
        EmailLoginScreen(
            onNavigateToForgotPassword = {},
            onNavigateBack = {},
            onNavigateToAuthenticatedRoute = {}
        )
    }

}

@Preview(showBackground = true)
@Composable
fun PreviewLoginScreen2() {


    AppTheme {
        LoginLandingScreen(
            onNavigateToLoginWithEmail = {},
            onNavigateToRegistration = {},
            onNavigateToAuthenticatedRoute = {}
        )
    }
}