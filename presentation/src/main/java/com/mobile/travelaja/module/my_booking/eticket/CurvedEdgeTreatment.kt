package com.mobile.travelaja.module.my_booking.eticket

import com.google.android.material.shape.EdgeTreatment
import com.google.android.material.shape.ShapePath
import java.lang.Math.atan
import java.lang.Math.sqrt

class CurvedEdgeTreatment(
    private val diameter: Float,
    private val roundedCornerRadius: Float = 0f,
    private val horizontalOffset: Float = 0f,
    private val verticalOffset: Float = 0f
) : EdgeTreatment() {
    private val arcQuarter = 90f
    private val arcHalf = 180f
    private val angleUp = 270f
    private val angleLeft = 180f

    override fun getEdgePath(
        length: Float,
        center: Float,
        interpolation: Float,
        shapePath: ShapePath
    ) {
        val radius = diameter / 2f
        val roundedCornerOffset: Float = interpolation * roundedCornerRadius
        val middle: Float = center + horizontalOffset

        // The center offset of the cutout tweens between the vertical offset when attached, and the
        // cradleRadius as it becomes detached.
        val verticalOffset: Float =
            interpolation * verticalOffset + (1 - interpolation) * radius
        val verticalOffsetRatio = verticalOffset / radius
        if (verticalOffsetRatio >= 1.0f) {
            // Vertical offset is so high that there's no curve to draw in the edge, i.e., the fab is
            // actually above the edge so just draw a straight line.
            shapePath.lineTo(length, 0f)
            return  // Early exit.
        }

        // Calculate the path of the cutout by calculating the location of two adjacent circles. One
        // circle is for the rounded corner. If the rounded corner circle radius is 0 the corner will
        // not be rounded. The other circle is the cutout.
        // Calculate the X distance between the center of the two adjacent circles using pythagorean
        // theorem.
        val distanceBetweenCenters = radius + roundedCornerOffset
        val distanceBetweenCentersSquared =
            distanceBetweenCenters * distanceBetweenCenters
        val distanceY = verticalOffset + roundedCornerOffset
        val distanceX =
            sqrt(distanceBetweenCentersSquared - (distanceY * distanceY).toDouble()).toFloat()

        // Calculate the x position of the rounded corner circles.
        val leftRoundedCornerCircleX = middle - distanceX
        val rightRoundedCornerCircleX = middle + distanceX

        // Calculate the arc between the center of the two circles.
        val cornerRadiusArcLength =
            Math.toDegrees(atan(distanceX / distanceY.toDouble())).toFloat()
        val cutoutArcOffset: Float = arcQuarter - cornerRadiusArcLength

        // Draw the starting line up to the left rounded corner.
        shapePath.lineTo( /* x= */leftRoundedCornerCircleX, 0f)

        // Draw the arc for the left rounded corner circle. The bounding box is the area around the
        // circle's center which is at `(leftRoundedCornerCircleX, roundedCornerOffset)`.
        shapePath.addArc( /* left= */
            leftRoundedCornerCircleX - roundedCornerOffset, 0f,  /* right= */
            leftRoundedCornerCircleX + roundedCornerOffset,  /* bottom= */
            roundedCornerOffset * 2,  /* startAngle= */
            angleUp,  /* sweepAngle= */
            cornerRadiusArcLength
        )

        // Draw the cutout circle.
        shapePath.addArc( /* left= */
            middle - radius,  /* top= */
            -radius - verticalOffset,  /* right= */
            middle + radius,  /* bottom= */
            radius - verticalOffset,  /* startAngle= */
            angleLeft - cutoutArcOffset,  /* sweepAngle= */
            cutoutArcOffset * 2 - arcHalf
        )

        // Draw an arc for the right rounded corner circle. The bounding box is the area around the
        // circle's center which is at `(rightRoundedCornerCircleX, roundedCornerOffset)`.
        shapePath.addArc( /* left= */
            rightRoundedCornerCircleX - roundedCornerOffset, 0f,  /* right= */
            rightRoundedCornerCircleX + roundedCornerOffset,  /* bottom= */
            roundedCornerOffset * 2,  /* startAngle= */
            angleUp - cornerRadiusArcLength,  /* sweepAngle= */
            cornerRadiusArcLength
        )

        // Draw the ending line after the right rounded corner.
        shapePath.lineTo( /* x= */length, 0f)
    }
}