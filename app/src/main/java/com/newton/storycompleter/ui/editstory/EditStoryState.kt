package com.newton.storycompleter.ui.editstory

data class EditStoryState(
    val title: String = "",
    val story: String = "",
    val wordCount: Int = 0,
    val isBtnEnabled: Boolean = false
)
