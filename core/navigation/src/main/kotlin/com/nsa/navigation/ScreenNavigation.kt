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
package com.nsa.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.nsa.chat.ChatScreen
import com.nsa.chat.ChatViewModel
import com.nsa.chat.args.ChatArgs
import com.nsa.chatlist.view.ChatListScreen
import com.nsa.chatlist.view.ChatListViewModel
import com.nsa.di.koinScope
import com.nsa.favorites.FavoritePeopleScreen
import com.nsa.find_people.FindPeopleScreen
import com.nsa.find_people.FindPeopleViewModel
import com.nsa.home.view.HomeScreen
import com.nsa.home.view.HomeViewModel
import com.nsa.navigation.ext.navigateTo
import com.nsa.navigation.graph.AuthenticationNavGraph
import com.nsa.navigation.graph.HomeNavGraph
import com.nsa.navigation.graph.OnBoardingNavGraph
import com.nsa.onboarding.view.OnBoardingFirstScreen
import com.nsa.onboarding.view.OnBoardingScreen
import com.nsa.onboarding.view.OnBoardingSecondScreen
import com.nsa.onboarding.view.OnBoardingThirdScreen
import com.nsa.people.profile.view.PeopleProfileScreen
import com.nsa.people.profile.view.PeopleProfileViewModel
import com.nsa.people.profile.view.args.PeopleProfileArgs
import com.nsa.peoplelist.view.PeopleListScreen
import com.nsa.peoplelist.view.PeopleListViewModel
import com.nsa.people.view.nearyou.NearYouListScreen
import com.nsa.people.view.newmatches.NewMatchesListScreen
import com.nsa.peoplelist.view.PeopleListUiEvent
import com.nsa.signin.EmailLoginScreen
import com.nsa.signin.LoginLandingScreen
import com.nsa.signin.LoginViewModel
import com.nsa.signin.di.featureLoginModule
import com.nsa.signup.RegistrationScreen
import com.nsa.signup.RegistrationViewModel
import com.nsa.signup.di.featureRegistrationModule
import com.nsa.subscription.SubscriptionScreen
import com.nsa.user.profile.di.featureUserProfileModule
import com.nsa.user.profile.view.ProfileScreen
import com.nsa.user.profile.view.vm.ProfileViewModel
import org.koin.core.context.loadKoinModules
import usecases.findpeopleusecases.FetchPeopleListKey


fun NavGraphBuilder.homeNavGraph(onNavigateToRoot: (Screen) -> Unit) {
    composable(
        route = Screen.Home.route
    ) {
        Log.d("navigation", "------homeNavGraph:START------------")

        //NavController for nested graph
        //It will not work for root graph
        val navController = rememberNavController()
        val navBackStackEntry by navController.currentBackStackEntryAsState()

        val bottomBar: @Composable () -> Unit = {
            Log.d("navigation", "homeNavGraph:bottomBar")
            HomeBottomNavigation(
                screens = listOf(
                    Screen.FindPeople,
                    Screen.People,
                    Screen.ChatList,
                    Screen.Profile,
                ), onNavigateTo = navController::navigateTo,
                currentDestination = navBackStackEntry?.destination
            )
        }

        val nestedNavGraph: @Composable () -> Unit = {
            Log.d("navigation", "homeNavGraph:nestedNavGraph")
            HomeNavGraph(
                navController = navController,
                onNavigateToRoot = onNavigateToRoot
            )
        }

        val viewModel: HomeViewModel = viewModel()

        HomeScreen(
            bottomBar = bottomBar,
            nestedNavGraph = nestedNavGraph
        )

        Log.d("navigation", "------homeNavGraph:END------------")
    }

}

fun NavGraphBuilder.onBoardingNavGraph(onNavigateToRoot: (Screen) -> Unit) {
    composable(
        route = Screen.OnBoarding.route
    ) {

        val navController = rememberNavController()

        val nestedNavGraph: @Composable () -> Unit = {
            OnBoardingNavGraph(
                navController = navController
            )
        }

        OnBoardingScreen(
            nestedNavGraph,
            onNext = {
                when (navController.currentDestination?.route) {
                    Screen.OnBoardingFirst.route -> navController.navigateTo(Screen.OnBoardingSecond)
                    Screen.OnBoardingSecond.route -> navController.navigateTo(Screen.OnBoardingThird)
                    Screen.OnBoardingThird.route -> Screen.Home.withClearBackStack()
                        .also(onNavigateToRoot)

                    else -> {}
                }
            }
        )
    }
}

fun NavGraphBuilder.favoritePeopleNavGraph(onNavigateToRoot: (Screen) -> Unit) {
    composable(
        route = Screen.People.route
    ) {

        val viewModel: PeopleListViewModel = viewModel()

        viewModel.observePeopleList(FetchPeopleListKey.Favorite)

        FavoritePeopleScreen(
            viewModel.uiState,
            {
                viewModel.onUiEvent(PeopleListUiEvent.MakeFavorite(it))
            },
            {
                viewModel.onUiEvent(PeopleListUiEvent.SayHi(it))
            }
        ) { profileId: Int ->
            Screen.PeopleProfile.routeWith(profileId.toString())
                .also(onNavigateToRoot)
        }


    }
}

fun NavGraphBuilder.onBoardingFirstScreen() {
    composable(
        route = Screen.OnBoardingFirst.route
    ) {
        OnBoardingFirstScreen()
    }
}

fun NavGraphBuilder.onBoardingSecondScreen() {
    composable(
        route = Screen.OnBoardingSecond.route
    ) {
        OnBoardingSecondScreen()
    }
}

fun NavGraphBuilder.onBoardingThirdScreen() {
    composable(
        route = Screen.OnBoardingThird.route
    ) {
        OnBoardingThirdScreen()
    }
}

fun NavGraphBuilder.subscriptionScreen(onNavigateBack: () -> Unit) {
    composable(
        route = Screen.Subscription.route
    ) {
        SubscriptionScreen(onNavigateBack)
    }
}


fun NavGraphBuilder.findPeopleScreen(onNavigateToRoot: (Screen) -> Unit) {
    composable(
        route = Screen.FindPeople.route
    ) {

        val findPeopleViewModel: FindPeopleViewModel = viewModel()

        FindPeopleScreen(
            findPeopleViewModel,
            {
                NearYouListScreen(onNavigateToProfile = { profileId: Int ->
                    Screen.PeopleProfile.routeWith(profileId.toString())
                        .also(onNavigateToRoot)
                })
            },
        ) {
            NewMatchesListScreen(onNavigateToProfile = { profileId: Int ->
                Screen.PeopleProfile.routeWith(profileId.toString())
                    .also(onNavigateToRoot)
            })
        }
    }
}


fun NavGraphBuilder.chatListScreen(onNavigateToRoot: (Screen) -> Unit) {
    composable(
        route = Screen.ChatList.route
    ) {

        val viewModel: ChatListViewModel = viewModel()

        ChatListScreen(
            state = viewModel.uiState,
            onClick = {conversationId ->
                Screen.Chat.routeWith(conversationId.toString())
                    .also(onNavigateToRoot)
            }
        )
    }
}

fun NavGraphBuilder.peopleListScreen(onNavigateTo: (Screen) -> Unit) {
    composable(
        route = Screen.PeopleList.route
    ) {

        val viewModel: PeopleListViewModel = viewModel()

        PeopleListScreen(
            stateFlow = viewModel.uiState,
            onNavigateToProfile = { profileId: Int ->
                Screen.PeopleProfile.routeWith(profileId.toString())
                    .also(onNavigateTo)
            },
            makeFavorite={
                viewModel.onUiEvent(PeopleListUiEvent.MakeFavorite(it))
            },
            sayHi = {
                viewModel.onUiEvent(PeopleListUiEvent.SayHi(it))
            }
        )
    }
}

fun NavGraphBuilder.peopleProfileScreen(
    onNavigateBack: () -> Unit,
    onNavigateTo: (Screen) -> Unit
) {
    composable(
        route = Screen.PeopleProfile.route,
        arguments = listOf(
            navArgument(PeopleProfileArgs.PEOPLE_PROFILE_ID_ARG) { type = NavType.IntType }
        )
    ) {

        val viewModel: PeopleProfileViewModel = viewModel()


        PeopleProfileScreen(
            viewModel.uiState,
            onBackPressed = onNavigateBack
        ) {}
    }
}


fun NavGraphBuilder.profileScreen(onNavigateToRoot: (Screen) -> Unit) {
    composable(
        route = Screen.Profile.route
    ) {

        loadKoinModules(featureUserProfileModule)
        val viewModel: ProfileViewModel by koinScope<ProfileViewModel>().inject()

        ProfileScreen(
            state = viewModel.uiState,
            onNavigateToSubscription = {
                onNavigateToRoot(Screen.Subscription)
            },
            onLogOut = { Screen.Authentication.withClearBackStack().also(onNavigateToRoot) }
        )
    }
}


fun NavGraphBuilder.authenticationGraph(onNavigateToRoot: (Screen) -> Unit) {
    composable(
        route = Screen.Authentication.route
    ) {

        val navController = rememberNavController()
        AuthenticationNavGraph(
            navController = navController,
            onNavigateToRoot = onNavigateToRoot
        )
    }
}

fun NavGraphBuilder.signInScreen(onNavigateTo: (Screen) -> Unit,onNavigateToRoot: (Screen) -> Unit) {
    composable(
        route = Screen.SignIn.route
    ) {

        loadKoinModules(featureLoginModule)
        val viewModel: LoginViewModel by koinScope<LoginViewModel>().inject()

        LoginLandingScreen(
            viewModel,
            onNavigateToRegistration = { onNavigateTo(Screen.SignUp)},
            onNavigateToLoginWithEmail = { onNavigateTo(Screen.SignInWithEmail) },
            onNavigateToAuthenticatedRoute = {
                Screen.Home.withClearBackStack()
                    .also(onNavigateToRoot)
            }
        )
    }
}

fun NavGraphBuilder.signInWithEmailScreen(onNavigateTo: (Screen) -> Unit,onNavigateToRoot: (Screen) -> Unit,onNavigateBack: () -> Unit) {
    composable(
        route = Screen.SignInWithEmail.route
    ) {

        loadKoinModules(featureLoginModule)
        val viewModel: LoginViewModel by koinScope<LoginViewModel>().inject()

        EmailLoginScreen(
            viewModel,
            onNavigateBack = { onNavigateBack()},
            onNavigateToForgotPassword = { /*TODO*/ },
            onNavigateToAuthenticatedRoute = {
                Screen.Home.withClearBackStack()
                    .also(onNavigateToRoot)
            }
        )
    }
}

fun NavGraphBuilder.signUpScreen(onNavigateBack: () -> Unit, onNavigateToRoot: (Screen) -> Unit) {
    composable(
        route = Screen.SignUp.route
    ) {

        loadKoinModules(featureRegistrationModule)
        val viewModel: RegistrationViewModel by koinScope<RegistrationViewModel>().inject()

        RegistrationScreen(
            viewModel,
            onNavigateBack = { onNavigateBack() },
            onNavigateToAuthenticatedRoute = {
                Screen.Home.withClearBackStack()
                .also(onNavigateToRoot)
            }
        )
    }
}

fun NavGraphBuilder.chatScreen(
    onNavigateBack: () -> Unit,
    onNavigateTo: (Screen) -> Unit
) {
    composable(
        route = Screen.Chat.route,
        arguments = listOf(
            navArgument(ChatArgs.CONVERSATION_ID_ARG) { type = NavType.IntType }
        )
    ) {

        val viewModel: ChatViewModel = viewModel()


        ChatScreen(
            viewModel.uiState,
            viewModel.userProfileState
        )
    }
}



