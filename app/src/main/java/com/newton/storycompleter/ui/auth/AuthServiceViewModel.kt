package com.newton.storycompleter.ui.auth

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch


class AuthServiceViewModel(application: Application) : AndroidViewModel(application) {
    private var _profileState = MutableLiveData<Profile>()
    val profile: LiveData<Profile> = _profileState

    private var _loadingState = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loadingState
val authService =AuthService(application.applicationContext)
    init {
        checkSignedInUser(application.applicationContext)
    }


    private fun checkSignedInUser(applicationContext: Context) {
        _loadingState.value = true



        _loadingState.value = false
    }
    private fun SignIn(email: String, password: String) {
        _loadingState.value = true

        viewModelScope.launch {
            authService.SignIn(email,password)
        }
        _loadingState.value = false
    }
    private fun SignUp(email: String, password: String) {
        _loadingState.value = true

        viewModelScope.launch {
            authService.SignIn(email,password)
        }
        _loadingState.value = false
    }

    fun hideLoading() {
        _loadingState.value = false
    }

    fun showLoading() {
        _loadingState.value = true
    }
}

class AuthServiceViewModelFactory(
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(AuthServiceViewModel::class.java)) {
            return AuthServiceViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}