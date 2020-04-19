import java.awt.Color
import java.awt.Dimension
import java.awt.Graphics
import javax.swing.JFrame
import javax.swing.JPanel

class JFrame3D private constructor(
    private var mesh: Mesh,
    private val effects: Collection<DrawEffect>,
    frameHeight: Int,
    frameWidth: Int
) : JFrame() {

    private val projectionMatrix: Array<FloatArray> = ProjectionMatrixFactory.createProjectionMatrix(90.0, 1f)

    init {
        minimumSize = Dimension(frameWidth, frameHeight)
        defaultCloseOperation = EXIT_ON_CLOSE
    }

    companion object {

        fun createTriangleDrawingJFrame(triangles: Mesh, effects: Collection<DrawEffect>): JFrame3D {
            val frame = JFrame3D(triangles, effects, 500, 500)
            frame.add(DynamicJPanel { graphics -> frame.draw(graphics) })
            return frame
        }
    }

    private fun draw(graphics: Graphics) {
        graphics.color = Color.WHITE

        var translatedMesh = mesh
        for (effect: DrawEffect in effects) {
            translatedMesh = effect.apply(translatedMesh)
        }

        for (triangle: Triangle in translatedMesh.triangles) {
            val triangleProjectedOn2DSpace = `3DCoordinatesTo2DCoordinates`(triangle)
            drawTriangle(graphics, triangleProjectedOn2DSpace)
        }

        this.contentPane.repaint()
    }

    private fun `3DCoordinatesTo2DCoordinates`(triangleRotatedX: Triangle): Triangle {
        // Translate it, otherwise it is in our face
        val triangleTranslated = triangleRotatedX.translateZ(3f)
        // Project 3D coordinates to 2D plane
        val triangleProjected = Triangle(
            multiplyVectorWithMatrix(triangleTranslated.a, projectionMatrix),
            multiplyVectorWithMatrix(triangleTranslated.b, projectionMatrix),
            multiplyVectorWithMatrix(triangleTranslated.c, projectionMatrix)
        )
        // Coordinates are now between -1 and 1; so they need scaling to width and height of screen
        triangleProjected.a.x = (triangleProjected.a.x + 1f) * 0.5f * width
        triangleProjected.a.y = (triangleProjected.a.y + 1f) * 0.5f * height
        triangleProjected.b.x = (triangleProjected.b.x + 1f) * 0.5f * width
        triangleProjected.b.y = (triangleProjected.b.y + 1f) * 0.5f * height
        triangleProjected.c.x = (triangleProjected.c.x + 1f) * 0.5f * width
        triangleProjected.c.y = (triangleProjected.c.y + 1f) * 0.5f * height
        return triangleProjected
    }

    private fun drawTriangle(graphics: Graphics, triangle: Triangle) {
        val firstCorner = triangle.a
        val secondCorner = triangle.b
        val thirdCorner = triangle.c

        graphics.drawLine(firstCorner.x.toInt(), firstCorner.y.toInt(), secondCorner.x.toInt(), secondCorner.y.toInt())
        graphics.drawLine(secondCorner.x.toInt(), secondCorner.y.toInt(), thirdCorner.x.toInt(), thirdCorner.y.toInt())
        graphics.drawLine(thirdCorner.x.toInt(), thirdCorner.y.toInt(), firstCorner.x.toInt(), firstCorner.y.toInt())
    }

    private class DynamicJPanel(var drawFunction: (Graphics) -> Unit) : JPanel() {

        init {
            background = Color.BLACK
        }

        override fun paintComponent(g: Graphics) {
            super.paintComponent(g)
            drawFunction.invoke(g)
        }
    }
}