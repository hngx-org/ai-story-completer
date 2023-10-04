package com.newton.storycompleter.ui.readingmode

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newton.storycompleter.app.data.local.Story
import com.newton.storycompleter.repository.StoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReadingModeViewModel @Inject constructor(
    private val repository: StoryRepository
) : ViewModel() {
    private val storyId = MutableStateFlow<Int?>(null)

    val story = storyId.map { id ->
        when (id) {
            null -> Story(title = "", content = "")
            else -> repository.getStory(id) ?: Story(title = "", content = "")
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        initialValue = Story(title = "", content = ""),
    )

    fun getStory(storyId: Int) {
        this.storyId.update { storyId }
    }

    fun deleteBook(story: Story) {
        viewModelScope.launch {
            repository.deleteStory(story)
        }
    }
}