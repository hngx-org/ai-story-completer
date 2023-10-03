package com.newton.storycompleter.ui.onboarding.signup

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class SignUpScreenViewModel(
) : ViewModel() {

    data class SignUpUiState(
        val username: String = "",
        val email: String = "",
        val password: String = ""
    )

    var uiState = mutableStateOf(SignUpUiState())
        private set
    private val username
        get() = uiState.value.username
    private val email
        get() = uiState.value.email
    private val password
        get() = uiState.value.password

    private val _message = MutableStateFlow("")
    val message: StateFlow<String> = _message

    fun postMessage(message: String) {
        // Do something with the message
        _message.value = message
    }

    fun onUsernameChange(newValue: String) {
        uiState.value = uiState.value.copy(username = newValue)
    }

    fun onEmailChange(newValue: String) {
        uiState.value = uiState.value.copy(email = newValue)
    }

    fun onPasswordChange(newValue: String) {
        uiState.value = uiState.value.copy(password = newValue)
    }


    private fun signUpWithEmailAndPassword(
        email: String, password: String,
        username: String
    ) = viewModelScope.launch {

    }


    fun onSignUpClick(openAndPopUp: (String, String) -> Unit) {


    }


}
