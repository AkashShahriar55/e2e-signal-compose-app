package com.nsa.domain.model

import android.net.Uri

data class StoryData(
    val storyId:Int,
    val name:String,
    val photo: Uri?,
    val status:Boolean,
    val stories:List<Story>
)


var fakeStoriesData = listOf(
    StoryData(
        storyId = 0,
        name = fakePeopleProfileList[0].name,
        photo = fakePeopleProfileList[0].photo,
        status = fakePeopleProfileList[0].status,
        stories = listOf(
            Story(
                fakePeopleProfileList[0].photo
            )
        )
    ),
    StoryData(
        storyId = 1,
        name = fakePeopleProfileList[1].name,
        photo = fakePeopleProfileList[1].photo,
        status = fakePeopleProfileList[1].status,
        stories = listOf(
            Story(
                fakePeopleProfileList[1].photo
            )
        )
    ),
    StoryData(
        storyId = 2,
        name = fakePeopleProfileList[2].name,
        photo = fakePeopleProfileList[2].photo,
        status = fakePeopleProfileList[2].status,
        stories = listOf(
            Story(
                fakePeopleProfileList[2].photo
            )
        )
    ),
    StoryData(
        storyId = 3,
        name = fakePeopleProfileList[3].name,
        photo = fakePeopleProfileList[3].photo,
        status = fakePeopleProfileList[3].status,
        stories = listOf(
            Story(
                fakePeopleProfileList[3].photo
            )
        )
    ),
    StoryData(
        storyId = 4,
        name = fakePeopleProfileList[4].name,
        photo = fakePeopleProfileList[4].photo,
        status = fakePeopleProfileList[4].status,
        stories = listOf(
            Story(
                fakePeopleProfileList[4].photo
            )
        )
    ),
    StoryData(
        storyId = 5,
        name = fakePeopleProfileList[5].name,
        photo = fakePeopleProfileList[5].photo,
        status = fakePeopleProfileList[5].status,
        stories = listOf(
            Story(
                fakePeopleProfileList[5].photo
            )
        )
    ),
    StoryData(
        storyId = 6,
        name = fakePeopleProfileList[6].name,
        photo = fakePeopleProfileList[6].photo,
        status = fakePeopleProfileList[6].status,
        stories = listOf(
            Story(
                fakePeopleProfileList[6].photo
            )
        )
    )
)
