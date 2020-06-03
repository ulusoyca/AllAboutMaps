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

package com.ulusoy.allaboutmaps.main.extensions

import android.content.Context
import androidx.core.content.ContextCompat
import com.huawei.hms.maps.MapsInitializer
import com.google.android.gms.maps.model.BitmapDescriptorFactory as GoogleBitmapDescriptorFactory
import com.google.android.gms.maps.model.MarkerOptions as GoogleMarkerOptions
import com.huawei.hms.maps.model.BitmapDescriptorFactory as HuaweiBitmapDescriptorFactory
import com.huawei.hms.maps.model.MarkerOptions as HuaweiMarkerOptions
import com.mapbox.mapboxsdk.maps.Style
import com.mapbox.mapboxsdk.plugins.annotation.SymbolOptions
import com.mapbox.mapboxsdk.utils.BitmapUtils
import com.mapbox.mapboxsdk.utils.ColorUtils
import com.ulusoy.allaboutmaps.domain.entities.MarkerOptions

fun MarkerOptions.toMapboxSymbolOptions(context: Context, style: Style): SymbolOptions {
    val drawable = ContextCompat.getDrawable(context, iconResId)
    val bitmap = BitmapUtils.getBitmapFromDrawable(drawable)!!
    style.addImage(iconMapStyleId, bitmap)
    val iconColor = ColorUtils.colorToRgbaString(
        ContextCompat.getColor(context, iconColor)
    )
    val textColor = ColorUtils.colorToRgbaString(
        ContextCompat.getColor(context, textColor)
    )
    var symbolOptions = SymbolOptions()
        .withIconImage(iconMapStyleId)
        .withLatLng(latLng.toMapboxLatLng())
        .withIconColor(iconColor)
        .withTextColor(textColor)
        .withIconOpacity(iconAlpha)
        .withTextOpacity(textAlpha)
    symbolOptions = text?.let { symbolOptions.withTextField(it) } ?: symbolOptions
    return symbolOptions
}

fun MarkerOptions.toGoogleMarkerOptions(): GoogleMarkerOptions {
    var markerOptions = GoogleMarkerOptions()
        .icon(GoogleBitmapDescriptorFactory.fromResource(iconResId))
        .position(latLng.toGoogleLatLng())
        .alpha(iconAlpha)
    markerOptions = text?.let { markerOptions.title(it) } ?: markerOptions
    return markerOptions
}

fun MarkerOptions.toHuaweiMarkerOptions(applicationContext: Context): HuaweiMarkerOptions {
    MapsInitializer.initialize(applicationContext)
    var markerOptions = HuaweiMarkerOptions()
        .icon(HuaweiBitmapDescriptorFactory.fromResource(iconResId))
        .position(latLng.toHuaweiLatLng())
        .alpha(iconAlpha)
    markerOptions = text?.let { markerOptions.title(it) } ?: markerOptions
    return markerOptions
}
