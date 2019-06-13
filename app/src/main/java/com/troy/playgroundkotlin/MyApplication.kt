package com.troy.playgroundkotlin

import android.content.Context
import com.troy.playgroundkotlin.core.utility.Log
import com.troy.playgroundkotlin.di.AppComponent
import com.troy.playgroundkotlin.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class MyApplication : DaggerApplication() {

    private var appComponent: AppComponent? = null

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)

    }

    override fun onCreate() {
        super.onCreate()
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