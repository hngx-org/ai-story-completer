package com.newton.storycompleter.ui.onboarding.signin

import android.app.Application
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.newton.storycompleter.ui.onboarding.signin.components.FullScreenLoaderComponent
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun SignInFullScreen(
    navigateToSignUpScreen: () -> Unit,
    openAndPopUp: (String,String) -> Unit,
) {
    val context = LocalContext.current
    val viewModel: SignInScreenViewModel = hiltViewModel()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val loading by viewModel.loading.observeAsState(false)

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
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


    LaunchedEffect(viewModel.message) {
        if (viewModel.message.value.isNotEmpty()) {
         scope.launch   { snackbarHostState.showSnackbar(viewModel.message.value) }
            viewModel.postMessage("")
        }
    }

}