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
import androidx.annotation.ColorInt
import com.mapbox.mapboxsdk.maps.MapView
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.Style
import com.mapbox.mapboxsdk.plugins.annotation.LineManager
import com.mapbox.mapboxsdk.plugins.annotation.LineOptions
import com.mapbox.mapboxsdk.plugins.annotation.SymbolManager
import com.mapbox.mapboxsdk.plugins.annotation.SymbolOptions
import com.mapbox.mapboxsdk.style.layers.Property
import com.mapbox.mapboxsdk.utils.ColorUtils
import com.ulusoy.allaboutmaps.R
import com.ulusoy.allaboutmaps.domain.entities.LatLng
import com.ulusoy.allaboutmaps.main.extensions.toMapboxLatLng

private const val CHECK_POINT_IMAGE_ID = "CHECK_POINT_IMAGE_ID"

class MapboxMapView
@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : MapView(context, attrs, defStyleAttr), AllAboutMapView {

    private var lineManager: LineManager? = null
    private var symbolManager: SymbolManager? = null
    private var map: MapboxMap? = null

    override fun onMapViewCreate(savedInstanceState: Bundle?) { super.onCreate(savedInstanceState) }
    override fun onMapViewStart() { super.onStart() }
    override fun onMapViewStop() { super.onStop() }
    override fun onMapViewResume() { super.onResume() }
    override fun onMapViewPause() { super.onPause() }
    override fun onMapViewDestroy() { super.onDestroy() }
    override fun onMapViewSaveInstanceState(savedInstanceState: Bundle?) { super.onSaveInstanceState() }
    override fun onMapViewLowMemory() { super.onLowMemory() }

    fun onMapReady(mapboxMap: MapboxMap, markerIcon: Bitmap) {
        map = mapboxMap.apply {
            setStyle(Style.Builder().fromUri(context.getString(R.string.mapbox_dark_map_style))) {
                it.addImage(CHECK_POINT_IMAGE_ID, markerIcon)
                lineManager = LineManager(this@MapboxMapView, mapboxMap, it)
                symbolManager = SymbolManager(this@MapboxMapView, mapboxMap, it)
            }
        }
    }

    override fun drawPolyline(latLngs: List<LatLng>, @ColorInt mapLineColor: Int) {
        val lineOptions = LineOptions().withLineJoin(Property.LINE_JOIN_ROUND)
            .withLineColor(ColorUtils.colorToRgbaString(mapLineColor))
            .withLineWidth(resources.getDimension(R.dimen.mapbox_route_line_width_cut))
            .withLatLngs(latLngs.map { it.toMapboxLatLng() })
        lineManager?.create(lineOptions)
    }

    override fun drawMarker(latLng: LatLng, icon: Bitmap, name: String?) {
        var symbolOptions = SymbolOptions()
            .withIconImage(CHECK_POINT_IMAGE_ID)
            .withLatLng(latLng.toMapboxLatLng())
            .withIconColor("#FFFFFF")
            .withTextColor("#FFFFFF")
            .withTextOffset(arrayOf(1f, 1f))
        symbolOptions = name?.run { symbolOptions.withTextField(name) }
        symbolManager?.create(symbolOptions)
    }
}
