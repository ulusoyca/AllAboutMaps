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
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.JointType
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.ulusoy.allaboutmaps.R
import com.ulusoy.allaboutmaps.databinding.FragmentGpxGoogleBinding
import com.ulusoy.allaboutmaps.main.gpx.GpxViewModel
import com.ulusoy.allaboutmaps.main.gpx.toGoogleLatLng
import dagger.android.support.DaggerFragment
import javax.inject.Inject
import timber.log.Timber

class GpxGoogleFragment : DaggerFragment() {

    private lateinit var binding: FragmentGpxGoogleBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: GpxViewModel by viewModels { viewModelFactory }

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
        super.onViewCreated(view, savedInstanceState)
        binding.mapView.run {
            onCreate(savedInstanceState)
            getMapAsync { onMapReady(it) }
        }

        with(viewModel) {
            routePoints.observe(viewLifecycleOwner, Observer { routePoints ->
                val latLngs = routePoints.map { it.latLng.toGoogleLatLng() }
                map?.addPolyline(
                    PolylineOptions()
                        .color(ContextCompat.getColor(requireContext(), R.color.map_route_cut_line_color))
                        .jointType(JointType.ROUND)
                        .width(resources.getDimension(R.dimen.google_route_line_width_cut))
                        .addAll(latLngs)
                )
            })
        }
    }

    private fun onMapReady(map: GoogleMap) {
        Timber.d("mapbox map style is loaded")
        this.map = map
        val mapStyleOptions = MapStyleOptions.loadRawResourceStyle(
            requireContext(),
            R.raw.google_maps_dark_style
        )
        map.setMapStyle(mapStyleOptions)
        viewModel.parseGpxFile(R.raw.cut)
    }

    override fun onStart() {
        super.onStart()
        binding.mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        binding.mapView.onResume()
    }

    override fun onStop() {
        super.onStop()
        binding.mapView.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.mapView.onDestroy()
    }
}
