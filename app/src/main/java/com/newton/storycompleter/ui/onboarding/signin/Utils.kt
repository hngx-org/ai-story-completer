package com.newton.storycompleter.ui.onboarding.signin

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.newton.storycompleter.ui.onboarding.signin.Constants.TAG

class Utils {
    companion object {
        fun Print(e: Exception) = Log.e(TAG, e.stackTraceToString())

        fun showMessage(
            context: Context,
            message: String?
        ) = Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}