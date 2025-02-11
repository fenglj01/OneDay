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

import android.util.Log
import com.google.android.material.shape.EdgeTreatment
import com.google.android.material.shape.ShapePath
import kotlin.math.atan
import kotlin.math.sqrt

private const val ARC_QUARTER = 90
private const val ARC_HALF = 180
private const val ANGLE_UP = 270
private const val ANGLE_LEFT = 180

/**
 * An edge treatment which draws a semicircle cutout at any point along the edge.
 *
 * @param cutoutMargin 要添加到[cutoutDiameter]的额外宽度，导致更大的总切口尺寸
 * @param cutoutRoundedCornerRadius 半圆所在的每个角的半径遇到直边。
 * @param cutoutVerticalOffset 切口的数量应相对于圆心升高。
 * @param cutoutDiameter 要切出的半圆的直径。
 * @param cutoutHorizontalOffset 从边缘的中间水平偏移，应在此处绘制切口。
 */
class SemiCircleEdgeCutoutTreatment(
    private var cutoutMargin: Float = 0F,
    private var cutoutRoundedCornerRadius: Float = 0F,
    private var cutoutVerticalOffset: Float = 0F,
    private var cutoutDiameter: Float = 0F,
    private var cutoutHorizontalOffset: Float = 0F
) : EdgeTreatment() {

    private var cradleDiameter = 0F
    private var cradleRadius = 0F
    private var roundedCornerOffset = 0F
    private var middle = 0F
    private var verticalOffset = 0F
    private var verticalOffsetRatio = 0F
    private var distanceBetweenCenters = 0F
    private var distanceBetweenCentersSquared = 0F
    private var distanceY = 0F
    private var distanceX = 0F
    private var leftRoundedCornerCircleX = 0F
    private var rightRoundedCornerCircleX = 0F
    private var cornerRadiusArcLength = 0F
    private var cutoutArcOffset = 0F

    init {
        require(cutoutVerticalOffset >= 0) {
            "cutoutVerticalOffset must be positive but was $cutoutVerticalOffset"
        }
        Log.d(
            "TAG",
            "${this.cutoutMargin} ${this.cutoutRoundedCornerRadius} ${this.cutoutVerticalOffset} ${this.cutoutDiameter} ${this.cutoutHorizontalOffset}"
        )
    }

    override fun getEdgePath(
        length: Float,
        center: Float,
        interpolation: Float,
        shapePath: ShapePath
    ) {
        if (cutoutDiameter == 0f) {
            // There is no cutout to draw.
            shapePath.lineTo(length, 0f)
            return
        }

        cradleDiameter = cutoutMargin * 2 + cutoutDiameter
        cradleRadius = cradleDiameter / 2f
        roundedCornerOffset = interpolation * cutoutRoundedCornerRadius
        middle = length / 2f + cutoutHorizontalOffset

        verticalOffset = interpolation * cutoutVerticalOffset +
                (1 - interpolation) * cradleRadius
        verticalOffsetRatio = verticalOffset / cradleRadius

        if (verticalOffsetRatio >= 1.0f) {
            // Vertical offset is so high that there's no curve to draw in the edge. The circle is
            // actually above the edge, so just draw a straight line.
            shapePath.lineTo(length, 0f)
            return
        }

        // Calculate the path of the cutout by calculating the location of two adjacent circles. One
        // circle is for the rounded corner. If the rounded corner circle radius is 0 the corner
        // will not be rounded. The other circle is the cutout.

        // Calculate the X distance between the center of the two adjacent circles using pythagorean
        // theorem.
        distanceBetweenCenters = cradleRadius + roundedCornerOffset
        distanceBetweenCentersSquared = distanceBetweenCenters * distanceBetweenCenters
        distanceY = verticalOffset + roundedCornerOffset
        distanceX = sqrt(
            (distanceBetweenCentersSquared - distanceY * distanceY).toDouble()
        ).toFloat()

        // Calculate the x position of the rounded corner circles.
        leftRoundedCornerCircleX = middle - distanceX
        rightRoundedCornerCircleX = middle + distanceX

        // Calculate the arc between the center of the two circles.
        cornerRadiusArcLength = Math.toDegrees(
            atan((distanceX / distanceY).toDouble())
        ).toFloat()
        cutoutArcOffset = ARC_QUARTER - cornerRadiusArcLength

        // Draw the starting line up to the left rounded corner.
        shapePath.lineTo(leftRoundedCornerCircleX - roundedCornerOffset, 0f)

        // Draw the arc for the left rounded corner circle. The bounding box is the area around the
        // circle's center which is at (leftRoundedCornerCircleX, roundedCornerOffset).
        shapePath.addArc(
            leftRoundedCornerCircleX - roundedCornerOffset,
            0f,
            leftRoundedCornerCircleX + roundedCornerOffset,
            roundedCornerOffset * 2,
            ANGLE_UP.toFloat(),
            cornerRadiusArcLength
        )

        // Draw the cutout circle.
        shapePath.addArc(
            middle - cradleRadius,
            -cradleRadius - verticalOffset,
            middle + cradleRadius,
            cradleRadius - verticalOffset,
            ANGLE_LEFT - cutoutArcOffset,
            cutoutArcOffset * 2 - ARC_HALF
        )

        // Draw an arc for the right rounded corner circle. The bounding box is the area around the
        // circle's center which is at (rightRoundedCornerCircleX, roundedCornerOffset).
        shapePath.addArc(
            rightRoundedCornerCircleX - roundedCornerOffset,
            0f,
            rightRoundedCornerCircleX + roundedCornerOffset,
            roundedCornerOffset * 2,
            ANGLE_UP - cornerRadiusArcLength,
            cornerRadiusArcLength
        )

        // Draw the ending line after the right rounded corner.
        shapePath.lineTo(length, 0f)
    }
}
