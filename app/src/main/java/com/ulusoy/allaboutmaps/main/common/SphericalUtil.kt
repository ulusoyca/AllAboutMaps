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

import com.ulusoy.allaboutmaps.domain.entities.LatLng
import com.ulusoy.allaboutmaps.domain.entities.Latitude
import com.ulusoy.allaboutmaps.domain.entities.Longitude
import kotlin.math.*

object SphericalUtil {
    /**
     * The earth's radius, in meters.
     * Mean radius as defined by IUGG.
     */
    private const val EARTH_RADIUS = 6371009.0

    /**
     * Returns the LatLng resulting from moving a distance from an origin
     * in the specified heading (expressed in degrees clockwise from north).
     *
     * @param from The LatLng from which to start.
     * @param distanceToTravel The distance to travel.
     * @param headingInDegrees The heading in degrees clockwise from north.
     */
    fun computeOffset(from: LatLng, distanceToTravel: Double, headingInDegrees: Double): LatLng {
        var distance = distanceToTravel
        var heading = headingInDegrees
        distance /= EARTH_RADIUS
        heading = Math.toRadians(heading)
        // http://williams.best.vwh.net/avform.htm#LL
        val fromLat = Math.toRadians(from.latDoubleValue)
        val fromLng = Math.toRadians(from.lngDoubleValue)
        val cosDistance = cos(distance)
        val sinDistance = sin(distance)
        val sinFromLat = sin(fromLat)
        val cosFromLat = cos(fromLat)
        val sinLat = cosDistance * sinFromLat + sinDistance * cosFromLat * cos(heading)
        val dLng = atan2(
            sinDistance * cosFromLat * sin(heading),
            cosDistance - sinFromLat * sinLat
        )
        return LatLng(
            Latitude(Math.toDegrees(asin(sinLat)).toFloat()),
            Longitude(Math.toDegrees(fromLng + dLng).toFloat())
        )
    }
}
