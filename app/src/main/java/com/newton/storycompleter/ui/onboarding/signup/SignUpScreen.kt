package com.newton.storycompleter.ui.onboarding.signup

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import com.newton.storycompleter.ui.onboarding.signin.components.FullScreenLoaderComponent


@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SignUpFullScreen(
    openAndPopUp: (String,String) -> Unit,
    navigateBack: () -> Unit
) {

    val viewModel: SignUpScreenViewModel = hiltViewModel()
    val state = viewModel.loading.observeAsState()
    val isLoading = state.value
    Scaffold(
        content = { innerPadding ->
           if (isLoading == true) {
               FullScreenLoaderComponent()
            } else {
                SignUpContent(
                    padding = innerPadding,
                    onSignUp = openAndPopUp,
                    navigateToSignInScreen = navigateBack,
                    viewModel = viewModel
                )
            }
}
    )
}


