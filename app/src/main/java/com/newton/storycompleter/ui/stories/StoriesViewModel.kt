package com.newton.storycompleter.ui.stories

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newton.storycompleter.app.data.local.Story
import com.newton.storycompleter.repository.StoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class StoriesViewModel @Inject constructor(private val storyRepository: StoryRepository) :
    ViewModel() {

    val state = storyRepository.getStories().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        initialValue = emptyList()
    )

}


@Stable
data class StoryListState(
    val stories: List<Story> = emptyList(),
   // val loading: Boolean = false
)