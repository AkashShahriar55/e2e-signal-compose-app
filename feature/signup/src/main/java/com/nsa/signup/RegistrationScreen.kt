package com.nsa.signup

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nsa.signup.state.RegistrationUiEvent
import com.nsa.ui.R
import com.nsa.ui.component.EmailTextField
import com.nsa.ui.component.NameTextField
import com.nsa.ui.component.PasswordTextField
import com.nsa.ui.component.SmallClickableWithIconAndText
import com.nsa.ui.component.TitleText
import com.nsa.ui.theme.AppTheme

@Composable
fun RegistrationScreen(
    registrationViewModel: RegistrationViewModel = viewModel(),
    onNavigateBack: () -> Unit,
    onNavigateToAuthenticatedRoute: () -> Unit
) {

    val registrationState by remember {
        registrationViewModel.registrationState
    }

    if (registrationState.isRegistrationSuccessful) {
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
                .padding(20.dp,30.dp)
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
                painter = painterResource(id = R.drawable.back), // Replace "your_vector_asset" with the actual name of your vector asset resource
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
                    text = "Get chatting with friends and family today by \nsigning up for our chat app!",
                    style = TextStyle(
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center,
                        color = Color(0xff797C7B)
                    )
                )


                NameTextField(
                    modifier = Modifier.fillMaxWidth(),
                    onValueChange = { value-> },
                    value = "",
                    label = "Your name"
                )


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

                PasswordTextField(
                    modifier = Modifier.fillMaxWidth(),
                    onValueChange = { value-> },
                    value = "",
                    label = "Confirm Password"
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
                        text = "Create an account",
                        style = MaterialTheme.typography.titleMedium
                    )
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