package com.newton.storycompleter.ui.onboarding.signin

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newton.storycompleter.app.navigation.SignInScreen
import com.newton.storycompleter.app.navigation.StoriesListScreen
import com.newton.storycompleter.ui.auth.AuthService
import com.newton.storycompleter.ui.auth.Profile
import com.newton.storycompleter.ui.auth.Response
import com.shegs.hng_auth_library.repositories.DataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInScreenViewModel @Inject constructor(
    private  val authService: AuthService, private val dataStoreRepository : DataStoreRepository
): ViewModel()
{

    private var _profileState = MutableLiveData<Profile>()
    val profile: LiveData<Profile> = _profileState

    private var _loadingState = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loadingState


    private var _iserror = MutableLiveData(false)
    val iserror: LiveData<Boolean> = _iserror



    fun signInWithEmailAndPassword(
        email: String, password: String,
        openAndPopUp: (String, String) -> Unit
    ) {

        _loadingState.value = true

        viewModelScope.launch {
            val response =authService.SignIn(email, password)
            when (response) {
                is Response.Success -> {
                    _loadingState.value = false
                    dataStoreRepository.saveUserID(response.data.data.id)
                   openAndPopUp(StoriesListScreen.route,SignInScreen.route)
                }
                is Response.Failure -> {
                    _loadingState.value = false
                    _iserror.value =true
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


        signInWithEmailAndPassword(email, password, openAndPopUp)


    }


}

