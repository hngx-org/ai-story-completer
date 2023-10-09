package com.newton.storycompleter.app.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.newton.storycompleter.app.data.local.Story
import com.newton.storycompleter.ui.editstory.EditStoryScreen
import com.newton.storycompleter.ui.editstory.EditStoryViewModel
import com.newton.storycompleter.ui.onboarding.signin.SignInFullScreen
import com.newton.storycompleter.ui.onboarding.signup.SignUpFullScreen
import com.newton.storycompleter.ui.profile.ProfileScreen
import com.newton.storycompleter.ui.readingmode.ReadModeScreen
import com.newton.storycompleter.ui.readingmode.ReadingModeViewModel
import com.newton.storycompleter.ui.stories.StoriesScreen
import com.newton.storycompleter.ui.stories.StoriesViewModel

@Composable
fun StoryAppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController

) {

    NavHost(
        modifier = modifier.fillMaxSize(),
        navController = navController,
        startDestination = SplashScreen.route
    ) {

        composable(route = SplashScreen.route) {
            com.newton.storycompleter.ui.onboarding.SplashScreen(onNext = {
                navController.navigate(route = SignInScreen.route) {
                    popUpTo(SplashScreen.route) { inclusive = true }
                }

            })
        }
        composable(route = StoriesListScreen.route) {
            val storiesViewModel: StoriesViewModel = hiltViewModel()


            val onStoryClicked = remember {
                { story: Story ->
                    navController.navigate(
                        ReadingModeScreen.routeWithArg.replace(
                            "{${ReadingModeScreen.idArg}}",
                            story.id.toString()
                        )

                    )
                    {
                        launchSingleTop = true
                    }
                }

            }

            StoriesScreen(
                onStoryClick = onStoryClicked,
                onCreateStoryClick = {
                    val routeWithArg =
                        EditStoryScreen.routeWithArg.replace("{${EditStoryScreen.idArg}}", "-1")
                    navController.navigate(route = routeWithArg)
                },
                stories = storiesViewModel.state.collectAsState().value,
                onProfileClick = {navController.navigate(route = ProfileScreen.route)}
            )
        }

        composable(
            route = EditStoryScreen.routeWithArg,
            arguments = listOf(
                navArgument(EditStoryScreen.idArg)
                {
                    type = NavType.IntType
                }
            )) { backStackEntry ->

            val viewModel: EditStoryViewModel = hiltViewModel()
            val state = viewModel.state.collectAsState().value

            val storyId = backStackEntry.arguments?.getInt(EditStoryScreen.idArg)

            LaunchedEffect(key1 = Unit, block = {
               viewModel.getStory(storyId!!)
            })

            val onClose: () -> Unit = remember {
                {
                    navController.navigateUp()
                }
            }

            val onFinish: () -> Unit = remember {
                {
                    viewModel.saveGeneratedStory()
                    onClose()
                }
            }

            EditStoryScreen(
                state = state,
                updateState = viewModel::updateState,
                isEdit = storyId != -1,
                onFinishClick = onFinish,
                onGenerateClick = viewModel::generateText,
                onClose = onClose,
                onDecreaseCandidate = { },
                onIncreaseCandidate = { },
                onDecreaseWords = { },
                onIncreaseWords = { },
                onPremiumClicked = { }
            )
        }
        composable(
            route = ReadingModeScreen.routeWithArg,
            arguments = listOf(
                navArgument(ReadingModeScreen.idArg)
                {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->

            val viewModel: ReadingModeViewModel = hiltViewModel()
            val storyId = backStackEntry.arguments?.getInt(ReadingModeScreen.idArg)

            LaunchedEffect(key1 = Unit, block = {
                if (storyId != null) {
                    viewModel.getStory(storyId)
                }
            })


            val onBack: () -> Unit = remember {
                {
                    navController.navigateUp()
                }
            }
            val story = viewModel.story.collectAsState().value

            val onEdit: () -> Unit = remember {
                {
                    navController.navigate(
                       EditStoryScreen.routeWithArg.replace(
                            "{${EditStoryScreen.idArg}}",
                            storyId.toString()
                        )
                    )
                }
            }

            val onDelete: () -> Unit =
                {
                   viewModel.deleteStory(story)
                    onBack()
                }

            ReadModeScreen(
                onBack = onBack,
                onDelete = onDelete,
                onEdit = onEdit,
                story = story
            )
        }
        composable(route = SignInScreen.route) {
            SignInFullScreen(navigateToSignUpScreen = { navController.navigate(route = SignUpScreen.route) },
                openAndPopUp = { route, popUpTo ->
                    navController.navigate(route) {
                        launchSingleTop = true
                        popUpTo(popUpTo) { inclusive = true }
                    }
                })
        }
        composable(route = SignUpScreen.route) {
            SignUpFullScreen(openAndPopUp = { route, popUpTo ->
                navController.navigate(route) {
                    launchSingleTop = true
                    popUpTo(popUpTo) { inclusive = true }
                }
            }, navigateBack = {
                navController.navigate(route = SignInScreen.route)
            })
        }

        composable(route = ProfileScreen.route) {
            ProfileScreen(
                navigateBack = {
                    navController.navigate(route = StoriesListScreen.route)
                },
                openAndPopUp = { route, popUpTo ->
                    navController.navigate(route) {
                        launchSingleTop = true
                        popUpTo(popUpTo) { inclusive = true }
                    }
                }
            )
        }

        /*   mainScreenGraph(navController)
             aiStoryGraphScreen(navController)
            readingModeScreenGraph(navController)*/


    }

    fun popUp() {
        navController.popBackStack()
    }

    fun navigate(route: String) {
        navController.navigate(route) { launchSingleTop = true }
    }

    fun navigateAndPopUp(route: String, popUp: String) {
        navController.navigate(route) {
            launchSingleTop = true
            popUpTo(popUp) { inclusive = true }
        }
    }

    fun clearAndNavigate(route: String) {
        navController.navigate(route) {
            launchSingleTop = true
            popUpTo(0) { inclusive = true }
        }
    }
}
