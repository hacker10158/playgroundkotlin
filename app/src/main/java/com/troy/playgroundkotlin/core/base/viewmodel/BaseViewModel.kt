package com.troy.playgroundkotlin.core.base.viewmodel

import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import com.troy.playgroundkotlin.core.base.model.UserData
import com.troy.playgroundkotlin.core.utility.AutoDisposeViewModel
import com.troy.playgroundkotlin.core.utility.Log
import com.troy.playgroundkotlin.server.GitClientInterface
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class BaseViewModel(gitClientInterface : GitClientInterface) : AutoDisposeViewModel() {
    private var gitClient : GitClientInterface = gitClientInterface

    public var items = ObservableField<List<UserData>>()
    public var loadingMore = ObservableBoolean(false)

    public fun searchUsers() {
        loadingMore.set(true)
        val disposable = gitClient.searchUsers("hacker",0)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(onSuccess = {
                Log.d("5566 it total_count " + it.total_count)
                items.set(it.items)
                loadingMore.set(false)
            })

        addDisposable(disposable)
    }
}