package com.troy.playgroundkotlin.di.builder

import com.troy.playgroundkotlin.MainActivity
import com.troy.playgroundkotlin.di.MainScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

    @MainScope
    @ContributesAndroidInjector(modules = [MainFragmentProvider::class])
    internal abstract fun bindMainActivity(): MainActivity

}
