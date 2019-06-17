package com.troy.playgroundkotlin.core.base.viewmodel

import com.troy.playgroundkotlin.core.utility.AutoDisposeViewModel
import com.troy.playgroundkotlin.core.utility.Log
import com.troy.playgroundkotlin.server.GitClientInterface
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class BaseViewModel(val gitClientInterface : GitClientInterface) : AutoDisposeViewModel() {

    public fun searchUsers() {
        val disposable = gitClientInterface.searchUsers("tom",1)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(onSuccess = {
                Log.d("5566 it total_count " + it.total_count)
            })

        addDisposable(disposable)
    }
}