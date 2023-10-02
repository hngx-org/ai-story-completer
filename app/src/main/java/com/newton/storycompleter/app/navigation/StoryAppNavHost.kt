package com.newton.storycompleter.app.navigation

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.newton.storycompleter.ui.editstory.EditStoryScreen
import com.newton.storycompleter.ui.editstory.EditStoryViewModel
import com.newton.storycompleter.ui.stories.StoriesScreen

@Composable
fun StoryAppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController

){

    NavHost(
        modifier = modifier.fillMaxSize(),
        navController = navController,
        startDestination = SplashScreen.route
    ){
        // TODO : Add your navigation graph as appropriate

        composable(route = SplashScreen.route) {
           com.newton.storycompleter.ui.onboarding.SplashScreen(onNext = {
               navController.navigate(route = StoriesListScreen.route){
                  // launchSingleTop = true
                   popUpTo(SplashScreen.route){inclusive = true}
               }

           })
        }
        composable(route = StoriesListScreen.route) {

            val createStory = remember {
                {
                    navController.navigate(EditStoryScreen.route)
                }
            }

            val stories =
                listOf("Kid Danger", "SpongeBob SquarePants", "Sofia the first living in a castle")


            StoriesScreen(
                stories = stories,
                onStoryClick = { }, //todo go to detail screen,
                onCreateStoryClick = createStory
            )

        }

        composable(route = EditStoryScreen.route) {
            val viewModel = viewModel<EditStoryViewModel>()
            val state = viewModel.state.collectAsState().value

            val onClose: () -> Unit = remember {
                {
                    navController.navigateUp()
                }
            }

            EditStoryScreen(
                state = state,
                updateState = viewModel::updateState,
                isEdit = false,
                onFinishClick = { },
                onGenerateClick = { },
                onClose = onClose,
                onDecreaseCandidate = { },
                onIncreaseCandidate = { },
                onDecreaseWords = { },
                onIncreaseWords = { },
                onPremiumClicked = { }
            )
        }
        composable(route = ReadingModeScreen.route) {
            Column (){

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
){

    val currentBackStackEntry by navController.currentBackStackEntryAsState()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        content = { offsetPadding->

            StoryAppNavHost(
                modifier = Modifier
                    .padding(offsetPadding)
                    .fillMaxSize(),
                navController =navController
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

class MockNavController(ctx: Context): NavHostController(ctx)