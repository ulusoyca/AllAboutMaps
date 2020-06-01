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

inline class Latitude(val value: Float)
inline class Longitude(val value: Float)

data class LatLng(
    val latitude: Latitude,
    val longitude: Longitude
) {
    constructor(latitude: Double, longitude: Double) : this(
        Latitude(latitude.toFloat()),
        Longitude(longitude.toFloat())
    )

    val latDoubleValue: Double
        get() = latitude.value.toDouble()

    val lngDoubleValue: Double
        get() = longitude.value.toDouble()
}