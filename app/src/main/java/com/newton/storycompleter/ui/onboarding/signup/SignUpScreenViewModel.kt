package com.newton.storycompleter.ui.onboarding.signup

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newton.storycompleter.app.navigation.SignInScreen
import com.newton.storycompleter.app.navigation.SignUpScreen
import com.newton.storycompleter.app.navigation.StoriesListScreen
import com.newton.storycompleter.ui.auth.AuthService
import com.newton.storycompleter.ui.auth.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SignUpScreenViewModel @Inject constructor(
    private  val authService: AuthService
): ViewModel()
{

    private var _loadingState = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loadingState


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
                    _loadingState.value = false
                    openAndPopUp(SignInScreen.route, SignUpScreen.route)

                }

                is Response.Failure -> {
                    _loadingState.value = false
                    openAndPopUp(StoriesListScreen.route, SignUpScreen.route)
                }
            }
        }

    }

    fun hideLoading() {
        _loadingState.value = false
    }

    fun showLoading() {
        _loadingState.value = true
    }


    fun onSignUpClick(openAndPopUp: (String, String) -> Unit) {

        /*  if (!email.isValidEmail()) {
                   postMessage(R.string.email_error.toString())
                   return
               }

               if (!password.isValidPassword()) {
                  postMessage(R.string.password_error.toString())
                   return
               }*/


        signUpWithEmailAndPassword(email, password, username,openAndPopUp)
    }


}

