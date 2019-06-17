package com.troy.playgroundkotlin.server

import com.troy.playgroundkotlin.server.response.SearchUserResponse
import io.reactivex.Single

interface GitClientInterface {
    fun searchUsers(keyword: String, page: Int): Single<SearchUserResponse>
}