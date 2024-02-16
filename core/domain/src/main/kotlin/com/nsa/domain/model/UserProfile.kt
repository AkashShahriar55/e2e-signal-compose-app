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
package com.nsa.domain.model

import android.net.Uri

/**
 * App User Profile
 *
 * Created on Feb 04, 2023.
 *
 */

data class UserProfile(
    val name : String,
    val nickname: String,
    val status: Boolean = false,
    val photo : Uri = Uri.EMPTY
)


val fakeUserProfile = UserProfile(
    name = "Pranav Ray",
    nickname = "Pranav",
    status = true,
    photo =   Uri.parse("https://images.unsplash.com/photo-1595152772835-219674b2a8a6?ixid=MXwxMjA3fDB8MHxzZWFyY2h8NDd8fHBvcnRyYWl0fGVufDB8MnwwfA%3D%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60"),
)
