package com.newton.storycompleter.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface StoryDao{
    @Upsert
    suspend fun saveStory(story: Story)

    @Delete
    suspend fun deleteStory(story: Story)

    @Query("SELECT * FROM story_table")
     fun getAllStories(): Flow<List<Story>>
}
