package com.troy.playgroundkotlin.core.searchuser.view

import android.support.v7.widget.RecyclerView
import android.view.View
import com.troy.playgroundkotlin.databinding.LoadingMoreItemBinding

class LoadingMoreViewHolder(var binding: LoadingMoreItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bindView(showLoadingMore: Boolean) {
        if (showLoadingMore) {
            binding.pbLoadingMore.visibility = View.VISIBLE
        } else {
            binding.pbLoadingMore.visibility = View.GONE
        }
    }

}