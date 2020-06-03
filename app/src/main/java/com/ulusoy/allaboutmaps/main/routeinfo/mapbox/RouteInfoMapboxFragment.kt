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

package com.ulusoy.allaboutmaps.main.routeinfo.mapbox

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.Style
import com.ulusoy.allaboutmaps.R
import com.ulusoy.allaboutmaps.databinding.FragmentRouteInfoMapboxBinding
import com.ulusoy.allaboutmaps.main.routeinfo.BaseRouteInfoMapFragment

class RouteInfoMapboxFragment : BaseRouteInfoMapFragment() {

    private lateinit var binding: FragmentRouteInfoMapboxBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentRouteInfoMapboxBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mapView = binding.mapView
        super.onViewCreated(view, savedInstanceState)
        binding.mapView.getMapAsync { mapboxMap ->
            onMapReady(mapboxMap)
        }
    }

    private fun onMapReady(mapboxMap: MapboxMap) {
        binding.mapView.onMapReady(mapboxMap)
        mapboxMap.setStyle(
            Style.Builder().fromUri(requireContext().getString(R.string.mapbox_dark_map_style))
        ) {
            binding.mapView.onStyleLoaded(it)
            onMapStyleLoaded()
        }
    }
}
