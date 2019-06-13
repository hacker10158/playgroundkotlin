package com.troy.playgroundkotlin.core.utility

import android.arch.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class AutoDisposeViewModel : ViewModel() {
    private val compositeDisposable: CompositeDisposable

    init {
        compositeDisposable = CompositeDisposable()
    }

    protected fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        if (!compositeDisposable.isDisposed()) {
            compositeDisposable.dispose()
        }
    }
}