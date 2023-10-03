package com.newton.storycompleter.ui.onboarding.signin

import android.app.Application
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.newton.storycompleter.ui.onboarding.signin.components.FullScreenLoaderComponent


@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun SignInFullScreen(
    navigateToSignUpScreen: () -> Unit,
    openAndPopUp: (String,String) -> Unit,
) {
    val context = LocalContext.current
    val viewModel: SignInScreenViewModel = viewModel(
        factory = SignInScreenViewModelFactory(context.applicationContext as Application)
    )
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val state = viewModel.loading.observeAsState()
    val isLoading = state.value

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        content = { innerPadding ->
            if (isLoading == true) {
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


    LaunchedEffect(viewModel.message) {
        if (viewModel.message.value.isNotEmpty()) {
            snackbarHostState.showSnackbar(viewModel.message.value)
            viewModel.postMessage("")
        }
    }
}