package com.troy.playgroundkotlin.core.searchuser.view

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.troy.playgroundkotlin.core.searchuser.model.UserData
import com.troy.playgroundkotlin.databinding.LoadingMoreItemBinding
import com.troy.playgroundkotlin.databinding.SearchUserItemBinding
import java.util.ArrayList


class SearchUserAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TYPE_IMAGE = 0x0001
    private val TYPE_LOADING = 0x0002

    private var dataList = ArrayList<UserData>()
    private var showLoadingMore: Boolean = false

    private val loadingIndicatorSize = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        when (viewType) {
            TYPE_LOADING -> return LoadingMoreViewHolder(LoadingMoreItemBinding.inflate(layoutInflater, parent, false))
            TYPE_IMAGE -> return SearchUserViewHolder(SearchUserItemBinding.inflate(layoutInflater, parent, false))
        }

        return LoadingMoreViewHolder(LoadingMoreItemBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is SearchUserViewHolder) {
            holder.bindView(dataList[position])
        }

        if (holder is LoadingMoreViewHolder) {
            holder.bindView(showLoadingMore)
        }
    }

    override fun getItemCount(): Int {
        return dataList.size + loadingIndicatorSize
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == itemCount - 1) {
            TYPE_LOADING
        } else TYPE_IMAGE
    }

    fun cleanData() {
        dataList.clear()
        notifyDataSetChanged()
    }

    fun addData(userDatas : ArrayList<UserData>?) {
        userDatas?.let {

            val start = dataList.size
            dataList.addAll(it)
            if (start == 0) {
                notifyDataSetChanged()
            } else {
                notifyItemRangeInserted(start, userDatas.size)
            }
        }
    }

    fun setLoadingMore(loadingMore: Boolean) {
        showLoadingMore = loadingMore
        notifyItemChanged(itemCount - 1)
    }
}