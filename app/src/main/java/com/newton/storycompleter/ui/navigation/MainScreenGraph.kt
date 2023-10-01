package com.newton.storycompleter.ui.navigation

import androidx.compose.foundation.layout.Column
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation

fun NavGraphBuilder.mainScreenGraph(navController: NavController) {

    navigation(startDestination = MainScreen.route, route = MainScreen.route ){

        composable(route = MainScreen.route) {
            Column (){

            }
        }
    }
}