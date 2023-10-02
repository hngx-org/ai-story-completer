package com.newton.storycompleter.app.navigation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.newton.storycompleter.ui.editstory.EditStoryScreen
import com.newton.storycompleter.ui.editstory.EditStoryViewModel

fun NavGraphBuilder.editStoryScreenGraph(
    navController: NavHostController,
) {

    navigation(startDestination = EditStoryScreen.route, route = EditStoryScreen.route) {

        composable(route = EditStoryScreen.route) {

        }
    }
}