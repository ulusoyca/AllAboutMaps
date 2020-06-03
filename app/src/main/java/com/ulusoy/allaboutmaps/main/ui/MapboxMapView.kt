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
import androidx.annotation.ColorInt
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory
import com.mapbox.mapboxsdk.maps.MapView
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.Style
import com.mapbox.mapboxsdk.plugins.annotation.LineManager
import com.mapbox.mapboxsdk.plugins.annotation.LineOptions
import com.mapbox.mapboxsdk.plugins.annotation.SymbolManager
import com.mapbox.mapboxsdk.style.layers.Property
import com.mapbox.mapboxsdk.utils.ColorUtils
import com.ulusoy.allaboutmaps.R
import com.ulusoy.allaboutmaps.domain.entities.LatLng
import com.ulusoy.allaboutmaps.domain.entities.LatLngBounds
import com.ulusoy.allaboutmaps.domain.entities.MarkerOptions
import com.ulusoy.allaboutmaps.main.extensions.toMapboxLatLng
import com.ulusoy.allaboutmaps.main.extensions.toMapboxSymbolOptions
import com.ulusoy.allaboutmaps.main.extensions.toMapbpoxLatLngBounds

class MapboxMapView
@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : MapView(context, attrs, defStyleAttr), AllAboutMapView, Style.OnStyleLoaded {

    private var lineManager: LineManager? = null
    private var symbolManager: SymbolManager? = null
    private var map: MapboxMap? = null
    private var style: Style? = null

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

    fun onMapReady(mapboxMap: MapboxMap) {
        map = mapboxMap
    }

    override fun onStyleLoaded(style: Style) {
        this.style = style
        map?.let {
            lineManager = LineManager(this@MapboxMapView, it, style)
            symbolManager = SymbolManager(this@MapboxMapView, it, style)
        }
    }

    override fun drawPolyline(latLngs: List<LatLng>, @ColorInt mapLineColor: Int) {
        val lineOptions = LineOptions().withLineJoin(Property.LINE_JOIN_ROUND)
            .withLineColor(ColorUtils.colorToRgbaString(mapLineColor))
            .withLineWidth(resources.getDimension(R.dimen.mapbox_route_line_width_cut))
            .withLatLngs(latLngs.map { it.toMapboxLatLng() })
        lineManager?.create(lineOptions)
    }

    override fun moveCamera(latLng: LatLng) {
        throw NotImplementedError()
    }

    override fun moveCamera(latLngBounds: LatLngBounds, padding: Int) {
        map?.moveCamera(
            CameraUpdateFactory.newLatLngBounds(latLngBounds.toMapbpoxLatLngBounds(), padding)
        )
    }

    override fun setMapPadding(left: Int, right: Int, top: Int, bottom: Int) {
        val currentCameraPosition = map?.cameraPosition
        if (currentCameraPosition != null) {
            // TODO Investigate why this implementation has no effect.
            map?.cameraPosition = CameraPosition.Builder(currentCameraPosition)
                .padding(
                    doubleArrayOf(
                        left.toDouble(),
                        right.toDouble(),
                        top.toDouble(),
                        bottom.toDouble()
                    )
                ).build()
        }
    }

    override fun drawMarker(markerOptions: MarkerOptions) {
        style?.let {
            symbolManager?.create(
                markerOptions.toMapboxSymbolOptions(context, it)
                    .withTextOffset(arrayOf(1f, 1f))
            )
        }
    }
}
