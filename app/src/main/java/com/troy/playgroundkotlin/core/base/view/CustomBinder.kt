package com.troy.playgroundkotlin.core.base.view

import android.databinding.BindingAdapter
import android.support.v7.widget.RecyclerView
import com.troy.playgroundkotlin.core.base.model.UserData

@BindingAdapter("android:userdata")
fun RecyclerView.updateData(items : ArrayList<UserData>?) {
    if(adapter is SearchUserAdapter) {
        (adapter as SearchUserAdapter).addData(items)
    }
}

@BindingAdapter("android:loadingmore")
fun RecyclerView.showLoadingMore(show : Boolean) {
    if(adapter is SearchUserAdapter) {
        (adapter as SearchUserAdapter).setLoadingMore(show)
    }
}