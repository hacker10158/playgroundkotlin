package com.troy.playgroundkotlin.core.base.di

import android.arch.lifecycle.ViewModelProvider
import com.troy.playgroundkotlin.core.base.viewmodel.BaseViewModel
import com.troy.playgroundkotlin.core.utility.ViewModelProviderFactory
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class BaseModule {

    @Provides
    fun provideBaseViewModel(): BaseViewModel {
        return BaseViewModel()
    }

    @Provides
    @Named("base")
    internal fun provideBaseViewModelProviderFactory(viewModel: BaseViewModel): ViewModelProvider.Factory {
        return ViewModelProviderFactory(viewModel)
    }
}