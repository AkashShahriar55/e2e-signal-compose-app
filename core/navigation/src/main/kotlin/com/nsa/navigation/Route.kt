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

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.nsa.people.profile.view.args.PeopleProfileArgs

const val navigationRouteOnBoarding = "on_boarding"
const val navigationRouteOnBoardingFirst = "on_boarding_first"
const val navigationRouteOnBoardingSecond = "on_boarding_second"
const val navigationRouteOnBoardingThird = "on_boarding_third"

const val navigationRouteHome = "home"
const val navigationRouteChatList = "chat_list"
const val navigationRouteProfile = "profile"

const val navigationRoutePeople = "people"
const val navigationRoutePeopleList = "people_list"
const val navigationRoutePeopleProfile =
    "people_profile/{${PeopleProfileArgs.PEOPLE_PROFILE_ID_ARG}}"

const val navigationRouteSubscription = "subscription"

const val navigationRouteAuth = "authentication"
const val navigationRouteSignIn = "sign_in"
const val navigationRouteSignInWithEmail = "sign_in_with_email"
const val navigationRouteSignUp = "sign_up"

sealed class Screen(
    val route: String,
    var routePath: String? = null,
    var clearBackStack: Boolean = false,
    val restoreState: Boolean = true,
    val title: String? = null,
    val icon: Int = com.nsa.ui.R.drawable.home_icon
) {

    fun withClearBackStack() = apply { clearBackStack = true }

    fun routeWith(path: String) = apply {
        routePath = path
    }

    object OnBoarding : Screen(navigationRouteOnBoarding)
    object OnBoardingFirst : Screen(navigationRouteOnBoardingFirst)
    object OnBoardingSecond : Screen(navigationRouteOnBoardingSecond)
    object OnBoardingThird : Screen(navigationRouteOnBoardingThird)

    object Home : Screen(navigationRouteHome)

    // 3 tabs of Bottom navigation
    object ChatList :
        Screen(route = navigationRouteChatList, title = "Chats", icon = com.nsa.ui.R.drawable.chat_icon)

    object People : Screen(
        route = navigationRoutePeople,
        restoreState = false,
        title = "People",
        icon = com.nsa.ui.R.drawable.favorite_icon,
    )

    object Authentication : Screen(navigationRouteAuth)
    object SignIn : Screen(navigationRouteSignIn)

    object SignInWithEmail : Screen(navigationRouteSignInWithEmail)
    object SignUp : Screen(navigationRouteSignUp)


    object Profile :
        Screen(route = navigationRouteProfile, title = "Profile", icon = com.nsa.ui.R.drawable.profile_icon)

    object Subscription : Screen(navigationRouteSubscription)

    object PeopleList : Screen(navigationRoutePeopleList)
    object PeopleProfile : Screen(navigationRoutePeopleProfile)

}