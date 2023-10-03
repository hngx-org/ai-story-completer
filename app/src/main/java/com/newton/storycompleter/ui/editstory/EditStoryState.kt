package com.newton.storycompleter.ui.editstory



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