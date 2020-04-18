import java.awt.Dimension
import java.awt.Graphics
import javax.swing.JFrame
import javax.swing.JPanel

class JFrame3D : JFrame() {

    companion object {

        fun createTriangleDrawingJFrame(triangles: Collection<Triangle>): JFrame3D {
            return create({ graphics -> drawTriangles(graphics, triangles) })
        }

        private fun create(drawFunction: (Graphics) -> Unit): JFrame3D {
            val panel = DynamicJPanel(drawFunction)

            val frame = JFrame3D()
            frame.minimumSize = Dimension(500, 500)
            frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
            frame.add(panel)
            return frame
        }

        private fun drawTriangles(graphics: Graphics, triangles: Collection<Triangle>) {
            // TODO
        }
    }


    private class DynamicJPanel(var drawFunction: (Graphics) -> Unit) : JPanel() {

        override fun paintComponent(g: Graphics) {
            super.paintComponent(g)
            drawFunction.invoke(g)
        }
    }
}