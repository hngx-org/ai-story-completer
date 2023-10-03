package com.newton.storycompleter.app.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation

fun NavGraphBuilder.editStoryScreenGraph(
    navController: NavHostController,
) {

    navigation(startDestination = EditStoryScreen.route, route = EditStoryScreen.route) {

        composable(route = EditStoryScreen.route) {

        }
    }
}