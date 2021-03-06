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

package com.ulusoy.allaboutmaps.main.home

import androidx.annotation.StringRes

interface TopicSelectedListener {
    fun onTopicSelected(@StringRes topicTitle: Int)
    fun onTopicSelectedWithHuaweiMap(@StringRes topicTitle: Int)
    fun onTopicSelectedWithGoogleMap(@StringRes topicTitle: Int)
    fun onTopicSelectedWithMapboxMap(@StringRes topicTitle: Int)
}
