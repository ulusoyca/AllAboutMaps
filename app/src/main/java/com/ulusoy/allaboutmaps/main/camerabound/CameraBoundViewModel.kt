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

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ulusoy.allaboutmaps.domain.entities.Point
import com.ulusoy.allaboutmaps.domain.interactors.GetRouteInfoUseCase
import com.ulusoy.allaboutmaps.domain.interactors.StartWaypointPlaybackUseCase
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(InternalCoroutinesApi::class)
class CameraBoundViewModel
@Inject constructor(
    private val getRouteInfoUseCase: GetRouteInfoUseCase,
    private val startWaypointPlaybackUseCase: StartWaypointPlaybackUseCase
) : ViewModel() {

    private val _waypointLatLngs = MutableLiveData<Point>()
    val waypointLatLngs: LiveData<Point>
        get() = _waypointLatLngs

    private val _playbackStatus = MutableLiveData<PlaybackStatus>()
    val playbackStatus: LiveData<PlaybackStatus>
        get() = _playbackStatus

    fun startPlayback(updateInterval: Long) {
        viewModelScope.launch {
            val routeInfo = getRouteInfoUseCase()
            startWaypointPlaybackUseCase(routeInfo.wayPoints, updateInterval)
                .onStart { _playbackStatus.postValue(PlaybackStatus.STARTED) }
                .onEach {
                    _waypointLatLngs.postValue(it)
                }
                .onCompletion { _playbackStatus.postValue(PlaybackStatus.COMPLETED) }
                .collect()
        }
    }
}
