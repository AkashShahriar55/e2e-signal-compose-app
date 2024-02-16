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
 * Profile
 *
 * Created on Feb 04, 2023.
 *
 */





data class PeopleProfile(
    val id: Int,
    val name: String,
    val status: Boolean,
    val photo: Uri? = null,
    val age:Int,
    val location:String,
    val about:String = "",
    val interest:List<String> = listOf(),
    val gallary:List<Uri> = listOf(),
    val distance:String = "0.0 km"
) {
    fun listKey(): String = "${id}_${name.replace("\\s+".toRegex(), "")}"
}


val fakePeopleProfileList = arrayListOf(
    PeopleProfile(
        id = 11,
        name = "Lily James",
        status = true,
        photo = Uri.parse("https://images.unsplash.com/flagged/photo-1571837360114-edf5dba7b1dd?q=80&w=1374&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"),
        29,
        "Spain",
        about = "Fun and adventurous. I'm not afraid to try new things and I love to be spontaneous. I want someone who is always up for an adventure, whether it's trying a new restaurant, going on a hike, or traveling to a new country.\n",
        interest = listOf("Art","Sports","Design","Photography","Coffee","Chocolates"),
        gallary = listOf(
            Uri.parse("https://images.unsplash.com/photo-1608312149553-d31a9cbc5224?q=80&w=1364&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"),
            Uri.parse("https://images.unsplash.com/photo-1563987219716-dac41f2d0b3a?q=80&w=1518&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"),
            Uri.parse("https://images.unsplash.com/photo-1567874250401-1a63635a1171?q=80&w=1527&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"),
            Uri.parse("https://images.unsplash.com/photo-1589042665482-aae96eac94af?q=80&w=1364&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"),
            Uri.parse("https://images.unsplash.com/photo-1591167068512-e96853b5a458?q=80&w=1365&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"),
            Uri.parse("https://images.unsplash.com/photo-1589220840325-df26ed81a09f?q=80&w=1364&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"),
            Uri.parse("https://images.unsplash.com/photo-1589220840325-df26ed81a09f?q=80&w=1364&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"),
//            Uri.parse("https://images.unsplash.com/photo-1589220840325-df26ed81a09f?q=80&w=1364&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D")
        ),
        distance = "2.3 km"
    ),
    PeopleProfile(
        id = 0,
        name = "Michaela Runnings",
        status = true,
        Uri.parse("https://images.unsplash.com/photo-1485290334039-a3c69043e517?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=800&q=80"),
        21,
        "Spain"
    ),
    PeopleProfile(
        id = 1,
        name = "John Pestridge",
        status = false,
        Uri.parse("https://images.unsplash.com/photo-1542178243-bc20204b769f?ixid=MXwxMjA3fDB8MHxzZWFyY2h8MTB8fHBvcnRyYWl0fGVufDB8MnwwfA%3D%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60"),
        19,
        "Madrid"
    ),
    PeopleProfile(
        id = 2,
        name = "Manilla Andrews",
        status = true,
        Uri.parse("https://images.unsplash.com/photo-1543123820-ac4a5f77da38?ixid=MXwxMjA3fDB8MHxzZWFyY2h8NDh8fHBvcnRyYWl0fGVufDB8MnwwfA%3D%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60"),
        24,
        "Italy"
    ),
    PeopleProfile(
        id = 3,
        name = "Dan Spicer",
        status = false,
        Uri.parse("https://images.unsplash.com/photo-1595152772835-219674b2a8a6?ixid=MXwxMjA3fDB8MHxzZWFyY2h8NDd8fHBvcnRyYWl0fGVufDB8MnwwfA%3D%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60"),
        29,
        "Germany"
    ),
    PeopleProfile(
        id = 4,
        name = "Keanu Dester",
        status = false,
        Uri.parse("https://images.unsplash.com/photo-1597528380214-aa94bde3fc32?ixid=MXwxMjA3fDB8MHxzZWFyY2h8NTZ8fHBvcnRyYWl0fGVufDB8MnwwfA%3D%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60"),
        23,
        "India"
    ),
    PeopleProfile(
        id = 5,
        name = "Anichu Patel",
        status = false,
        Uri.parse("https://images.unsplash.com/photo-1598641795816-a84ac9eac40c?ixid=MXwxMjA3fDB8MHxzZWFyY2h8NjJ8fHBvcnRyYWl0fGVufDB8MnwwfA%3D%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60"),
        31,
        "Rome"
    ),
    PeopleProfile(
        id = 6,
        name = "Kienla Onso",
        status = true,
        Uri.parse("https://images.unsplash.com/photo-1566895733044-d2bdda8b6234?ixid=MXwxMjA3fDB8MHxzZWFyY2h8ODh8fHBvcnRyYWl0fGVufDB8MnwwfA%3D%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60"),
        18,
        "Spain"
    ),
    PeopleProfile(
        id = 7,
        name = "Andra Matthews",
        status = false,
        Uri.parse("https://images.unsplash.com/photo-1530577197743-7adf14294584?ixid=MXwxMjA3fDB8MHxzZWFyY2h8NTV8fHBvcnRyYWl0fGVufDB8MnwwfA%3D%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60"),
        21,
        "Spain"
    ),
    PeopleProfile(
        id = 8,
        name = "Georgia S.", status = false,
        Uri.parse("https://images.unsplash.com/photo-1547212371-eb5e6a4b590c?ixid=MXwxMjA3fDB8MHxzZWFyY2h8MTA3fHxwb3J0cmFpdHxlbnwwfDJ8MHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60"),
        22,
        "Russia"
    ),
    PeopleProfile(
        id = 12,
        name = "Matt Dengo",
        status = false,
        Uri.parse("https://images.unsplash.com/photo-1578176603894-57973e38890f?ixid=MXwxMjA3fDB8MHxzZWFyY2h8MTE0fHxwb3J0cmFpdHxlbnwwfDJ8MHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60"),
        25,
        "Ukraine"
    ),
    PeopleProfile(
        id = 9,
        name = "Marsha T.",
        status = true,
        Uri.parse("https://images.unsplash.com/photo-1605087880595-8cc6db61f3c6?ixid=MXwxMjA3fDB8MHxzZWFyY2h8MTI0fHxwb3J0cmFpdHxlbnwwfDJ8MHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60"),
        20,
        "Singapore"
    ),
    PeopleProfile(
        id = 10,
        name = "Invshu Patel",
        status = true,
        Uri.parse("https://images.unsplash.com/photo-1561820009-8bef03ebf8e5?ixid=MXwxMjA3fDB8MHxzZWFyY2h8MTM3fHxwb3J0cmFpdHxlbnwwfDJ8MHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60"),
        29,
        "China"
    )
)

fun List<PeopleProfile>.toChatName(): String =
    mapTo(ArrayList<String>()) { profile -> profile.name }.let { names ->
        val stringBuilder = StringBuilder()
        names.onEachIndexed { index, s ->
            if (index > 0)
                stringBuilder.append(", ")
            stringBuilder.append(s)
        }
        stringBuilder.toString()
    }