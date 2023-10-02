package com.newton.storycompleter.ui.editstory

data class EditStoryState(
    val title: String = "",
    val story: String = "",
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