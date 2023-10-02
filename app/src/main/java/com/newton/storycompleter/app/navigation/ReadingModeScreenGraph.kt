package com.newton.storycompleter.app.navigation

import androidx.compose.foundation.layout.Column
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation

fun NavGraphBuilder.readingModeScreenGraph(
    navController: NavHostController,
) {

    navigation(startDestination = ReadingModeScreen.route, route = ReadingModeScreen.route) {

        composable(route = ReadingModeScreen.route) {
       Column (){

       }
        }
    }
}