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

package com.ulusoy.allaboutmaps.main.ui

import android.os.Bundle
import com.ulusoy.allaboutmaps.domain.entities.LatLng
import com.ulusoy.allaboutmaps.domain.entities.LatLngBounds
import com.ulusoy.allaboutmaps.domain.entities.LineOptions
import com.ulusoy.allaboutmaps.domain.entities.MarkerOptions

interface AllAboutMapView {
    fun onMapViewCreate(savedInstanceState: Bundle?)
    fun onMapViewStart()
    fun onMapViewStop()
    fun onMapViewResume()
    fun onMapViewPause()
    fun onMapViewDestroy()
    fun onMapViewSaveInstanceState(savedInstanceState: Bundle?)
    fun onMapViewLowMemory()
    fun drawPolyline(lineOptions: LineOptions)
    fun moveCamera(latLng: LatLng)
    fun moveCamera(latLngBounds: LatLngBounds, padding: Int = 0)
    fun setMapPadding(left: Int = 0, right: Int = 0, top: Int = 0, bottom: Int = 0)
    fun drawMarker(markerOptions: MarkerOptions)
}
