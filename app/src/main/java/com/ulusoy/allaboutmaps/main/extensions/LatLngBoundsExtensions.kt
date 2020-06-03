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

import com.google.android.gms.maps.model.LatLngBounds as GoogleLatLngBounds
import com.huawei.hms.maps.model.LatLngBounds as HuaweiLatLngBounds
import com.mapbox.mapboxsdk.geometry.LatLngBounds as MapboxLatLngBounds
import com.ulusoy.allaboutmaps.domain.entities.LatLngBounds

fun LatLngBounds.toGoogleLatLngBounds() = GoogleLatLngBounds(
    this.southwestCorner.toGoogleLatLng(),
    northeastCorner.toGoogleLatLng()
)

fun LatLngBounds.toHuaweiLatLngBounds() = HuaweiLatLngBounds(
    southwestCorner.toHuaweiLatLng(),
    northeastCorner.toHuaweiLatLng()
)

fun LatLngBounds.toMapbpoxLatLngBounds() = MapboxLatLngBounds.from(
    northeastCorner.latDoubleValue,
    northeastCorner.lngDoubleValue,
    southwestCorner.latDoubleValue,
    southwestCorner.lngDoubleValue
)
