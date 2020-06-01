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

package com.ulusoy.allaboutmaps.main.common

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ulusoy.allaboutmaps.R
import com.ulusoy.allaboutmaps.main.ui.AllAboutMapView
import dagger.android.support.DaggerFragment
import javax.inject.Inject

/**
 * The base fragment handles map lifecycle. To use it, the mapview classes should implement
 * [AllAboutMapView] interface.
 */
abstract class MapLifecycleHandlerFragment : DaggerFragment() {

    protected lateinit var mapView: AllAboutMapView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mapView.onMapViewCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        mapView.onMapViewResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onMapViewPause()
    }

    override fun onStart() {
        super.onStart()
        mapView.onMapViewStart()
    }

    override fun onStop() {
        super.onStop()
        mapView.onMapViewStop()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mapView.onMapViewDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onMapViewSaveInstanceState(outState)
    }
}
