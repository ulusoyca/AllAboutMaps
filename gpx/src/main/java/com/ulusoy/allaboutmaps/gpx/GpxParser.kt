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

package com.ulusoy.allaboutmaps.gpx

import android.content.Context
import android.net.Uri
import androidx.annotation.RawRes
import com.ulusoy.allaboutmaps.domain.GpxParseRepository
import com.ulusoy.allaboutmaps.domain.entities.Latitude
import com.ulusoy.allaboutmaps.domain.entities.Longitude
import com.ulusoy.allaboutmaps.domain.entities.RoutePoint
import io.ticofab.androidgpxparser.parser.GPXParser
import io.ticofab.androidgpxparser.parser.domain.Gpx
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import net.danlew.android.joda.JodaTimeAndroid
import org.xmlpull.v1.XmlPullParserException
import timber.log.Timber
import javax.inject.Inject

class GpxParser
@Inject constructor(
    private val context: Context
) : GpxParseRepository {

    override suspend fun parseGpxFile(gpxFileUri: Int) = withContext(Dispatchers.Default) {
        val routePoints = mutableListOf<RoutePoint>()
        try {
            loadGpx(gpxFileUri)?.run {
                routePoints += getRoutePointsFromTrackStructure()
            }
            routePoints
        } catch (e: XmlPullParserException) {
            val reason = "Could not parse the GPX file"
            Timber.w(e, "$reason ${e.message}")
            emptyList<RoutePoint>()
        } catch (e: Exception) {
            val reason = "Could not read the GPX file"
            Timber.w(e, "$reason ${e.message}")
            emptyList<RoutePoint>()
        }
    }

    private fun loadGpx(@RawRes gpxFileResId: Int): Gpx {
        JodaTimeAndroid.init(context)
        val uri = Uri.parse("android.resource://${context.packageName}/$gpxFileResId")
        return context.contentResolver.openInputStream(uri).use { GPXParser().parse(it) }
    }

    private fun Gpx.getRoutePointsFromTrackStructure(): List<RoutePoint> {
        val routePoints = mutableListOf<RoutePoint>()
        tracks[0].trackSegments.forEachIndexed { index, trackSegment ->
            val trackPoints = trackSegment.trackPoints
            if (trackPoints.isEmpty()) {
                if (index == 0) {
                    throw IllegalArgumentException("Track cannot be empty")
                }
            } else {
                val filteredTrackPoints = trackPoints
                    .filter { it.longitude != null && it.latitude != null }
                    .map { point ->
                        RoutePoint(
                            latitude = Latitude(point.latitude.toFloat()),
                            longitude = Longitude(point.longitude.toFloat()),
                            altitude = point.elevation.toFloat()
                        )
                    }
                if (filteredTrackPoints.isEmpty() && index == 0) {
                    throw IllegalArgumentException("Track cannot be empty")
                }
                routePoints += filteredTrackPoints
            }
        }
        return routePoints
    }
}