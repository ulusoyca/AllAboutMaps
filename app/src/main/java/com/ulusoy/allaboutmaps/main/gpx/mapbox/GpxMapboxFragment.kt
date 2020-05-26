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
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.Style
import com.mapbox.mapboxsdk.plugins.annotation.LineManager
import com.mapbox.mapboxsdk.plugins.annotation.LineOptions
import com.ulusoy.allaboutmaps.R
import com.ulusoy.allaboutmaps.databinding.FragmentGpxMapboxBinding
import com.ulusoy.allaboutmaps.main.gpx.GpxViewModel
import com.ulusoy.allaboutmaps.main.gpx.toMapboxLatLng
import dagger.android.support.DaggerFragment
import javax.inject.Inject
import timber.log.Timber

class GpxMapboxFragment : DaggerFragment() {

    private lateinit var binding: FragmentGpxMapboxBinding

    private var lineManager: LineManager? = null

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var lineOptions: LineOptions

    private val viewModel: GpxViewModel by viewModels { viewModelFactory }

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
        super.onViewCreated(view, savedInstanceState)
        binding.mapView.run {
            onCreate(savedInstanceState)
            getMapAsync { mapboxMap ->
                onMapReady(mapboxMap)
            }
        }

        with(viewModel) {
            // Parse each file
            parseGpxFile(R.raw.cut)
            // Observe the route points for each file
            routePoints.observe(viewLifecycleOwner, Observer { routePoints ->
                val latLngs = routePoints.map { it.latLng.toMapboxLatLng() }
                lineManager?.create(lineOptions.withLatLngs(latLngs))
            })
        }
    }

    private fun onMapReady(mapboxMap: MapboxMap) {
        Timber.d("on mapbox map ready")
        mapboxMap.apply {
            setStyle(Style.Builder().fromUri(getString(R.string.map_style))) {
                Timber.d("mapbox map style is loaded")
                lineManager = LineManager(binding.mapView, mapboxMap, it)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding.mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.mapView.onPause()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.mapView.onLowMemory()
    }

    override fun onStart() {
        super.onStart()
        binding.mapView.onStart()
    }

    override fun onStop() {
        super.onStop()
        binding.mapView.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.mapView.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding.mapView.onSaveInstanceState(outState)
    }
}
