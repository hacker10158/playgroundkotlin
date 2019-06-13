package com.troy.playgroundkotlin.di

import android.app.Application
import com.troy.playgroundkotlin.MyApplication
import com.troy.playgroundkotlin.di.builder.ActivityBuilder
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidSupportInjectionModule::class, AppModule::class, NetworkModule::class, ActivityBuilder::class])
interface AppComponent : AndroidInjector<MyApplication> {

    override fun inject(app: MyApplication)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}
