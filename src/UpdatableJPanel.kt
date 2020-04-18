import java.awt.Graphics
import javax.swing.JPanel

class UpdatableJPanel(var drawFunction: (Graphics) -> Unit) : JPanel() {

    companion object {
        fun createTriangleDrawingUpdatableJPanel(triangles: Collection<Triangle>): JPanel {
            return UpdatableJPanel({ graphics -> drawTriangles(graphics, triangles) })
        }
    }

    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        drawFunction.invoke(g)
    }
}

private fun drawTriangles(graphics: Graphics, triangles: Collection<Triangle>) {

    // TODO
}