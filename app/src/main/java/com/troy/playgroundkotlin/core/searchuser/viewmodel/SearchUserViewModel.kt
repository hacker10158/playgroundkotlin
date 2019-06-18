package com.troy.playgroundkotlin.core.searchuser.viewmodel

import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import android.databinding.ObservableInt
import android.text.TextUtils
import com.troy.playgroundkotlin.core.searchuser.model.UserData
import com.troy.playgroundkotlin.core.utility.AutoDisposeViewModel
import com.troy.playgroundkotlin.core.utility.Log
import com.troy.playgroundkotlin.server.ErrorCode
import com.troy.playgroundkotlin.server.GitClientInterface
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import java.util.concurrent.TimeUnit

class SearchUserViewModel(gitClientInterface : GitClientInterface) : AutoDisposeViewModel() {

    var newItems = ObservableField<ArrayList<UserData>>()
    var searchText = ObservableField<String>()
    var loadingMore = ObservableBoolean(false)
    var errorCode = ObservableInt(ErrorCode.ERROR_CODE_DEFAULT)

    private var gitClient : GitClientInterface = gitClientInterface
    private lateinit var scrollSubject: Subject<Int>
    private var page = 1
    private var keyword : String? = null

    init {
        setupScrollSubject()
    }

    fun onSearchClick() {
        errorCode.set(ErrorCode.ERROR_CODE_DEFAULT)

        if(TextUtils.isEmpty(searchText.get())) {
            errorCode.set(ErrorCode.ERROR_CODE_EMPTY_KEYWORD)
            return
        }

        keyword = searchText.get()
        newItems.set(null)
        loadingMore.set(false)
        page = 1

        search()
    }

    private fun search() {

        if (loadingMore.get()) {
            Log.d("is loading more")
            return
        }

        loadingMore.set(true)

        val disposable = gitClient.searchUsers(keyword!!,page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(onSuccess = {
                if (it.items.isEmpty()) {
                    loadingMore.set(false)
                    if (page == 1) {
                        errorCode.set(ErrorCode.ERROR_CODE_NO_RESULT)
                    }
                } else {
                    page++
                    newItems.set(it.items)
                    loadingMore.set(false)
                }
            },
                onError = {
                    loadingMore.set(false)
                    errorCode.set(ErrorCode.ERROR_CODE_UNKNOWN)
                    Log.e("Error on search user. Throwable: $it")
                })

        addDisposable(disposable)
    }

    fun loadNextPage() {
        scrollSubject.onNext(0)
    }

    private fun setupScrollSubject() {
        scrollSubject = PublishSubject.create<Int>().toSerialized()
        val disposable = scrollSubject.throttleFirst(300, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(onNext = {
                search()
            }, onError = {
                errorCode.set(ErrorCode.ERROR_CODE_UNKNOWN)
                Log.e("Error on loading more. Throwable: $it")
            })

        addDisposable(disposable)
    }
}