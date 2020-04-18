import java.awt.Color
import java.awt.Dimension
import java.awt.Graphics
import javax.swing.JFrame
import javax.swing.JPanel
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.tan

class JFrame3D(var triangles: Collection<Triangle>): JFrame() {

    private var counter = 1f

    private val projectionMatrix: Array<FloatArray> = arrayOf(
        FloatArray(4),
        FloatArray(4),
        FloatArray(4),
        FloatArray(4)
    )

    init {
        val near = 0.1f
        val far = 1000.0f
        val fieldOfViewInDegrees = 90.0

        val aspectRatio = 1 // TODO height/width, but we dont know that yet here, pass that in via parameter?
        val fovRad = 1f / ((tan(fieldOfViewInDegrees * 0.5f / 180.0f * 3.14159f)).toFloat())

        projectionMatrix[0][0] = aspectRatio * fovRad
        projectionMatrix[1][1] = fovRad
        projectionMatrix[2][2] = far / (far - near)
        projectionMatrix[3][2] = (-far * near) / (far - near)
        projectionMatrix[2][3] = 1f
    }

    companion object {

        fun createTriangleDrawingJFrame(triangles: Collection<Triangle>): JFrame3D {
            val frame = JFrame3D(triangles)
            frame.minimumSize = Dimension(500, 500)
            frame.defaultCloseOperation = EXIT_ON_CLOSE

            val panel = DynamicJPanel { graphics -> frame.draw(graphics) }
            panel.background = Color.BLACK

            frame.add(panel)
            return frame
        }
    }

    private fun draw(graphics: Graphics) {
        counter += 0.001f;

        val rotationZ: Array<FloatArray> = arrayOf(
            FloatArray(4),
            FloatArray(4),
            FloatArray(4),
            FloatArray(4)
        )
        rotationZ[0][0] = cos(counter)
        rotationZ[0][1] = sin(counter)
        rotationZ[1][0] = -sin(counter)
        rotationZ[1][1] = cos(counter)
        rotationZ[2][2] = 1f
        rotationZ[3][3] = 1f

        val rotationX: Array<FloatArray> = arrayOf(
            FloatArray(4),
            FloatArray(4),
            FloatArray(4),
            FloatArray(4)
        )
        rotationX[0][0] = 1f
        rotationX[1][1] = cos(counter * 0.5f)
        rotationX[1][2] = sin(counter * 0.5f)
        rotationX[2][1] = -sin(counter * 0.5f)
        rotationX[2][2] = cos(counter * 0.5f)
        rotationX[3][3] = 1f

        graphics.color = Color.WHITE

        for (triangle: Triangle in triangles) {
            // Rotate that bitch
            val triangleRotatedZ = Triangle(
                multiplyVectorWithMatrix(triangle.a, rotationZ),
                multiplyVectorWithMatrix(triangle.b, rotationZ),
                multiplyVectorWithMatrix(triangle.c, rotationZ)
            )
            val triangleRotatedX = Triangle(
                multiplyVectorWithMatrix(triangleRotatedZ.a, rotationX),
                multiplyVectorWithMatrix(triangleRotatedZ.b, rotationX),
                multiplyVectorWithMatrix(triangleRotatedZ.c, rotationX)
            )
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

            drawTriangle(graphics, triangleProjected)
        }

        this.contentPane.repaint()
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

        override fun paintComponent(g: Graphics) {
            super.paintComponent(g)
            drawFunction.invoke(g)
        }
    }
}