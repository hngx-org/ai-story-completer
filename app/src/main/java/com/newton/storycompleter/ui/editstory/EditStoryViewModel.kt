package com.newton.storycompleter.ui.editstory

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newton.storycompleter.app.data.local.Story
import com.newton.storycompleter.app.data.remote.api.AiCallback
import com.newton.storycompleter.app.data.remote.api.AiRepository
import com.newton.storycompleter.repository.StoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditStoryViewModel @Inject constructor(private val storyRepository: StoryRepository) : ViewModel() {

    private val _story = MutableStateFlow(Story(title = "", content = ""))
    private val _state = MutableStateFlow(EditStoryState())
    val state = _state.asStateFlow()

    fun updateState(currentState: EditStoryState) {
        val words = countWords(currentState.story?.content!!)
        val buttonEnabled = currentState.wordCount > 14 && currentState.story.title.isNotEmpty()

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

    fun generateText(prompt:String) {
        val prompt1 = "Continue this story in less than 50 token:$prompt"
        Log.d("Prompt", prompt1)
        repository.generateText(prompt1, object : AiCallback {

            override fun onResponse(response: String) {

                _state.update {
                    it.copy(

                        story = state.value.story?.copy(content = state.value.story!!.content+"\n\n"+response )
                    )
                }
                Log.d("Api Response",response)
            }

            override fun onFailure(e: Exception) {
                Log.e("Api Response","error occur",e)
            }
        })
    }


    fun saveGeneratedStory() {
        viewModelScope.launch {
            storyRepository.saveStory(_story.value)
        }
    }

    fun getStory(storyId: Int?) {
        viewModelScope.launch {
            _story.update {
                when (storyId) {
                    null -> Story(title = "", content = "")
                    else -> storyRepository.getStory(storyId) ?: Story(title = "", content = "")
                }
            }
        }
    }
}