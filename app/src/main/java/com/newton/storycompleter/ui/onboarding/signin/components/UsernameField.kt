package com.newton.storycompleter.ui.onboarding.signin.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.newton.storycompleter.ui.onboarding.signin.Constants

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UsernameField(
    username: String,
    onUsernameValueChange: (newValue: String) -> Unit
) {
    OutlinedTextField(
        value = username,
        onValueChange = { newValue ->
            onUsernameValueChange(newValue)
        },
        label = {
            Text(
                text = Constants.USERNAME_LABEL
            )
        })
}