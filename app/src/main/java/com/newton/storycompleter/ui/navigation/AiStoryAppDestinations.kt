package com.newton.storycompleter.ui.navigation

interface AiStoryAppDestination{
    val route:String
}

object SplashScreen:AiStoryAppDestination{
    override val route="splash"
    // TODO : Add other information for this route e.g any navigation argument needed
}
object MainScreen:AiStoryAppDestination{
    override val route="ai_main"
    // TODO : Add other information for this route e.g any navigation argument needed
}
object AiStoryScreen:AiStoryAppDestination{
    override val route="ai_story"
    // TODO : Add other information for this route e.g any navigation argument needed
}
object ReadingModeScreen:AiStoryAppDestination{
    override val route="reading_mode"
    // TODO : Add other information for this route e.g any navigation argument needed
}