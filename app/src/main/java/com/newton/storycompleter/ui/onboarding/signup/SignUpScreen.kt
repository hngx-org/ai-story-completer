package com.newton.storycompleter.ui.onboarding.signup

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalContext


@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SignUpFullScreen(
    openAndPopUp: () -> Unit,
    navigateBack: () -> Unit
) {
    val context = LocalContext.current
    val viewModel: SignUpScreenViewModel = SignUpScreenViewModel()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        content = { innerPadding ->

            SignUpContent(
                padding = innerPadding,
                onSignUp = openAndPopUp,
                snackBar = { /*TODO*/ },
                navigateToSignInScreen = navigateBack,
                viewModel = viewModel
            )
        }
    )
}


