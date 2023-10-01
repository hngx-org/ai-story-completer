package com.newton.storycompleter.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation

fun NavGraphBuilder.storiesListGraph(navController: NavController) {

    navigation(startDestination = StoriesListScreen.route, route = StoriesListScreen.route) {

        composable(route = StoriesListScreen.route) {


        }
    }
}