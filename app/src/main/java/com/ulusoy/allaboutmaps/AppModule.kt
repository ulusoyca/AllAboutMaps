package com.ulusoy.allaboutmaps

import android.content.Context
import com.ulusoy.allaboutmaps.AllAboutMapsApp
import dagger.Binds
import dagger.Module

/**
 * Application module refers to sub components and provides application level dependencies.
 */
@Module
internal abstract class AppModule {
    @Binds
    abstract fun provideContext(application: AllAboutMapsApp): Context
}
