package com.newton.storycompleter.ui.onboarding.signin

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import com.newton.storycompleter.ui.onboarding.signin.components.FullScreenLoaderComponent


@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun SignInFullScreen(
    navigateToSignUpScreen: () -> Unit,
    openAndPopUp: (String,String) -> Unit,
) {

    val viewModel: SignInScreenViewModel = hiltViewModel()
    val loading by viewModel.loading.observeAsState(false)

    Scaffold(
        content = { innerPadding ->
            if (loading) {
                FullScreenLoaderComponent()
            } else {
                SignInContent(
                    padding = innerPadding,
                    navigateToForgotPasswordScreen = { /*TODO*/ },
                    navigateToSignUpScreen = navigateToSignUpScreen,
                    onSignIn = openAndPopUp,
                    viewModel = viewModel
                )
            }
        }
    )
}