package com.nsa.signin

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Scale
import com.guru.fontawesomecomposelib.FaIcon
import com.guru.fontawesomecomposelib.FaIcons
import com.jodhpurtechies.composelogin.ui.screens.unauthenticated.login.state.LoginUiEvent
import com.nsa.ui.component.AppTopBar
import com.nsa.ui.component.EmailTextField
import com.nsa.ui.component.InputEmail
import com.nsa.ui.component.MediumTitleText
import com.nsa.ui.component.PasswordTextField
import com.nsa.ui.component.RoundedCornerButton
import com.nsa.ui.component.TitleText
import com.nsa.ui.theme.AppTheme
import com.nsa.ui.theme.AppleIconDark
import com.nsa.ui.theme.AppleIconLight
import com.nsa.ui.theme.BackIcon
import com.nsa.ui.theme.FacebookIcon
import com.nsa.ui.theme.GoogleIcon
import com.nsa.ui.theme.md_theme_light_scrim
import com.nsa.ui.theme.white_button_container_color
import com.nsa.ui.theme.white_button_content_color

@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel = viewModel(),
    onNavigateToRegistration: () -> Unit,
    onNavigateToForgotPassword: () -> Unit,
    onNavigateToAuthenticatedRoute: () -> Unit
) {

    val loginState by remember {
        loginViewModel.loginState
    }

    if (loginState.isLoginSuccessful) {
        /**
         * Navigate to Authenticated navigation route
         * once login is successful
         */
        LaunchedEffect(key1 = true) {
            onNavigateToAuthenticatedRoute.invoke()
        }
    } else {

        // Full Screen Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding()
                .imePadding()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Main card Content for Login
            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(AppTheme.dimens.paddingLarge)
            ) {
                Column(
                    modifier = Modifier
                        .padding(horizontal = AppTheme.dimens.paddingLarge)
                        .padding(bottom = AppTheme.dimens.paddingExtraLarge)
                ) {

                    // Heading Jetpack Compose
                    MediumTitleText(
                        modifier = Modifier
                            .padding(top = AppTheme.dimens.paddingLarge)
                            .fillMaxWidth(),
                        text = stringResource(id = R.string.jetpack_compose),
                        textAlign = TextAlign.Center
                    )

                    // Login Logo
                    AsyncImage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(128.dp)
                            .padding(top = AppTheme.dimens.paddingSmall),
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(data = R.drawable.jetpack_compose_logo)
                            .crossfade(enable = true)
                            .scale(Scale.FILL)
                            .build(),
                        contentDescription = stringResource(id = R.string.login_heading_text)
                    )

                    // Heading Login
                    TitleText(
                        modifier = Modifier.padding(top = AppTheme.dimens.paddingLarge),
                        text = stringResource(id = R.string.login_heading_text)
                    )

                    // Login Inputs Composable
                    LoginInputs(
                        loginState = loginState,
                        onEmailOrMobileChange = { inputString ->
                            loginViewModel.onUiEvent(
                                loginUiEvent = LoginUiEvent.EmailOrMobileChanged(
                                    inputString
                                )
                            )
                        },
                        onPasswordChange = { inputString ->
                            loginViewModel.onUiEvent(
                                loginUiEvent = LoginUiEvent.PasswordChanged(
                                    inputString
                                )
                            )
                        },
                        onSubmit = {
                            loginViewModel.onUiEvent(loginUiEvent = LoginUiEvent.Submit)
                        },
                        onForgotPasswordClick = onNavigateToForgotPassword
                    )

                }
            }

            // Register Section
            Row(
                modifier = Modifier.padding(AppTheme.dimens.paddingNormal),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Don't have an account?
                Text(text = stringResource(id = R.string.do_not_have_account))

                //Register
                Text(
                    modifier = Modifier
                        .padding(start = AppTheme.dimens.paddingExtraSmall)
                        .clickable {
                            onNavigateToRegistration.invoke()
                        },
                    text = stringResource(id = R.string.register),
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }

    }

}



@Composable
fun SocialButtons(
    isLight:Boolean = true
){

    val color = if(isLight) Color.Black else Color.White


    Row() {
        FacebookIcon(
            modifier = Modifier
                .padding(10.dp)
                .border(width = 1.dp, color = color, shape = RoundedCornerShape(50))
                .padding(12.dp)
        )
        GoogleIcon(
            modifier = Modifier
                .padding(10.dp)
                .border(width = 1.dp, color = color, shape = RoundedCornerShape(50))
                .padding(12.dp)
        )
        if(isLight){
            AppleIconDark(
                modifier = Modifier
                    .padding(10.dp)
                    .border(width = 1.dp, color = color, shape = RoundedCornerShape(50))
                    .padding(12.dp)
            )
        }else{
            AppleIconLight(
                modifier = Modifier
                    .padding(10.dp)
                    .border(width = 1.dp, color = color, shape = RoundedCornerShape(50))
                    .padding(12.dp)
            )
        }

    }
}


@Composable
fun OrDivider(
    textColor:Color
){

    Row(
        modifier = Modifier.padding(20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Divider(
            modifier = Modifier
                .padding(0.dp, 0.dp, 16.dp, 0.dp)
                .height(1.dp)
                .background(Color(0xffCDD1D0))
                .weight(1f)
        )
        Text("OR", color = textColor)
        Divider(
            modifier = Modifier
                .padding(16.dp, 0.dp, 0.dp, 0.dp)
                .height(1.dp)
                .background(Color(0xffCDD1D0))
                .weight(1f)

        )
    }
}






@Composable
fun LoginLandingScreen(
    loginViewModel: LoginViewModel = viewModel(),
    onNavigateToRegistration: () -> Unit,
    onNavigateToLoginWithEmail: () -> Unit,
    onNavigateToAuthenticatedRoute: () -> Unit
) {

    val loginState by remember {
        loginViewModel.loginState
    }

    if (loginState.isLoginSuccessful) {
        /**
         * Navigate to Authenticated navigation route
         * once login is successful
         */
        LaunchedEffect(key1 = true) {
            onNavigateToAuthenticatedRoute.invoke()
        }
    } else {

        // Full Screen Content
        Column(
            modifier = Modifier
                .background(Color(0xff1A1A1A))
                .fillMaxSize()
                .navigationBarsPadding()
                .imePadding()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                modifier = Modifier.padding(top = 30.dp),
                color = Color.White,
                style = TextStyle(fontSize = 68.sp),
                text = "Connect friends"
            )

            Text(
                color = Color.White,
                style = TextStyle(fontSize = 68.sp,fontWeight = FontWeight.Bold),
                text = "easily & quickly"
            )

            Text(
                modifier = Modifier
                    .padding(top = 30.dp, bottom = 30.dp)
                    .fillMaxWidth(),
                color = Color(0xFFB9C1BE),
                style = TextStyle(fontSize = 16.sp, textAlign = TextAlign.Start),
                text = "Our chat app is the perfect way to stay connected with friends and family."
            )



            SocialButtons(false)

            OrDivider(Color(0xffD6E4E0))





            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
                    .height(48.dp),
                shape = RoundedCornerShape(16.dp),
                onClick = {
                    onNavigateToRegistration()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = white_button_container_color,
                    contentColor = white_button_content_color,

                )
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
                        color = Color.White
                    )
                    Text(
                        modifier = Modifier.clickable {
                            onNavigateToLoginWithEmail()
                        },
                        text = " Log in",
                        style =  MaterialTheme.typography.titleMedium,
                        color = Color.White,
                    )
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


    Column(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .imePadding()
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

            SocialButtons()

            OrDivider(Color(0xff797C7B))

            EmailTextField(
                modifier = Modifier.fillMaxWidth(),
                onValueChange = { value-> },
                value = "",
                label = "Your email"
            )

            PasswordTextField(
                modifier = Modifier.fillMaxWidth(),
                onValueChange = { value-> },
                value = "",
                label = "Password"
            )







        }



        Column(
            modifier = Modifier
                .fillMaxSize()
        ){
            Spacer(modifier = Modifier.weight(1f))
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
                    .height(48.dp),
                shape = RoundedCornerShape(16.dp),
                onClick = {
                    onNavigateToAuthenticatedRoute()
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





@Preview(showBackground = true)
@Composable
fun PreviewLoginScreen() {
    AppTheme {
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