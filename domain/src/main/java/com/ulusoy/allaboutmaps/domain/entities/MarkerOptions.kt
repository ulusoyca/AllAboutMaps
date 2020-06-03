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

package com.ulusoy.allaboutmaps.domain.entities

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import com.ulusoy.allaboutmaps.domain.R

data class MarkerOptions(
    var latLng: LatLng,
    var text: String? = null,
    @DrawableRes var iconResId: Int,
    var iconMapStyleId: String,
    @ColorRes var iconColor: Int = R.color.black,
    @ColorRes var textColor: Int = R.color.white,
    var iconAlpha: Float = 1f,
    var textAlpha: Float = 1f
)