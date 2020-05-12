package com.ulusoy.allaboutmaps

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import timber.log.Timber

class AllAboutMapsApp : DaggerApplication() {
    private val appComponent: AndroidInjector<AllAboutMapsApp> by lazy {
        DaggerAppComponent.factory().create(this)
    }

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return appComponent
    }
}