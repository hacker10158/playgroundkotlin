package com.troy.playgroundkotlin.core.rxjavademo


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.troy.playgroundkotlin.R
import com.troy.playgroundkotlin.core.utility.Log
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_rxjava_demo.*
import java.util.concurrent.TimeUnit

class RxJavaDemoFragment : Fragment() {

    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_rxjava_demo, container, false)
    }

    override fun onStart() {
        super.onStart()
        init()
    }

    private fun init() {
        val disposable = provideHelloWorldString()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy (
                onNext = {
                    tv_hello_world.text = it
                },
                onComplete = {

                },
                onError = {
                    Log.d("5566 " + it.message)
                    //
                    // Only the original thread that created a view hierarchy can touch its views.
                }
            )
        compositeDisposable.add(disposable)
    }

    private fun provideHelloWorldString(): Observable<String> {
        return Observable
            .just("Hello RxJava")
            .delay(2, TimeUnit.SECONDS)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }

    fun test() {

        val disposableObserver = object : DisposableObserver<String>() {
            override fun onComplete() {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onNext(t: String) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onError(e: Throwable) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        }

        val dis = Observable
            .just("Hello RxJava")
            .subscribe(disposableObserver)
        
    }
}
