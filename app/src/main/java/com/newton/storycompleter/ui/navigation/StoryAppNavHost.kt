package com.newton.storycompleter.ui.navigation

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.newton.storycompleter.ui.onboarding.SplashScreen

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
               navController.navigate(route = MainScreen.route){
                   launchSingleTop = true
                   popUpTo(SplashScreen.route){inclusive = true}
               }

           })
        }
        composable(route = MainScreen.route) {
            Column (){

            }
        }

        composable(route = AiStoryScreen.route) {
            Column (){

            }
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