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

package com.ulusoy.allaboutmaps.domain.interactors

import androidx.annotation.RawRes
import com.ulusoy.allaboutmaps.domain.GpxParseRepository
import com.ulusoy.allaboutmaps.domain.entities.RoutePoint
import javax.inject.Inject

class ParseGpxFileUseCase
@Inject constructor(
    private val gpxParseRepository: GpxParseRepository
) {
    suspend operator fun invoke(@RawRes gpxFileUri: Int): List<RoutePoint> {
        return gpxParseRepository.parseGpxFile(gpxFileUri)
    }
}