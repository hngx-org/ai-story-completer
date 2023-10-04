package com.newton.storycompleter.repository

import com.newton.storycompleter.app.data.local.Story
import com.newton.storycompleter.app.data.local.StoryDao
import kotlinx.coroutines.flow.Flow

class StoryRepository(private val dao: StoryDao) {
    fun getStories(): Flow<List<Story>> = dao.getAllStories()
    suspend fun saveStory(story: Story) = dao.insertStory(story)

    suspend fun deleteStory(story: Story) = dao.deleteStory(story)

    suspend fun getStory(id: Int) = dao.getStoryById(id = id)

}