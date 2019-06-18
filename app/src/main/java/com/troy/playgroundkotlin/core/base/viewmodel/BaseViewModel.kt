package com.troy.playgroundkotlin.core.base.viewmodel

import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.troy.playgroundkotlin.core.base.model.UserData
import com.troy.playgroundkotlin.core.utility.AutoDisposeViewModel
import com.troy.playgroundkotlin.core.utility.Log
import com.troy.playgroundkotlin.server.GitClientInterface
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import java.util.concurrent.TimeUnit

class BaseViewModel(gitClientInterface : GitClientInterface) : AutoDisposeViewModel() {
    private val PRELOAD_COUNT = 15

    var newItems = ObservableField<ArrayList<UserData>>()
    var searchText = ObservableField<String>()
    var loadingMore = ObservableBoolean(false)

    private var gitClient : GitClientInterface = gitClientInterface
    private lateinit var scrollSubject: Subject<Int>
    private var page = 1
    private var keyword : String? = null
    private var isStartLoading = false

    init {
        setupScrollSubject()
    }

    public fun onSearchClick() {
        keyword = searchText.get()
        newItems.set(null)
        isStartLoading = true
        loadingMore.set(false)
        page = 1
        search()
    }

    private fun search() {
        if (!isStartLoading) {
            Log.d("not allow start loading")
            return
        }

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
                    isStartLoading = false
                    loadingMore.set(false)
                } else {
                    page++
                    newItems.set(it.items)
                    loadingMore.set(false)
                }
            },
                onError = {
                    isStartLoading = false
                    loadingMore.set(false)
                    Log.d("Error on loading $it")
                })

        addDisposable(disposable)

    }

    private fun setupScrollSubject() {
        scrollSubject = PublishSubject.create<Int>().toSerialized()
        val disposable = scrollSubject.throttleFirst(300, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(onNext = {
                search()
            }, onError = {
                Log.e("Error on loading more. Throwable: $it")
            })

        addDisposable(disposable)
    }

    fun createScrollListener(): RecyclerView.OnScrollListener {
        return object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val totalCount = recyclerView!!.layoutManager.itemCount
                val lastPosition: Int
                if (recyclerView.layoutManager is GridLayoutManager) {
                    lastPosition = (recyclerView.layoutManager as GridLayoutManager).findLastVisibleItemPosition()
                } else if (recyclerView.layoutManager is LinearLayoutManager) {
                    lastPosition = (recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                } else {
                    val lastChildView = recyclerView.layoutManager.getChildAt(recyclerView.layoutManager.childCount - 1)
                    if (lastChildView == null) {
                        Log.w("lastChildView null") // what this mean?
                        return
                    }
                    lastPosition = recyclerView.layoutManager.getPosition(lastChildView)
                }
                Log.d("lastPosition $lastPosition totalCount $totalCount")

                if (dy > 0 && lastPosition >= totalCount - PRELOAD_COUNT) {
                    scrollSubject.onNext(0)
                }
            }
        }
    }
}