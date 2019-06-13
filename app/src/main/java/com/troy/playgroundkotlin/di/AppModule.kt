package com.troy.playgroundkotlin.di

import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    internal fun provideContext(application: Application): Context {
        return application
    }

    @Provides
    @Singleton
    internal fun providePackageManager(context: Context): PackageManager {
        return context.packageManager
    }

    @Provides
    @Singleton
    @Named("versionName")
    internal fun provideVersionName(context: Context, packageManager: PackageManager): String {
        var version = ""
        try {
            version = packageManager.getPackageInfo(context.packageName, 0).versionName
        } catch (ignore: PackageManager.NameNotFoundException) {
        }

        return version
    }
}

