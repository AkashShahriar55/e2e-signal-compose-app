/*
 * Copyright 2022 | Dmitri Chernysh | https://mobile-dev.pro
 *
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.nsa.navigation.graph

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.nsa.navigation.Screen
import com.nsa.navigation.authenticationGraph
import com.nsa.navigation.chatScreen
import com.nsa.navigation.ext.navigateTo
import com.nsa.navigation.homeNavGraph
import com.nsa.navigation.onBoardingNavGraph
import com.nsa.navigation.peopleProfileScreen
import com.nsa.navigation.subscriptionScreen

/**
 * Top-level navigation host in the app
 *
 * Created on Jan 07, 2023.
 *
 */

@Composable
fun RootNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: Screen
) {
    NavHost(
        navController = navController,
        route = "root_host",
        startDestination = startDestination.route,
        modifier = modifier,
    ) {

        val navigateBack: () -> Unit = {
            navController.navigateUp()
        }


        //Nested Navigation Graphs
        onBoardingNavGraph(onNavigateToRoot = navController::navigateTo)
        homeNavGraph(onNavigateToRoot = navController::navigateTo)
        authenticationGraph(onNavigateToRoot = navController::navigateTo)

        peopleProfileScreen(
            onNavigateTo = navController::navigateTo,
            onNavigateBack = navController::navigateUp
        )

        //Root screens
        subscriptionScreen(onNavigateBack = navigateBack)

        chatScreen(
            onNavigateTo = navController::navigateTo,
            onNavigateBack = navController::navigateUp
        )
    }
}