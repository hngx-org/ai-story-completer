package com.newton.storycompleter.ui.onboarding.signin.components

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.newton.storycompleter.R
import com.newton.storycompleter.ui.onboarding.signin.Constants.PASSWORD_LABEL

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordField(
    password: String,
    onPasswordValueChange: (newValue: String) -> Unit
) {
    var passwordIsVisible by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = password,
        onValueChange = { newValue ->
            onPasswordValueChange(newValue)
        },
        label = {
            Text(
                text = PASSWORD_LABEL
            )
        },
        singleLine = true,
        visualTransformation = if (passwordIsVisible) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password
        ),
        trailingIcon = {
            val icon = if (passwordIsVisible) {
             R.drawable.visibility_fill0_wght400_grad0_opsz48
            } else {
               R.drawable.visibility_off_fill0_wght400_grad0_opsz48
            }
            IconButton(
                onClick = {
                    passwordIsVisible = !passwordIsVisible
                }
            ) {
                Icon(
                    painter = painterResource(icon),
                    contentDescription = null,
                    modifier = Modifier.size(width = 16.dp, height = 16.dp)
                )
            }
        }
    )
}