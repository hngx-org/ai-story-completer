package com.newton.storycompleter.ui.onboarding.signin

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.newton.storycompleter.R
import com.newton.storycompleter.ui.onboarding.signin.Constants.FORGOT_PASSWORD
import com.newton.storycompleter.ui.onboarding.signin.Constants.NO_ACCOUNT
import com.newton.storycompleter.ui.onboarding.signin.Constants.SIGN_IN
import com.newton.storycompleter.ui.onboarding.signin.Constants.VERTICAL_DIVIDER
import com.newton.storycompleter.ui.onboarding.signin.components.EmailField
import com.newton.storycompleter.ui.onboarding.signin.components.PasswordField
import com.newton.storycompleter.ui.onboarding.signin.components.SmallSpacer


@Composable
@ExperimentalComposeUiApi
fun SignInContent(
    padding: PaddingValues,
    navigateToForgotPasswordScreen: () -> Unit,
    navigateToSignUpScreen: () -> Unit,
    onSignIn: () -> Unit,
    viewModel: SignInScreenViewModel
) {

    val uiState by viewModel.uiState
    val keyboard = LocalSoftwareKeyboardController.current

    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            contentScale = ContentScale.FillBounds,
            painter = painterResource(id = R.drawable.sign_in_background),
            contentDescription = "",
            modifier = Modifier.matchParentSize()
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            /*verticalArrangement = Arrangement.Center*/
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(80.dp))
            Spacer(modifier = Modifier.height(48.dp))
            Text(
                text = "Welcome Back!",
// Geometria 24 | Medium
                style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight(500),
                    color = Color(0xFF000000),
                    textAlign = TextAlign.Center,
                )
            )

            Spacer(modifier = Modifier.height(48.dp))
            EmailField(
                email = uiState.email,
                onEmailValueChange = { newValue ->
                    viewModel.onEmailChange(newValue)
                }
            )
            SmallSpacer()
            PasswordField(
                password = uiState.password,
                onPasswordValueChange = { newValue ->
                    viewModel.onPasswordChange(newValue)
                }
            )
            SmallSpacer()
            SmallSpacer()
            Button(modifier = Modifier
                .width(236.dp)
                .height(45.dp),
                onClick = {
                    keyboard?.hide()
               onSignIn()
                }
            ) {
                Text(
                    text = SIGN_IN,
                    fontSize = 15.sp
                )
            }
            Spacer(modifier = Modifier.height(64.dp))
            Row(horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.google_logo),
                    contentDescription = "google logo",

                    )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    painter = painterResource(id = R.drawable.twitter_logo),
                    contentDescription = "google logo",

                    )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row {
                Text(
                    modifier = Modifier.clickable {
                        navigateToForgotPasswordScreen()
                    },
                    text = FORGOT_PASSWORD,
                    fontSize = 15.sp
                )
                Text(
                    modifier = Modifier.padding(start = 4.dp, end = 4.dp),
                    text = VERTICAL_DIVIDER,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    modifier = Modifier.clickable {
                        navigateToSignUpScreen.invoke()
                    },
                    text = NO_ACCOUNT,
                    fontSize = 15.sp,
                    textDecoration = TextDecoration.Underline
                )
            }
        }
    }
}
