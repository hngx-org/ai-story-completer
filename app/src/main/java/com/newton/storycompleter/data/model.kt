package com.newton.storycompleter.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "story_table")
data class Story(

     @PrimaryKey(autoGenerate = true) val id:Int=0,
    val title:String,
    val content:String
)