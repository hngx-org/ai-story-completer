package com.newton.storycompleter.ui.onboarding.signup

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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.newton.storycompleter.R
import com.newton.storycompleter.ui.onboarding.signin.Constants
import com.newton.storycompleter.ui.onboarding.signin.components.EmailField
import com.newton.storycompleter.ui.onboarding.signin.components.PasswordField
import com.newton.storycompleter.ui.onboarding.signin.components.SmallSpacer
import com.newton.storycompleter.ui.onboarding.signin.components.UsernameField


@Composable
@ExperimentalComposeUiApi
fun SignUpContent(
    padding: PaddingValues,
    navigateToSignInScreen: () -> Unit,
    onSignUp: (String,String) -> Unit,
    snackBar: () -> Unit,
    viewModel: SignUpScreenViewModel
) {
    val uiState by viewModel.uiState
    val context = LocalContext.current
    var progressDialog by remember { mutableStateOf(false) }

    val keyboard = LocalSoftwareKeyboardController.current

    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            contentScale = ContentScale.FillBounds,
            painter = painterResource(id = R.drawable.sign_up_background),
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
            //CircularProgress
            if (progressDialog) {
                CircularProgressIndicator()
            }

            Spacer(modifier = Modifier.height(80.dp))

            Spacer(modifier = Modifier.height(48.dp))
            Text(
                text = "Create Account",
// Geometria 24 | Medium
                style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight(500),
                    color = Color(0xFF000000),
                    textAlign = TextAlign.Center,
                )
            )

            Spacer(modifier = Modifier.height(48.dp))
            UsernameField(username = uiState.username, onUsernameValueChange = { newValue ->
                viewModel.onUsernameChange(newValue)
            })
            SmallSpacer()
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
                   // viewModel.showLoading()
                    viewModel.onSignUpClick(openAndPopUp = onSignUp)

                }
            ) {
                Text(
                    text = Constants.SIGN_UP,
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
                        navigateToSignInScreen.invoke()
                    },
                    text = Constants.ALREADY_USER,
                    fontSize = 15.sp
                )
            }
        }
    }
}
