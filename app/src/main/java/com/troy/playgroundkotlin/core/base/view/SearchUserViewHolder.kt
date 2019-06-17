package com.troy.playgroundkotlin.core.base.view

import android.support.v7.widget.RecyclerView
import android.view.View
import com.troy.playgroundkotlin.core.base.model.UserData
import com.troy.playgroundkotlin.databinding.LoadingMoreItemBinding
import com.troy.playgroundkotlin.databinding.SearchUserItemBinding

class SearchUserViewHolder(var binding: SearchUserItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bindView(userData: UserData) {
        binding.tvName.text = userData.login

//        FrescoHelper.loadInto(
//            imageData.getPreviewURL(),
//            binding.ivImage,
//            imageData.getWebformatWidth(),
//            imageData.getWebformatWidth()
//        )
    }

}