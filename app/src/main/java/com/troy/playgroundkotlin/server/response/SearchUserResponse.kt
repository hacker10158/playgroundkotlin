package com.troy.playgroundkotlin.server.response

import com.troy.playgroundkotlin.core.base.model.UserData

data class SearchUserResponse(val total_count : Int,
                              val incomplete_results : Boolean,
                              val items : List<UserData>)