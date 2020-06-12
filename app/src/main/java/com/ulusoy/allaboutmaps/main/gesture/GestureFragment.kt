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

package com.ulusoy.allaboutmaps.main.gesture

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.ulusoy.allaboutmaps.R
import com.ulusoy.allaboutmaps.databinding.FragmentGestureBinding
import com.ulusoy.allaboutmaps.main.MapProvider
import com.ulusoy.allaboutmaps.main.gesture.mapbox.GestureMapboxFragmentDirections
import dagger.android.support.DaggerFragment

class GestureFragment : DaggerFragment() {

    private lateinit var binding: FragmentGestureBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentGestureBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = requireActivity().findNavController(R.id.gesture_nav_host_fragment)
        binding.bottomNav.setupWithNavController(navController)
        (arguments?.getSerializable("mapProvider") as? MapProvider)?.let {
            when (it) {
                MapProvider.HUAWEI -> navController.navigate(
                    GestureMapboxFragmentDirections.actionGestureMapboxFragmentToGestureHuaweiFragment()
                )
                MapProvider.GOOGLE -> navController.navigate(
                   GestureMapboxFragmentDirections.actionGestureMapboxFragmentToGestureGoogleFragment()
                )
                else -> {
                    // Here the start destination is Mapbox Fragment. The directions should be from it.
                    // That's why we don't do anything when it is Mapbox
                }
            }
        }
    }
}
