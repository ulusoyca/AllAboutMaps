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

package com.ulusoy.allaboutmaps.main.gpx.mapbox

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.Style
import com.mapbox.mapboxsdk.plugins.annotation.LineManager
import com.mapbox.mapboxsdk.plugins.annotation.LineOptions
import com.mapbox.mapboxsdk.plugins.annotation.SymbolManager
import com.mapbox.mapboxsdk.plugins.annotation.SymbolOptions
import com.mapbox.mapboxsdk.style.layers.Property
import com.mapbox.mapboxsdk.utils.ColorUtils
import com.ulusoy.allaboutmaps.R
import com.ulusoy.allaboutmaps.databinding.FragmentGpxMapboxBinding
import com.ulusoy.allaboutmaps.domain.entities.LatLng as DomainLatLng
import com.ulusoy.allaboutmaps.domain.entities.Point
import com.ulusoy.allaboutmaps.main.gpx.BaseGpxMapProviderFragment

private const val CHECK_POINT_IMAGE_ID = "CHECK_POINT_IMAGE_ID"

class GpxMapboxFragment : BaseGpxMapProviderFragment() {

    private lateinit var binding: FragmentGpxMapboxBinding

    private var lineManager: LineManager? = null

    private var symbolManager: SymbolManager? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentGpxMapboxBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mapView = binding.mapView
        super.onViewCreated(view, savedInstanceState)
        binding.mapView.getMapAsync { mapboxMap -> onMapReady(mapboxMap) }
    }

    private fun onMapReady(mapboxMap: MapboxMap) {
        mapboxMap.apply {
            setStyle(Style.Builder().fromUri(getString(R.string.mapbox_dark_map_style))) {
                it.addImage(CHECK_POINT_IMAGE_ID, foodStationIcon)
                lineManager = LineManager(binding.mapView, mapboxMap, it)
                symbolManager = SymbolManager(binding.mapView, mapboxMap, it)
                onMapStyleLoaded()
            }
        }
    }

    override fun drawRoutePoints(routePoints: List<Point>) {
        val latLngs = routePoints.map { it.latLng.toMapboxLatLng() }
        val lineOptions = LineOptions().withLineJoin(Property.LINE_JOIN_ROUND)
            .withLineColor(ColorUtils.colorToRgbaString(mapLineColor))
            .withLineWidth(requireContext().resources.getDimension(R.dimen.mapbox_route_line_width_cut))
            .withLatLngs(latLngs)
        lineManager?.create(lineOptions)
    }

    override fun addWaypoint(latLng: DomainLatLng) {
        val symbolOptions = SymbolOptions()
            .withIconImage(CHECK_POINT_IMAGE_ID)
            .withLatLng(latLng.toMapboxLatLng())
            .withIconColor("#FFFFFF")
        symbolManager?.create(symbolOptions)
    }

    private fun DomainLatLng.toMapboxLatLng() = LatLng(
        latitude.value.toDouble(),
        longitude.value.toDouble()
    )
}
