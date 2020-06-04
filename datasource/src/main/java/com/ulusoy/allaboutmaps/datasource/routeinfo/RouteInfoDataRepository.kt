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

package com.ulusoy.allaboutmaps.datasource.routeinfo

import com.ulusoy.allaboutmaps.datasource.routeinfo.datasource.RouteInfoDatasource
import com.ulusoy.allaboutmaps.domain.RouteInfoRepository
import com.ulusoy.allaboutmaps.domain.entities.Point
import com.ulusoy.allaboutmaps.domain.entities.RouteInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Named

@OptIn(ExperimentalCoroutinesApi::class)
class RouteInfoDataRepository
@Inject constructor(
    @Named("GPX_DATA_SOURCE")
    private val gpxFileDatasource: RouteInfoDatasource
) : RouteInfoRepository {
    override suspend fun getRouteInfo(): RouteInfo {
        return gpxFileDatasource.parseGpxFile()
    }

    override suspend fun startWaypointPlayback(
        points: List<Point>,
        updateInterval: Long
    ): Flow<Point> = flow {
        val routeInfo = gpxFileDatasource.parseGpxFile()
        routeInfo.wayPoints.forEachIndexed { index, waypoint ->
            if (index != 0) {
                delay(updateInterval)
            }
            emit(waypoint)
        }
    }.flowOn(Dispatchers.Default)
}