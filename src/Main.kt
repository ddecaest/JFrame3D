import java.awt.Dimension
import javax.swing.JFrame
import javax.swing.JPanel

fun main() {
    val cube = createUnitCube()

    val panel = UpdatableJPanel.createTriangleDrawingUpdatableJPanel(cube.triangles)
    val frame = createFrame(panel)
    frame.isVisible = true
}

private fun createFrame(p: JPanel): JFrame {
    val frame = JFrame()
    frame.minimumSize = Dimension(500, 500)
    frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
    frame.add(p)
    return frame
}

private fun createUnitCube(): Mesh {
    return Mesh(
        listOf(
            Triangle(Vector3D(0f, 0f, 0f), Vector3D(0f, 1f, 0f), Vector3D(1f, 1f, 0f)),
            Triangle(Vector3D(0f, 0f, 0f), Vector3D(1f, 0f, 0f), Vector3D(1f, 0f, 0f)),

            Triangle(Vector3D(1f, 0f, 0f), Vector3D(1f, 1f, 0f), Vector3D(1f, 1f, 1f)),
            Triangle(Vector3D(1f, 0f, 0f), Vector3D(1f, 1f, 1f), Vector3D(1f, 0f, 1f)),

            Triangle(Vector3D(1f, 0f, 1f), Vector3D(1f, 1f, 1f), Vector3D(0f, 1f, 1f)),
            Triangle(Vector3D(1f, 0f, 1f), Vector3D(0f, 1f, 1f), Vector3D(0f, 0f, 1f)),

            Triangle(Vector3D(0f, 0f, 1f), Vector3D(0f, 1f, 1f), Vector3D(0f, 1f, 0f)),
            Triangle(Vector3D(0f, 0f, 1f), Vector3D(0f, 1f, 0f), Vector3D(0f, 0f, 0f)),

            Triangle(Vector3D(0f, 1f, 0f), Vector3D(0f, 1f, 1f), Vector3D(1f, 1f, 1f)),
            Triangle(Vector3D(0f, 1f, 0f), Vector3D(1f, 1f, 1f), Vector3D(1f, 1f, 0f)),

            Triangle(Vector3D(1f, 0f, 1f), Vector3D(0f, 0f, 1f), Vector3D(0f, 0f, 0f)),
            Triangle(Vector3D(1f, 0f, 1f), Vector3D(0f, 0f, 0f), Vector3D(1f, 0f, 0f))
        )
    )
}