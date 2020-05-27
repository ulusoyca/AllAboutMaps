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

package com.ulusoy.allaboutmaps.main.gpx.google

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.*
import com.ulusoy.allaboutmaps.R
import com.ulusoy.allaboutmaps.databinding.FragmentGpxGoogleBinding
import com.ulusoy.allaboutmaps.domain.entities.LatLng as DomainLatLng
import com.ulusoy.allaboutmaps.domain.entities.Point
import com.ulusoy.allaboutmaps.main.gpx.BaseGpxMapProviderFragment

class GpxGoogleFragment : BaseGpxMapProviderFragment() {

    private lateinit var binding: FragmentGpxGoogleBinding

    private var map: GoogleMap? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentGpxGoogleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mapView = binding.mapView
        super.onViewCreated(view, savedInstanceState)
        binding.mapView.getMapAsync { mapboxMap -> onMapReady(mapboxMap) }
    }

    private fun onMapReady(map: GoogleMap) {
        this.map = map
        val mapStyleOptions = MapStyleOptions.loadRawResourceStyle(
            requireContext(),
            R.raw.google_maps_dark_style
        )
        map.setMapStyle(mapStyleOptions)
        onMapStyleLoaded()
    }

    override fun drawRoutePoints(routePoints: List<Point>) {
        val latLngs = routePoints.map { it.latLng.toGoogleLatLng() }
        map?.addPolyline(
            PolylineOptions()
                .color(mapLineColor)
                .jointType(JointType.ROUND)
                .width(resources.getDimension(R.dimen.google_route_line_width_cut))
                .addAll(latLngs)
        )
    }

    override fun addWaypoint(latLng: DomainLatLng) {
        map?.addMarker(
            MarkerOptions()
                .icon(BitmapDescriptorFactory.fromBitmap(foodStationIcon))
                .position(latLng.toGoogleLatLng())
        )
    }

    private fun DomainLatLng.toGoogleLatLng() = LatLng(
        latitude.value.toDouble(),
        longitude.value.toDouble()
    )
}
