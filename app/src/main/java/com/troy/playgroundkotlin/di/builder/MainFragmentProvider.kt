package com.troy.playgroundkotlin.di.builder

import com.troy.playgroundkotlin.core.searchuser.SearchUserFragment
import com.troy.playgroundkotlin.core.searchuser.di.SearchUserModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainFragmentProvider {

    @ContributesAndroidInjector(modules = [SearchUserModule::class])
    internal abstract fun provideSearchUserFragment(): SearchUserFragment
}
