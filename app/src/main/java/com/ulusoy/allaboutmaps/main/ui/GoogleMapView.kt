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

import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.util.AttributeSet
import androidx.annotation.ColorRes
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.*
import com.ulusoy.allaboutmaps.R
import com.ulusoy.allaboutmaps.domain.entities.LatLng
import com.ulusoy.allaboutmaps.main.extensions.toGoogleLatLng

class GoogleMapView
@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : MapView(context, attrs, defStyleAttr), AllAboutMapView {

    private var map: GoogleMap? = null

    override fun onMapViewCreate(savedInstanceState: Bundle?) { super.onCreate(savedInstanceState) }
    override fun onMapViewStart() { super.onStart() }
    override fun onMapViewStop() { super.onStop() }
    override fun onMapViewResume() { super.onResume() }
    override fun onMapViewPause() { super.onPause() }
    override fun onMapViewDestroy() { super.onDestroy() }
    override fun onMapViewSaveInstanceState(savedInstanceState: Bundle?) { super.onSaveInstanceState() }
    override fun onMapViewLowMemory() { super.onLowMemory() }

    fun onMapReady(map: GoogleMap) {
        this.map = map
        val mapStyleOptions =
            MapStyleOptions.loadRawResourceStyle(context, R.raw.google_maps_dark_style)
        map.setMapStyle(mapStyleOptions)
    }

    override fun drawPolyline(latLngs: List<LatLng>, @ColorRes mapLineColor: Int) {
        map?.addPolyline(
            PolylineOptions()
                .color(mapLineColor)
                .jointType(JointType.ROUND)
                .width(resources.getDimension(R.dimen.google_route_line_width_cut))
                .addAll(latLngs.map { it.toGoogleLatLng() })
        )
    }

    override fun drawMarker(latLng: LatLng, icon: Bitmap, name: String?) {
        var markerOptions = MarkerOptions()
            .icon(BitmapDescriptorFactory.fromBitmap(icon))
            .position(latLng.toGoogleLatLng())
        markerOptions = name?.run { markerOptions.title(this) }
        map?.addMarker(markerOptions)?.showInfoWindow()
    }
}
