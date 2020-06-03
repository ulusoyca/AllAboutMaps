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

package com.ulusoy.allaboutmaps.main.routeinfo

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ulusoy.allaboutmaps.R
import com.ulusoy.allaboutmaps.domain.entities.MarkerOptions
import com.ulusoy.allaboutmaps.main.common.MapLifecycleHandlerFragment
import javax.inject.Inject

private const val CHECK_POINT_STYLE_IMAGE_ID = "CHECK_POINT_STYLE_IMAGE_ID"

abstract class BaseRouteInfoMapFragment : MapLifecycleHandlerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val mapLineColor: Int by lazy {
        ContextCompat.getColor(requireContext(), R.color.map_route_cut_line_color)
    }

    private val viewModel: RouteInfoViewModel by viewModels { viewModelFactory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(viewModel) {
            routePoints.observe(viewLifecycleOwner, Observer { routePoints ->
                mapView.drawPolyline(routePoints.map { it.latLng }, mapLineColor)
            })
            waypoints.observe(viewLifecycleOwner, Observer { waypoints ->
                waypoints.forEach {
                    mapView.drawMarker(
                        MarkerOptions(
                            latLng = it.latLng,
                            iconResId = R.drawable.food,
                            text = it.name,
                            iconMapStyleId = CHECK_POINT_STYLE_IMAGE_ID,
                            textColor = R.color.white
                        )
                    )
                }
            })
        }
    }

    protected fun onMapStyleLoaded() {
        viewModel.getRouteInfo()
    }
}
