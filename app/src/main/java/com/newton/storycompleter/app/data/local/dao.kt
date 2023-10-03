package com.newton.storycompleter.app.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface StoryDao{

    @Delete
    suspend fun deleteStory(story: Story)

    @Query("SELECT * FROM story_table")
     fun getAllStories(): Flow<List<Story>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStory(story: Story)

    @Query("SELECT * FROM story_table WHERE id = :id")
    suspend fun getStoryById(id: Int): Story?
}
