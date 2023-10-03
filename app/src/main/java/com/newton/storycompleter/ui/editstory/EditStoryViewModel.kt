package com.newton.storycompleter.ui.editstory

import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.newton.storycompleter.ui.api.AiCallback
import com.newton.storycompleter.ui.api.AiRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class EditStoryViewModel : ViewModel() {
    private val _state = MutableStateFlow(EditStoryState())
    val state = _state.asStateFlow()

    fun updateState(currentState: EditStoryState) {
        val words = countWords(currentState.story)
        val buttonEnabled = currentState.wordCount > 14 && currentState.title.isNotEmpty()

        _state.update { currentState }
        _state.update {
            it.copy(
                wordCount = words,
                isBtnEnabled = buttonEnabled,
            )
        }
    }

    private fun countWords(text: String): Int {
        val words = text.split(Regex("\\s+"))
        return words.count { it.isNotBlank() }
    }

    // Todo Add OpenApi functions for continue and finish story
    // Todo Once the continue function is clicked, reset the wordCount to 0
    private val repository: AiRepository = AiRepository()

    var generatedText by mutableStateOf("")
        private set

    var inputText by mutableStateOf("")
        private set


    var prompt2 = "give a title to this story,$generatedText"
    /* init  {
         Log.d("Api Respose","Test")
         generateText()
     }*/
    fun onInputTextChanged(text: String) {
        inputText = text
    }

    fun generateText() {
        val prompt1 = "Continue this story in less than 50 token: ${_state.value.story}"
        Log.d("Prompt", prompt1)
        repository.generateText(prompt1, object : AiCallback {

            override fun onResponse(response: String) {

                _state.update {
                    it.copy(
                        story = state.value.story+"\n\n"+response
                    )
                }
                Log.d("Api Response",response)
            }

            override fun onFailure(e: Exception) {
                Log.e("Api Respose","error occur",e)
            }
        })
    }
}