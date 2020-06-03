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

package com.ulusoy.allaboutmaps.main.extensions

import com.ulusoy.allaboutmaps.domain.entities.LatLng
import com.ulusoy.allaboutmaps.domain.entities.LatLngBounds
import com.ulusoy.allaboutmaps.main.common.SphericalUtil.computeOffset
import kotlin.math.sqrt

/**
 * Our goal is to calculate two points (LatLngs): southwestCorner and northeastCorner.
 * 225 and 45 are heading values, and the distanceFromCenterToCorner is the distance.
 */
fun LatLng.toBounds(radiusInMeters: Double): LatLngBounds {
    val distanceFromCenterToCorner = radiusInMeters * sqrt(2.0)
    val southwestCorner =
        computeOffset(this, distanceFromCenterToCorner, 225.0)
    val northeastCorner =
        computeOffset(this, distanceFromCenterToCorner, 45.0)
    return LatLngBounds(southwestCorner, northeastCorner)
}
