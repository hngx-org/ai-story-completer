package com.newton.storycompleter

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newton.storycompleter.ui.api.AiCallback
import com.newton.storycompleter.ui.api.AiRepository
import kotlinx.coroutines.launch

class AiStoriesViewModel : ViewModel() {

    private val repository: AiRepository = AiRepository()

    var generatedText by mutableStateOf("")
        private set

    var inputText by mutableStateOf("")
        private set

    var prompt1 = "Continue this story in less than 50 token: The boy came from a poor background"
    var prompt2 = "give a title to this story,$generatedText"
   /* init  {
        Log.d("Api Respose","Test")
        generateText()
    }*/
    fun onInputTextChanged(text: String) {
        inputText = text
    }

    fun generateText() {

           repository.generateText(prompt1, object : AiCallback {
                override fun onResponse(response: String) {
                  Log.d("Api Response",response)
                }

                override fun onFailure(e: Exception) {
                    Log.e("Api Respose","error occur",e)
                }
            })
        }

}