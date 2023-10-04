package com.newton.storycompleter.app.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Story::class], version = 1, exportSchema = false)
abstract class StoryCompleterDatabase : RoomDatabase() {
    abstract fun storyDao(): StoryDao
}