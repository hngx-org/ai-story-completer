package com.newton.storycompleter.ui.onboarding.signup

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.newton.storycompleter.R
import com.newton.storycompleter.app.navigation.SignInScreen
import com.newton.storycompleter.app.navigation.SignUpScreen
import com.newton.storycompleter.ui.auth.AuthService
import com.newton.storycompleter.ui.auth.Response
import com.newton.storycompleter.ui.onboarding.signin.components.isValidEmail
import com.newton.storycompleter.ui.onboarding.signin.components.isValidPassword
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class SignUpScreenViewModel(
    application: Application
) : AndroidViewModel(application) {

    private var _loadingState = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loadingState
    val authService = AuthService(application.applicationContext)

    data class SignUpUiState(
        val username: String = "",
        val email: String = "",
        val password: String = ""
    )

    private val email
        get() = uiState.value.email
    private val password
        get() = uiState.value.password
    private val username
        get() = uiState.value.username
    var uiState = mutableStateOf(SignUpUiState())
        private set


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
        username: String,  openAndPopUp: (String, String) -> Unit
    ) {
        _loadingState.value = true
        viewModelScope.launch {

            val response = authService.SignUp(username, email, password)
            when (response) {
                is Response.Success -> {
                    openAndPopUp(SignInScreen.route, SignUpScreen.route)
                }

                is Response.Failure -> {
                    postMessage(response.e)
                }
            }
        }
        _loadingState.value = false
    }

    fun hideLoading() {
        _loadingState.value = false
    }

    fun showLoading() {
        _loadingState.value = true
    }


    fun onSignUpClick(openAndPopUp: (String, String) -> Unit) {

          if (!email.isValidEmail()) {
                   postMessage(R.string.email_error.toString())
                   return
               }

               if (!password.isValidPassword()) {
                  postMessage(R.string.password_error.toString())
                   return
               }


        signUpWithEmailAndPassword(email, password, username,openAndPopUp)
    }


}

class SignUpScreenViewModelFactory(
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(SignUpScreenViewModel::class.java)) {
            return SignUpScreenViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
