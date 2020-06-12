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

package com.ulusoy.allaboutmaps.main.gesture.google

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.GoogleMap
import com.ulusoy.allaboutmaps.databinding.FragmentGestureGoogleBinding
import com.ulusoy.allaboutmaps.main.common.MapLifecycleHandlerFragment
import timber.log.Timber

class GestureGoogleFragment : MapLifecycleHandlerFragment() {

    private lateinit var binding: FragmentGestureGoogleBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentGestureGoogleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mapView = binding.mapView
        super.onViewCreated(view, savedInstanceState)
        binding.mapView.getMapAsync { googleMap ->
            binding.mapView.getMapAsync { onMapReady(googleMap) }
        }
    }

    private fun onMapReady(googleMap: GoogleMap) {
        binding.mapView.onMapReady(googleMap)
        googleMap.apply {
            setOnCameraIdleListener {
                Timber.d("[MAP-GOOGLE] onCameraIdle()")
            }
            setOnCameraMoveStartedListener {
                Timber.d("[MAP-GOOGLE] onCameraMoveStarted()")
            }
            setOnCameraMoveCanceledListener {
                Timber.d("[MAP-GOOGLE] onCameraMoveCancelled()")
            }
        }
    }
}
