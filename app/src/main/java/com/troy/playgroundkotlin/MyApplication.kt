package com.troy.playgroundkotlin

import android.content.Context
import com.facebook.drawee.backends.pipeline.Fresco
import com.troy.playgroundkotlin.di.AppComponent
import com.troy.playgroundkotlin.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class MyApplication : DaggerApplication() {

    private var appComponent: AppComponent? = null

    override fun onCreate() {
        super.onCreate()
        Fresco.initialize(this)
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        appComponent = DaggerAppComponent.builder().application(this).build()
        appComponent!!.inject(this)
        return appComponent!!
    }

    companion object {

        fun injector(context: Context): AppComponent? {
            return (context.applicationContext as MyApplication).appComponent
        }
    }

}