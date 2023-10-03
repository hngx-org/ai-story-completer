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
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.newton.storycompleter.ui.editstory.PromptScreen
import com.newton.storycompleter.ui.editstory.PromptViewModel
import com.newton.storycompleter.ui.stories.StoriesScreen
import com.newton.storycompleter.ui.stories.StoriesViewModel

@Composable
fun StoryAppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController

){
    // instantiate viewmodels


    NavHost(
        modifier = modifier.fillMaxSize(),
        navController = navController,
        startDestination = SplashScreen.route
    ){
        // TODO : Add your navigation graph as appropriate

        composable(route = SplashScreen.route) {
           com.newton.storycompleter.ui.onboarding.SplashScreen(
               onNext = {
                   navController.navigate(route = StoriesListScreen.route){
                   popUpTo(SplashScreen.route){inclusive = true}
               }
           })
        }
        composable(route = StoriesListScreen.route) {
            val storiesViewModel: StoriesViewModel = hiltViewModel()
            val storyListState by storiesViewModel.state.collectAsState()
           StoriesScreen(
               onStoryClick ={storyIndex->
                   navController.navigate(route =ReadingModeScreen.route )
                             } ,
               onCreateStoryClick = { navController.navigate(route =EditStoryScreen.route) },
               state = storyListState
           )
        }

        composable(route = EditStoryScreen.route) {
            val promptViewModel:PromptViewModel = hiltViewModel()
            val state = promptViewModel.state.collectAsState().value
            val state2 = promptViewModel.state.collectAsState().value


            PromptScreen(
                state = state,
                updateState = promptViewModel::updateState,
                isEdit = false,
                onFinishClick = { promptViewModel.saveGeneratedStory(navController,it) },
                onGenerateClick = { },
                onClose = {navController.navigateUp()},
                onDecreaseCandidate = { },
                onIncreaseCandidate = { },
                onDecreaseWords = { },
                onIncreaseWords = { },
                onPremiumClicked = { },
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