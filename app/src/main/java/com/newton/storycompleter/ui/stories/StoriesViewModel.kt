package com.newton.storycompleter.ui.stories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newton.storycompleter.Repository.StoryRepository
import com.newton.storycompleter.data.Story
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class StoriesViewModel @Inject constructor(private val storyRepository: StoryRepository):ViewModel() {



    private val _state = MutableStateFlow(StoryListState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.value = _state.value.copy(loading = true)
             storyRepository.getStories().collect{
                 _state.value = _state.value.copy(loading = false, stories = it)
             }

        }
    }
}

data class StoryListState(
    val stories: List<Story> = emptyList(),
    val loading:Boolean = false
)