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

package com.ulusoy.allaboutmaps.main.camerabound

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ulusoy.allaboutmaps.R
import com.ulusoy.allaboutmaps.domain.entities.MarkerOptions
import com.ulusoy.allaboutmaps.main.common.MapLifecycleHandlerFragment
import com.ulusoy.allaboutmaps.main.extensions.toBounds
import javax.inject.Inject
import kotlin.math.roundToInt

private const val PLAYBACK_GPS_INTERVAL = 2000L
private const val PLACE_STYLE_IMAGE_ID = "PLACE_STYLE_IMAGE_ID"

abstract class BaseCameraBoundMapFragment : MapLifecycleHandlerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val mapLineColor: Int by lazy {
        ContextCompat.getColor(requireContext(), R.color.map_route_cut_line_color)
    }

    private val viewModel: CameraBoundViewModel by viewModels { viewModelFactory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(viewModel) {
            playbackStatus.observe(viewLifecycleOwner, Observer { status ->
                val msg = when (status) {
                    PlaybackStatus.STARTED -> {
                        mapView.drawPolyline(emptyList(), mapLineColor)
                        R.string.playback_started
                    }
                    PlaybackStatus.COMPLETED -> R.string.playback_finished
                }
                Toast.makeText(requireContext(), msg, Toast.LENGTH_LONG).show()
            })
            waypointLatLngs.observe(viewLifecycleOwner, Observer {
                mapView.drawMarker(
                    MarkerOptions(
                        latLng = it.latLng,
                        iconResId = R.drawable.food,
                        text = it.name,
                        iconMapStyleId = PLACE_STYLE_IMAGE_ID,
                        textColor = R.color.black
                    )
                )
                mapView.moveCamera(it.latLng.toBounds(2000.0))
            })
        }
    }

    protected fun onMapStyleLoaded() {
        val padding = resources.getDimension(R.dimen.size_spacing_medium).roundToInt()
        mapView.setMapPadding(padding, padding, padding, padding)
        viewModel.startPlayback(PLAYBACK_GPS_INTERVAL)
    }
}
