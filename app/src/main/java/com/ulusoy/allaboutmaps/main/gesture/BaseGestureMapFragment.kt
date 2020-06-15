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

import android.widget.Toast
import com.ulusoy.allaboutmaps.R
import com.ulusoy.allaboutmaps.main.MapProvider
import com.ulusoy.allaboutmaps.main.common.MapLifecycleHandlerFragment
import timber.log.Timber

abstract class BaseGestureMapFragment : MapLifecycleHandlerFragment() {

    private var numOfMoveStart = 0
    private var numOfIdleCamera = 0
    private var numOfCancel = 0

    protected fun onCameraIdle(mapProvider: MapProvider) {
        val providerName = requireContext().getString(mapProvider.providerNameResId)
        val event = requireContext().getString(R.string.camera_idle)
        numOfIdleCamera++
        Timber.d("[MAP-${providerName}] ${getEventNumbers()}")
        Toast.makeText(requireContext(), getToastNessage(providerName, event), Toast.LENGTH_SHORT).show()
    }

    protected fun onCameraMoveStared(mapProvider: MapProvider) {
        val providerName = requireContext().getString(mapProvider.providerNameResId)
        val event = requireContext().getString(R.string.camera_move_started)
        numOfMoveStart++
        Timber.d("[MAP-$providerName] ${getEventNumbers()}")
        Toast.makeText(requireContext(), getToastNessage(providerName, event), Toast.LENGTH_SHORT).show()
    }

    protected fun onCameraMoveCanceled(mapProvider: MapProvider) {
        val providerName = requireContext().getString(mapProvider.providerNameResId)
        val event = requireContext().getString(R.string.camera_move_canceled)
        numOfCancel++
        Timber.d("[MAP-$providerName] ${getEventNumbers()}")
        Toast.makeText(requireContext(), getToastNessage(providerName, event), Toast.LENGTH_SHORT).show()
    }

    private fun getToastNessage(providerName: String, event: String) =
        "$providerName - $event\n${getEventNumbers()}"

    override fun onResume() {
        super.onResume()
        numOfIdleCamera = 0
        numOfMoveStart = 0
        numOfCancel = 0
    }

    private fun getEventNumbers(): String {
        return "Idle: $numOfIdleCamera\nMove Start: $numOfMoveStart\nCancel: $numOfCancel"
    }
}
