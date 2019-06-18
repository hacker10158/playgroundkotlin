package com.troy.playgroundkotlin.core.base.view

import android.support.v7.widget.RecyclerView
import com.troy.playgroundkotlin.core.base.model.UserData
import com.troy.playgroundkotlin.core.utility.FrescoHelper
import com.troy.playgroundkotlin.databinding.SearchUserItemBinding

class SearchUserViewHolder(var binding: SearchUserItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bindView(userData: UserData) {
        binding.tvName.text = userData.login
        FrescoHelper.loadInto(userData.avatar_url, binding.ivAvatar)
    }

}