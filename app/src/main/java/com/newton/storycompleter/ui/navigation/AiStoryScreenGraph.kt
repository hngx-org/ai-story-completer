package com.newton.storycompleter.ui.navigation

import androidx.compose.foundation.layout.Column
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation

fun NavGraphBuilder.aiStoryGraphScreen(
    navController: NavHostController,
) {

    navigation(startDestination = AiStoryScreen.route, route = AiStoryScreen.route) {

        composable(route = AiStoryScreen.route) {
            Column (){

            }
        }
    }
}