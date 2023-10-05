package com.newton.storycompleter.ui.profile

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newton.storycompleter.app.navigation.ProfileScreen
import com.newton.storycompleter.app.navigation.SignInScreen
import com.newton.storycompleter.app.navigation.StoriesListScreen
import com.newton.storycompleter.ui.auth.AuthService
import com.newton.storycompleter.ui.auth.Profile
import com.newton.storycompleter.ui.auth.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private  val authService: AuthService
): ViewModel()
{
    private var _profileState = MutableLiveData<Profile>()
    val profile: LiveData<Profile> = _profileState

    private var _loadingState = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loadingState
    private val _message = MutableStateFlow("")
    val message: StateFlow<String> = _message

    fun postMessage(message: String) {
        // Do something with the message
        _message.value = message
    }
    init {
        viewModelScope.launch {
           val response = authService.userProfile()
            when (response) {
                is Response.Success -> {
                    Log.d("Profile",response.data.data.email)
                  _profileState.value =  Profile(email = response.data.data.email, id = response.data.data
                      .id, name = response.data.data.name, credit = response.data.data.credits)
                    Log.d("Profile details","${profile.value}")
                }
                is Response.Failure -> {
                    Log.d("Profile",response.e)
                    postMessage( response.e)
                }
            }
        }

    }


    private fun checkSignedInUser(applicationContext: Context) {
        _loadingState.value = true



        _loadingState.value = false
    }
    fun SignOut(popUp:(String,String) -> Unit) {


        viewModelScope.launch {
            authService.Logout()
            popUp(SignInScreen.route,ProfileScreen.route)
        }

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
