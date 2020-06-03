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
import android.os.Bundle
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng as GoogleLatLang
import com.google.android.gms.maps.model.LatLngBounds as GoogleLatLangBounds
import com.google.android.gms.maps.model.MarkerOptions as GoogleMarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.ulusoy.allaboutmaps.domain.entities.LatLng
import com.ulusoy.allaboutmaps.domain.entities.LatLngBounds
import com.ulusoy.allaboutmaps.domain.entities.LineOptions
import com.ulusoy.allaboutmaps.domain.entities.MarkerOptions as DomainMarkerOptions

class GoogleMapView
@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : MapView(context, attrs, defStyleAttr), AllAboutMapView {

    private var map: GoogleMap? = null

    override fun onMapViewCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onMapViewStart() {
        super.onStart()
    }

    override fun onMapViewStop() {
        super.onStop()
    }

    override fun onMapViewResume() {
        super.onResume()
    }

    override fun onMapViewPause() {
        super.onPause()
    }

    override fun onMapViewDestroy() {
        super.onDestroy()
    }

    override fun onMapViewSaveInstanceState(savedInstanceState: Bundle?) {
        super.onSaveInstanceState()
    }

    override fun onMapViewLowMemory() {
        super.onLowMemory()
    }

    override fun drawPolyline(lineOptions: LineOptions) {
        map?.addPolyline(lineOptions.toGoogleLineOptions(context))
    }

    fun onMapReady(map: GoogleMap) {
        this.map = map
    }

    override fun moveCamera(latLng: LatLng) {
        throw NotImplementedError()
    }

    override fun moveCamera(latLngBounds: LatLngBounds, padding: Int) {
        map?.moveCamera(
            CameraUpdateFactory.newLatLngBounds(
                latLngBounds.toGoogleLatLngBounds(), padding
            )
        )
    }

    override fun setMapPadding(left: Int, right: Int, top: Int, bottom: Int) {
        map?.setPadding(left, right, top, bottom)
    }

    override fun drawMarker(markerOptions: DomainMarkerOptions) {
        map?.addMarker(markerOptions.toGoogleMarkerOptions())?.showInfoWindow()
    }

    private fun LatLng.toGoogleLatLng() = GoogleLatLang(
        latitude.value.toDouble(),
        longitude.value.toDouble()
    )

    private fun DomainMarkerOptions.toGoogleMarkerOptions(): GoogleMarkerOptions {
        var markerOptions = GoogleMarkerOptions()
            .icon(BitmapDescriptorFactory.fromResource(iconResId))
            .position(latLng.toGoogleLatLng())
            .alpha(iconAlpha)
        markerOptions = text?.let { markerOptions.title(it) } ?: markerOptions
        return markerOptions
    }

    private fun LatLngBounds.toGoogleLatLngBounds() = GoogleLatLangBounds(
            this.southwestCorner.toGoogleLatLng(),
            northeastCorner.toGoogleLatLng()
        )

    private fun LineOptions.toGoogleLineOptions(context: Context) = PolylineOptions()
        .color(ContextCompat.getColor(context, lineColor))
        .width(resources.getDimension(lineWidth))
        .addAll(latLngs.map { it.toGoogleLatLng() })
}
