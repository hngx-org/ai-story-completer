package com.newton.storycompleter.ui.editstory

import androidx.lifecycle.ViewModel
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

}