package com.troy.playgroundkotlin.di

import android.content.Context
import com.troy.playgroundkotlin.server.GitClient
import com.troy.playgroundkotlin.server.GitClientInterface
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    internal fun provideGitClientInterface(context: Context): GitClientInterface {
        return GitClient(context)
    }
}