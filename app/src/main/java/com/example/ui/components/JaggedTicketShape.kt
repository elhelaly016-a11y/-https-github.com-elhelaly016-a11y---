package com.example.ui.components

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection

/**
 * Creates a coupon ticket shape with scalloped/jagged teeth along the left and right or top and bottom edges.
 */
class JaggedTicketShape(
    private val notchRadius: Float = 14f,
    private val toothCount: Int = 8
) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val path = Path().apply {
            reset()
            moveTo(0f, 0f)
            lineTo(size.width, 0f)

            // Right jagged scalloped teeth
            val rightStep = size.height / toothCount
            for (i in 0 until toothCount) {
                val y1 = i * rightStep
                val yMid = y1 + rightStep / 2f
                val y2 = (i + 1) * rightStep
                lineTo(size.width, y1)
                lineTo(size.width - notchRadius, yMid)
                lineTo(size.width, y2)
            }

            lineTo(size.width, size.height)
            lineTo(0f, size.height)

            // Left jagged scalloped teeth
            for (i in toothCount - 1 downTo 0) {
                val y2 = (i + 1) * rightStep
                val yMid = i * rightStep + rightStep / 2f
                val y1 = i * rightStep
                lineTo(0f, y2)
                lineTo(notchRadius, yMid)
                lineTo(0f, y1)
            }

            close()
        }
        return Outline.Generic(path)
    }
}
