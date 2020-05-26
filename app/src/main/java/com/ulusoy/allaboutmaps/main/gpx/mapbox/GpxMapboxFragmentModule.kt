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

package com.ulusoy.allaboutmaps.main.gpx.mapbox

import android.content.Context
import androidx.core.content.ContextCompat
import com.mapbox.mapboxsdk.plugins.annotation.LineOptions
import com.mapbox.mapboxsdk.style.layers.Property
import com.mapbox.mapboxsdk.utils.ColorUtils
import com.ulusoy.allaboutmaps.FragmentScope
import com.ulusoy.allaboutmaps.R
import com.ulusoy.allaboutmaps.main.gpx.GpxModule
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector

@Module
abstract class GpxMapboxFragmentModule {
    @FragmentScope
    @ContributesAndroidInjector(modules = [GpxModule::class])
    abstract fun bindConjugationFragment(): GpxMapboxFragment

    companion object {
        @Provides
        fun provideLineOptions(context: Context): LineOptions = LineOptions().withLineJoin(Property.LINE_JOIN_ROUND)
            .withLineColor(
                ColorUtils.colorToRgbaString(
                    ContextCompat.getColor(context, R.color.map_route_cut_line_color)
                )
            )
            .withLineWidth(context.resources.getDimension(R.dimen.route_line_width_cut))
    }
}
