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
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory
import com.mapbox.mapboxsdk.geometry.LatLng as MapboxLatLng
import com.mapbox.mapboxsdk.geometry.LatLngBounds as MapboxLatLngBounds
import com.mapbox.mapboxsdk.maps.MapView
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.Style
import com.mapbox.mapboxsdk.plugins.annotation.LineManager
import com.mapbox.mapboxsdk.plugins.annotation.LineOptions as MapboxLineOptions
import com.mapbox.mapboxsdk.plugins.annotation.SymbolManager
import com.mapbox.mapboxsdk.plugins.annotation.SymbolOptions
import com.mapbox.mapboxsdk.utils.BitmapUtils
import com.mapbox.mapboxsdk.utils.ColorUtils
import com.ulusoy.allaboutmaps.domain.entities.LatLng
import com.ulusoy.allaboutmaps.domain.entities.LatLngBounds
import com.ulusoy.allaboutmaps.domain.entities.LineOptions
import com.ulusoy.allaboutmaps.domain.entities.MarkerOptions

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

    override fun drawPolyline(lineOptions: LineOptions) {
        lineManager?.create(lineOptions.toMapboxLineOptions(context))
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

    override fun moveCamera(latLng: LatLng) {
        throw NotImplementedError()
    }

    override fun moveCamera(latLngBounds: LatLngBounds, padding: Int) {
        map?.moveCamera(
            CameraUpdateFactory.newLatLngBounds(
                latLngBounds.toMapboxLatLngBounds(), padding
            )
        )
    }

    /**
     * There is a bug as of 9.2.0 version. Follow the issue:
     * https://github.com/mapbox/mapbox-gl-native-android/issues/282
     */
    override fun setMapPadding(left: Int, right: Int, top: Int, bottom: Int) {
        val currentCameraPosition = map?.cameraPosition
        if (currentCameraPosition != null) {
            map?.moveCamera(
                CameraUpdateFactory.paddingTo(
                    left.toDouble(),
                    right.toDouble(),
                    top.toDouble(),
                    bottom.toDouble()
                )
            )
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

    private fun LatLng.toMapboxLatLng() = MapboxLatLng(
        latitude.value.toDouble(),
        longitude.value.toDouble()
    )

    private fun LatLngBounds.toMapboxLatLngBounds() = MapboxLatLngBounds.from(
        northeastCorner.latDoubleValue,
        northeastCorner.lngDoubleValue,
        southwestCorner.latDoubleValue,
        southwestCorner.lngDoubleValue
    )

    private fun LineOptions.toMapboxLineOptions(context: Context): com.mapbox.mapboxsdk.plugins.annotation.LineOptions? {
        val color = ColorUtils.colorToRgbaString(
            ContextCompat.getColor(context, lineColor)
        )
        return MapboxLineOptions()
            .withLineColor(color)
            .withLineWidth(resources.getDimension(lineWidth))
            .withLatLngs(latLngs.map { it.toMapboxLatLng() })
    }

    private fun MarkerOptions.toMapboxSymbolOptions(context: Context, style: Style): SymbolOptions {
        val drawable = ContextCompat.getDrawable(context, iconResId)
        val bitmap = BitmapUtils.getBitmapFromDrawable(drawable)!!
        style.addImage(iconMapStyleId, bitmap)
        val iconColor = ColorUtils.colorToRgbaString(
            ContextCompat.getColor(context, iconColor)
        )
        val textColor = ColorUtils.colorToRgbaString(
            ContextCompat.getColor(context, textColor)
        )
        var symbolOptions = SymbolOptions()
            .withIconImage(iconMapStyleId)
            .withLatLng(latLng.toMapboxLatLng())
            .withIconColor(iconColor)
            .withTextColor(textColor)
        symbolOptions = text?.let { symbolOptions.withTextField(it) } ?: symbolOptions
        return symbolOptions
    }
}
