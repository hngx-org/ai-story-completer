package com.newton.storycompleter.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Story::class], version = 1)
abstract class StoryCompleterDatabase:RoomDatabase() {
    abstract fun storyDao(): StoryDao
}