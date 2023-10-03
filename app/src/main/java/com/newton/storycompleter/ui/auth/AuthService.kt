package com.newton.storycompleter.ui.auth

import android.content.Context
import com.shegs.hng_auth_library.authlibrary.AuthLibrary
import com.shegs.hng_auth_library.model.AuthResponse
import com.shegs.hng_auth_library.model.LoginRequest
import com.shegs.hng_auth_library.model.LogoutResponse
import com.shegs.hng_auth_library.model.SignupRequest
import com.shegs.hng_auth_library.network.ApiResponse

class AuthService(context: Context) {

    private val apiService = AuthLibrary.createAuthService()
    private val dataStoreRepository = AuthLibrary.createDataStoreRepository(context)

    suspend fun SignUp(name: String, email: String, password: String): ApiResponse<AuthResponse>  {

        val signupRepository = AuthLibrary.createSignupRepository(apiService)

        val signupRequest = SignupRequest(
            name = name,
            email = email,
            password = password,
            confirm_password = password
        )
        val result: ApiResponse<AuthResponse> = signupRepository.signup(signupRequest)
        return result
    }

    suspend fun SignIn(email: String, password: String): ApiResponse<AuthResponse> {
        val loginRepository = AuthLibrary.createLoginRepository(apiService, dataStoreRepository)

        val loginRequest = LoginRequest(
            email = email,
            password = password
        )
        val result: ApiResponse<AuthResponse> = loginRepository.login(loginRequest)
       return result
    }

    suspend fun Logout(): ApiResponse<LogoutResponse> {
        val logoutRepository = AuthLibrary.createLogoutRepository(apiService)
        val result: ApiResponse<LogoutResponse> = logoutRepository.logout()
       return result
    }

    suspend fun UserProfile(): ApiResponse<AuthResponse> {
        val profileRepository = AuthLibrary.createProfileRepository(apiService)
        val result: ApiResponse<AuthResponse> = profileRepository.profile()

        return result
    }
}

