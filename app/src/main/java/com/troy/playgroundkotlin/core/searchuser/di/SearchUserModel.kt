package com.troy.playgroundkotlin.core.searchuser.di

import android.arch.lifecycle.ViewModelProvider
import com.troy.playgroundkotlin.core.searchuser.view.SearchUserAdapter
import com.troy.playgroundkotlin.core.searchuser.viewmodel.SearchUserViewModel
import com.troy.playgroundkotlin.core.utility.ViewModelProviderFactory
import com.troy.playgroundkotlin.server.GitClientInterface
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class SearchUserModule {

    @Provides
    fun provideBaseViewModel(gitClientInterface: GitClientInterface): SearchUserViewModel {
        return SearchUserViewModel(gitClientInterface)
    }

    @Provides
    fun provideSearchUserAdapter(): SearchUserAdapter {
        return SearchUserAdapter()
    }

    @Provides
    @Named("search")
    internal fun provideSearchUserViewModelProviderFactory(viewModel: SearchUserViewModel): ViewModelProvider.Factory {
        return ViewModelProviderFactory(viewModel)
    }
}