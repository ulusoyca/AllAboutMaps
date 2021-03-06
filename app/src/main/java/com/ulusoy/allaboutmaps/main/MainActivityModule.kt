/*
 * Copyright 2020 Cagatay Ulusoy (Ulus Oy Apps). All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ulusoy.allaboutmaps.main

import com.ulusoy.allaboutmaps.ActivityScope
import com.ulusoy.allaboutmaps.main.camerabound.CameraBoundFragmentModule
import com.ulusoy.allaboutmaps.main.camerabound.google.CameraBoundGoogleFragmentModule
import com.ulusoy.allaboutmaps.main.camerabound.huawei.CameraBoundHuaweiFragmentModule
import com.ulusoy.allaboutmaps.main.camerabound.mapbox.CameraBoundMapboxFragmentModule
import com.ulusoy.allaboutmaps.main.gesture.GestureFragmentModule
import com.ulusoy.allaboutmaps.main.gesture.google.GestureGoogleFragmentModule
import com.ulusoy.allaboutmaps.main.gesture.huawei.GestureHuaweiFragmentModule
import com.ulusoy.allaboutmaps.main.gesture.mapbox.GestureMapboxFragmentModule
import com.ulusoy.allaboutmaps.main.home.HomeFragmentModule
import com.ulusoy.allaboutmaps.main.routeinfo.RouteInfoFragmentModule
import com.ulusoy.allaboutmaps.main.routeinfo.google.RouteInfoGoogleFragmentModule
import com.ulusoy.allaboutmaps.main.routeinfo.huawei.RouteInfoHuaweiFragmentModule
import com.ulusoy.allaboutmaps.main.routeinfo.mapbox.RouteInfoMapboxFragmentModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainActivityModule {
    @ActivityScope
    @ContributesAndroidInjector(
        modules = [
            HomeFragmentModule::class,
            RouteInfoFragmentModule::class,
            CameraBoundFragmentModule::class,
            MainModule::class,
            RouteInfoMapboxFragmentModule::class,
            RouteInfoHuaweiFragmentModule::class,
            RouteInfoGoogleFragmentModule::class,
            CameraBoundGoogleFragmentModule::class,
            CameraBoundHuaweiFragmentModule::class,
            CameraBoundMapboxFragmentModule::class,
            GestureGoogleFragmentModule::class,
        GestureMapboxFragmentModule::class,
            GestureHuaweiFragmentModule::class,
            GestureFragmentModule::class
        ]
    )
    abstract fun contributeMainActivityInjector(): MainActivity
}
