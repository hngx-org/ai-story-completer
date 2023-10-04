package com.newton.storycompleter.app.navigation

interface AiStoryAppDestination{
    val route:String
}

object SplashScreen: AiStoryAppDestination {
    override val route="splash"
    // TODO : Add other information for this route e.g any navigation argument needed
}
object StoriesListScreen: AiStoryAppDestination {
    override val route="ai_stories"
    // TODO : Add other information for this route e.g any navigation argument needed
}
object EditStoryScreen: AiStoryAppDestination {
    override val route="ai_edit_story"
    val idArg: Int? = null
    val routeWithArg = "${route}/{$idArg}"
    // TODO : Add other information for this route e.g any navigation argument needed
}
object ReadingModeScreen: AiStoryAppDestination {
    override val route="reading_mode"
    val idArg: Int? = null
    val routeWithArg = "${route}/{$idArg}"
    // TODO : Add other information for this route e.g any navigation argument needed
}
object SignInScreen:AiStoryAppDestination{
    override val route="signin"
    // TODO : Add other information for this route e.g any navigation argument needed
}
object SignUpScreen:AiStoryAppDestination{
    override val route="signup"
    // TODO : Add other information for this route e.g any navigation argument needed
}