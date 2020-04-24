package external

import internal.MeshBasedDrawFunctionBuilder
import java.awt.Color
import java.awt.Dimension
import java.awt.Graphics
import javax.swing.JFrame
import javax.swing.JPanel

class JFrame3D private constructor(
    frameHeight: Int, frameWidth: Int, drawFunction: (Graphics, Vector3D) -> Unit
) : JFrame() {

    private val camera = Vector3D(0f, 0f, 0f)

    init {
        minimumSize = Dimension(frameWidth, frameHeight)
        defaultCloseOperation = EXIT_ON_CLOSE

        contentPane = object : JPanel() {
            override fun paintComponent(g: Graphics) {
                super.paintComponent(g)
                drawFunction.invoke(g, camera)
                this.repaint()
            }
        }
        contentPane.background = Color.BLACK
    }

    companion object {

        fun createTriangleDrawingJFrame(
            frameHeight: Int, frameWidth: Int, triangles: Mesh, effects: MutableList<DrawEffect>, drawMode: DrawMode
        ): JFrame3D {
            val drawFunctionBuilder = MeshBasedDrawFunctionBuilder(frameWidth, frameHeight)
            drawFunctionBuilder.mesh = triangles
            drawFunctionBuilder.effects = effects
            drawFunctionBuilder.drawMode = drawMode
            val drawFunction = drawFunctionBuilder.build()

            return JFrame3D(500, 500, drawFunction)
        }
    }
}