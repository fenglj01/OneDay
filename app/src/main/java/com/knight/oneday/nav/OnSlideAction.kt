/*
 * Copyright 2019 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.knight.oneday.nav

import android.view.View
import android.widget.ImageView
import androidx.annotation.FloatRange
import androidx.core.view.marginTop
import androidx.core.view.updatePadding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.shape.MaterialShapeDrawable
import com.knight.oneday.R
import com.knight.oneday.utilities.normalize

/**
 * An action to be performed when a bottom sheet's slide offset is changed.
 */
interface OnSlideAction {
    /**
     * Called when the bottom sheet's [slideOffset] is changed. [slideOffset] will always be a
     * value between -1.0 and 1.0. -1.0 is equal to [BottomSheetBehavior.STATE_HIDDEN], 0.0
     * is equal to [BottomSheetBehavior.STATE_HALF_EXPANDED] and 1.0 is equal to
     * [BottomSheetBehavior.STATE_EXPANDED].
     */
    fun onSlide(
        sheet: View,
        @FloatRange(
            from = -1.0,
            fromInclusive = true,
            to = 1.0,
            toInclusive = true
        ) slideOffset: Float
    )
}

/**
 * A slide action which rotates a view counterclockwise by 180 degrees between the hidden state
 * and the half expanded state.
 */
class HalfClockwiseRotateSlideAction(
    private val view: View
) : OnSlideAction {

    override fun onSlide(sheet: View, slideOffset: Float) {
        view.rotation = slideOffset.normalize(
            -1F,
            0F,
            0F,
            180F
        )
    }
}


/**
 * 透明度根据drawer的滑动动态变化
 */
class AlphaSlideAction(
    private val view: View,
    private val reverse: Boolean = false
) : OnSlideAction {

    override fun onSlide(sheet: View, slideOffset: Float) {
        val alpha = slideOffset.normalize(-1F, 0F, 0F, 1F)
        view.alpha = if (!reverse) alpha else 1F - alpha
    }
}
