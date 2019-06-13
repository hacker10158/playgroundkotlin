package com.troy.playgroundkotlin.di.builder

import com.troy.playgroundkotlin.core.base.BaseFragment
import com.troy.playgroundkotlin.core.base.di.BaseModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainFragmentProvider {

    @ContributesAndroidInjector(modules = [BaseModule::class])
    internal abstract fun provideBaseFragment(): BaseFragment
}
