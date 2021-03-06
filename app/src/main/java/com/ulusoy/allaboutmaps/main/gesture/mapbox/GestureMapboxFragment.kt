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

package com.ulusoy.allaboutmaps.main.gesture.mapbox

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.Style
import com.ulusoy.allaboutmaps.databinding.FragmentGestureMapboxBinding
import com.ulusoy.allaboutmaps.main.MapProvider
import com.ulusoy.allaboutmaps.main.common.MapLifecycleHandlerFragment
import com.ulusoy.allaboutmaps.main.gesture.BaseGestureMapFragment
import timber.log.Timber

class GestureMapboxFragment : BaseGestureMapFragment() {

    private lateinit var binding: FragmentGestureMapboxBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentGestureMapboxBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mapView = binding.mapView
        super.onViewCreated(view, savedInstanceState)
        binding.mapView.getMapAsync { onMapReady(it) }
    }

    private fun onMapReady(mapboxMap: MapboxMap) {
        binding.mapView.onMapReady(mapboxMap)
        mapboxMap.setStyle(Style.OUTDOORS) {
            binding.mapView.onStyleLoaded(it)
        }
        mapboxMap.apply {
            addOnCameraIdleListener { onCameraIdle(MapProvider.MAPBOX) }
            addOnCameraMoveStartedListener { onCameraMoveStared(MapProvider.MAPBOX) }
            addOnCameraMoveCancelListener { onCameraMoveCanceled(MapProvider.MAPBOX) }
        }
    }
}
