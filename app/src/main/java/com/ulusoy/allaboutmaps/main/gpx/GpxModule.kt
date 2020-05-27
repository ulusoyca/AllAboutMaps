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

package com.ulusoy.allaboutmaps.main.gpx

import android.content.Context
import android.graphics.Bitmap
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.ViewModel
import com.ulusoy.allaboutmaps.FragmentScope
import com.ulusoy.allaboutmaps.R
import com.ulusoy.allaboutmaps.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Named

const val NAMED_FOOD_STATION_BITMAP = "NAMED_FOOD_STATION_BITMAP"
const val NAMED_MAP_LINE_COLOR = "NAMED_MAP_LINE_COLOR"

@Module
abstract class GpxModule {
    @FragmentScope
    @Binds
    @IntoMap
    @ViewModelKey(GpxViewModel::class)
    abstract fun bindViewModel(viewModel: GpxViewModel): ViewModel

    companion object {
        @Provides
        @Named(NAMED_FOOD_STATION_BITMAP)
        fun provideFoodStationBitmap(context: Context): Bitmap =
            ContextCompat.getDrawable(context, R.drawable.ic_food_white)!!.toBitmap()

        @Provides
        @Named(NAMED_MAP_LINE_COLOR)
        @ColorInt
        fun provideMapLineColor(context: Context): Int =
            ContextCompat.getColor(context, R.color.map_route_cut_line_color)
    }
}
