package com.newton.storycompleter.ui.onboarding.signup

import android.app.Application
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.newton.storycompleter.ui.onboarding.signin.components.FullScreenLoaderComponent
import kotlinx.coroutines.launch


@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SignUpFullScreen(
    openAndPopUp: (String,String) -> Unit,
    navigateBack: () -> Unit
) {

    val context = LocalContext.current
    val viewModel: SignUpScreenViewModel = hiltViewModel()
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
                SignUpContent(
                    padding = innerPadding,
                    onSignUp = openAndPopUp,
                    snackBar = { /*TODO*/ },
                    navigateToSignInScreen = navigateBack,
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


