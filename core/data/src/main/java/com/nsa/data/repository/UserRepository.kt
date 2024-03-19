package com.nsa.data.repository

import com.nsa.data.model.UserInfo

interface UserRepository {
    suspend fun getUserData():UserInfo

}