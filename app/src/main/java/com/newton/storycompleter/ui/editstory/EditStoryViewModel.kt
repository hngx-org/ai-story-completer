package com.newton.storycompleter.ui.editstory

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apilibrary.wrapperclass.OpenAiCaller
import com.newton.storycompleter.app.data.local.Story
import com.newton.storycompleter.app.data.remote.api.AiCallback
import com.newton.storycompleter.app.data.remote.api.AiRepository
import com.newton.storycompleter.repository.StoryRepository
import com.newton.storycompleter.ui.auth.AuthService
import com.shegs.hng_auth_library.authlibrary.AuthLibrary
import com.shegs.hng_auth_library.network.ApiService
import com.shegs.hng_auth_library.repositories.DataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditStoryViewModel @Inject constructor(private val storyRepository: StoryRepository,private val authService: AuthService) : ViewModel() {

    private val _story = MutableStateFlow(Story(title = "", content = ""))
    private val _state = MutableStateFlow(EditStoryState())
    val state = _state.asStateFlow()
    private val openApiClient = OpenAiCaller
    var id:String?  = null


    init{
        getId()
    }
    fun updateState(currentState: EditStoryState) {
        val words = countWords(currentState.story?.content!!)
        val buttonEnabled = currentState.wordCount > 14 && currentState.story.title.isNotEmpty()

        _state.update { it.copy(story = _story.value) }
        _state.update { currentState }
        _story.update { it.copy(title = currentState.story.title, content = currentState.story.content) }
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



    private fun getId(){


        viewModelScope.launch {
            authService.dataStoreRepository.getUserID().collect{
                id =it
            }}
    }
    fun generateText(prompt:String) {
        val prompt1 = "Continue this story in less than 50 token:$prompt"
        Log.d("Prompt", prompt1)
Log.d("UserId","$id")
        viewModelScope.launch {
          /*  val response = openApiClient.generateChatResponse(prompt1, id!!)
            _state.update {
                it.copy(

                    story = state.value.story?.copy(content = state.value.story!!.content + response)
                )
            }*/

              repository.generateText(prompt1, object : AiCallback {

            override fun onResponse(response: String) {

                _state.update {
                    it.copy(

                        story = state.value.story?.copy(content = state.value.story!!.content+response )
                    )
                }
                Log.d("Api Response",response)
            }

            override fun onFailure(e: Exception) {
                Log.e("Api Response","error occur",e)
            }
        })
        }

    }
        fun saveGeneratedStory() {
            viewModelScope.launch {
                storyRepository.saveStory(_story.value)
            }
        }

        fun getStory(storyId: Int) {
            viewModelScope.launch {
                val story = if (storyId == -1) {
                    Story(title = "", content = "")
                } else {
                    storyRepository.getStory(storyId) ?: Story(title = "", content = "")
                }
                _story.update { story }
                _state.update { it.copy(story = story) }
            }
        }

}