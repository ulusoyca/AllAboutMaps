package com.ulusoy.allaboutmaps.home

import android.content.Context
import androidx.lifecycle.ViewModel
import com.ulusoy.allaboutmaps.FragmentScope
import com.ulusoy.allaboutmaps.R
import com.ulusoy.allaboutmaps.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Named

@Module
abstract class HomeModule {
    @FragmentScope
    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    /* Note: the return type should be ViewModel */
    abstract fun bindViewModel(viewModel: HomeViewModel): ViewModel
}
