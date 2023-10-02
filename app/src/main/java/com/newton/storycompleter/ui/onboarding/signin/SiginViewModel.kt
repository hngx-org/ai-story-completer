package com.newton.storycompleter.ui.onboarding.signin

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newton.storycompleter.R
import com.newton.storycompleter.ui.onboarding.signin.components.isValidEmail
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SignInScreenViewModel(
) : ViewModel() {


    private val _message = MutableStateFlow("")
    val message: StateFlow<String> = _message

    fun postMessage(message: String) {
        // Do something with the message
        _message.value = message
    }


    fun signInWithEmailAndPassword(
        email: String, password: String,
        openAndPopUp: (String, String) -> Unit
    ) = viewModelScope.launch {


    }


    var uiState = mutableStateOf(LoginUiState())
        private set

    private val email
        get() = uiState.value.email
    private val password
        get() = uiState.value.password

    fun onEmailChange(newValue: String) {
        uiState.value = uiState.value.copy(email = newValue)
    }

    fun onPasswordChange(newValue: String) {
        uiState.value = uiState.value.copy(password = newValue)
    }

    fun onSignInClick(openAndPopUp: (String, String) -> Unit) {
        if (!email.isValidEmail()) {
            // SnackbarManager.showMessage(AppText.email_error)
            postMessage(R.string.email_error.toString())
            return
        }

        if (password.isBlank()) {
            //SnackbarManager.showMessage(AppText.empty_password_error)
            postMessage(R.string.empty_password_error.toString())
            return
        }

        signInWithEmailAndPassword(email, password, openAndPopUp)


    }

}