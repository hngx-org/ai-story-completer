package com.newton.storycompleter.app.navigation

import android.content.Context
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.newton.storycompleter.app.data.local.Story
import com.newton.storycompleter.ui.editstory.EditStoryScreen
import com.newton.storycompleter.ui.editstory.EditStoryViewModel
import com.newton.storycompleter.ui.onboarding.signin.SignInFullScreen
import com.newton.storycompleter.ui.onboarding.signup.SignUpFullScreen
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
            val storyListState by storiesViewModel.state.collectAsState()

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
                state = storyListState
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

            val onDelete: () -> Unit = remember {
                {
                   viewModel.deleteStory(story)
                    onBack()
                }
            }
            ReadModeScreen(
                onBack = onBack,
                onDelete = onDelete,
                onEdit = onEdit,
                story = story
            )
        }
        composable(route = SignInScreen.route) {
            SignInFullScreen(
                navigateToSignUpScreen = {
                    navController.navigate(route = SignUpScreen.route)
                }) {
                navController.navigate(route = StoriesListScreen.route) {
                    popUpTo(SignInScreen.route) { inclusive = true }
                }
            }
        }
        composable(route = SignUpScreen.route) {
            SignUpFullScreen(openAndPopUp = {
                navController.navigate(route = SignInScreen.route)
            }) {
                navController.navigate(route = SignInScreen.route)
            }
        }

        /*   mainScreenGraph(navController)
             aiStoryGraphScreen(navController)
            readingModeScreenGraph(navController)*/


    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AiStoryApp(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {

    val currentBackStackEntry by navController.currentBackStackEntryAsState()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        content = { offsetPadding ->

            StoryAppNavHost(
                modifier = Modifier
                    .padding(offsetPadding)
                    .fillMaxSize(),
                navController = navController
            )
        },
        bottomBar = {
            Row {

            }
        },

        snackbarHost = {
            SnackbarHostState()
        }
    )
}

class MockNavController(ctx: Context) : NavHostController(ctx)