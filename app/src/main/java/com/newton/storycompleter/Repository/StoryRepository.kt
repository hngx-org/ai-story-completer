package com.newton.storycompleter.Repository

import com.newton.storycompleter.data.Story
import com.newton.storycompleter.data.StoryCompleterDatabase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class StoryRepository @Inject constructor(private val storyDb:StoryCompleterDatabase) {
    suspend fun getStories(): Flow<List<Story>> = storyDb.storyDao().getAllStories()
    suspend fun saveStory(story: Story) = storyDb.storyDao().saveStory(story)

    suspend fun deleteStory(story: Story) = storyDb.storyDao().deleteStory(story)



}