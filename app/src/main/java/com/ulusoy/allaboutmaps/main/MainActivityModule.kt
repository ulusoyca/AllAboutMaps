package com.ulusoy.allaboutmaps.main


import com.ulusoy.allaboutmaps.ActivityScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainActivityModule {
    @ActivityScope
    @ContributesAndroidInjector(
        modules = [
            MainModule::class
        ]
    )
    abstract fun contributeMainActivityInjector(): MainActivity
}
