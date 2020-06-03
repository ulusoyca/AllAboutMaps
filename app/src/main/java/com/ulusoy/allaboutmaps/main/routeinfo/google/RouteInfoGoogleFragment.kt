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

package com.ulusoy.allaboutmaps.main.routeinfo.google

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.model.*
import com.ulusoy.allaboutmaps.R
import com.ulusoy.allaboutmaps.databinding.FragmentRouteInfoGoogleBinding
import com.ulusoy.allaboutmaps.main.routeinfo.BaseRouteInfoMapFragment

class RouteInfoGoogleFragment : BaseRouteInfoMapFragment() {

    private lateinit var binding: FragmentRouteInfoGoogleBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentRouteInfoGoogleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mapView = binding.mapView
        super.onViewCreated(view, savedInstanceState)
        binding.mapView.getMapAsync { mapboxMap ->
            binding.mapView.onMapReady(mapboxMap)
            val mapStyleOptions =
                MapStyleOptions.loadRawResourceStyle(context, R.raw.google_maps_dark_style)
            mapboxMap.setMapStyle(mapStyleOptions)
            onMapStyleLoaded()
        }
    }
}
