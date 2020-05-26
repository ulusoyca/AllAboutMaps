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

package com.ulusoy.allaboutmaps.main.gpx

import androidx.annotation.RawRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ulusoy.allaboutmaps.domain.interactors.ParseGpxFileUseCase
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GpxViewModel
@Inject constructor(
    private val parseGpxFileUseCase: ParseGpxFileUseCase
) : ViewModel() {

    private val _size = MutableLiveData<Int>()
    val size: LiveData<Int>
        get() = _size

    fun parseGpxFile(@RawRes gpxFileResId: Int) {
        viewModelScope.launch(Dispatchers.Default) {
            val routePoints = parseGpxFileUseCase(gpxFileResId)
            _size.postValue(routePoints.size)
        }
    }
}