package com.newton.storycompleter.ui.editstory

import com.newton.storycompleter.data.Story

data class EditStoryState(
    val story: Story? = null,
    val wordCount: Int = 0,
    val isBtnEnabled: Boolean = false,
    val isPremiumVersion: Boolean = false,
    val premiumFeatures: PremiumFeatures = PremiumFeatures(),
    val trialSession: Int = 1
)

data class PremiumFeatures(
    val creativityIndex: CreativeIndex = CreativeIndex.Balanced,
    val candidateCount: Int = 2,
    val generatedWords: Int = 50, //words per Generation
)

enum class CreativeIndex {
    Conservative,
    Balanced,
    Inventive
}