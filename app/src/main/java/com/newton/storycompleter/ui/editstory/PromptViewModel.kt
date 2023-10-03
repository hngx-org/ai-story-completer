package com.newton.storycompleter.ui.editstory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.newton.storycompleter.Repository.StoryRepository
import com.newton.storycompleter.data.Story
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class PromptViewModel @Inject constructor(private val storyRepository: StoryRepository) : ViewModel() {
    private val _state = MutableStateFlow(PromptScreenState())
    val state = _state.asStateFlow()

    fun updateState(currentState: PromptScreenState) {
        val words = countWords(currentState.story?.content?:"")
        val buttonEnabled = currentState.wordCount > 14 && currentState.story?.title?.isNotEmpty() == true

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

    fun saveGeneratedStory(navController: NavHostController,story: Story){
        viewModelScope.launch {
           // val story = Story(title = state.value.storyTitle , content = state.value.storyContent)
            storyRepository.saveStory(story)
            navController.navigateUp()
        }
    }

}

data class PromptScreenState(
    val story:Story? = null,
    val wordCount: Int = 0,
    val isBtnEnabled: Boolean = false,
    val isPremiumVersion: Boolean = false,
    val premiumFeatures: PremiumFeatures = PremiumFeatures(),
    val trialSession: Int = 1
)