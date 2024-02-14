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
import com.nsa.navigation.ext.navigateTo
import com.nsa.navigation.onBoardingFirstScreen
import com.nsa.navigation.onBoardingSecondScreen
import com.nsa.navigation.onBoardingThirdScreen
import com.nsa.navigation.signInScreen
import com.nsa.navigation.signInWithEmailScreen
import com.nsa.navigation.signUpScreen

/**
 * Nested navigation graph for OnBoarding screen
 *
 * Created on Jan 24, 2023.
 *
 */
@Composable
fun AuthenticationNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    onNavigateToRoot: (Screen) -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = Screen.SignIn.route,
        modifier = modifier,
    ) {

        signInScreen(
            onNavigateTo = navController::navigateTo,
            onNavigateToRoot = onNavigateToRoot
        )

        signInWithEmailScreen(
            onNavigateTo = navController::navigateTo,
            onNavigateToRoot = onNavigateToRoot,
            onNavigateBack = navController::navigateUp
        )

        signUpScreen(
            onNavigateBack = navController::navigateUp,
            onNavigateToRoot = onNavigateToRoot
        )
        onBoardingThirdScreen()
    }
}