package internal

import external.Triangle
import java.awt.Graphics

internal object TriangleDrawingFunctions {

    fun drawFilledInTriangle(graphics: Graphics, triangle: Triangle) {
        val xPoints = intArrayOf(triangle.a.x.toInt(), triangle.b.x.toInt(), triangle.c.x.toInt())
        val yPoints = intArrayOf(triangle.a.y.toInt(), triangle.b.y.toInt(), triangle.c.y.toInt())
        graphics.fillPolygon(xPoints, yPoints, 3)
    }

    fun drawWireFrameTriangle(graphics: Graphics, triangle: Triangle) {
        val firstCorner = triangle.a
        val secondCorner = triangle.b
        val thirdCorner = triangle.c

        graphics.drawLine(
            firstCorner.x.toInt(),
            firstCorner.y.toInt(),
            secondCorner.x.toInt(),
            secondCorner.y.toInt()
        )
        graphics.drawLine(
            secondCorner.x.toInt(),
            secondCorner.y.toInt(),
            thirdCorner.x.toInt(),
            thirdCorner.y.toInt()
        )
        graphics.drawLine(
            thirdCorner.x.toInt(),
            thirdCorner.y.toInt(),
            firstCorner.x.toInt(),
            firstCorner.y.toInt()
        )

    }
}