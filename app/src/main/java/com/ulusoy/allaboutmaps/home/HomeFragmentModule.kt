package com.ulusoy.allaboutmaps.home

import com.ulusoy.allaboutmaps.FragmentScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class HomeFragmentModule {
    @FragmentScope
    @ContributesAndroidInjector(modules = [HomeModule::class])
    abstract fun bindInfinitiveFragment(): HomeFragment
}
