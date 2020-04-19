import java.awt.Dimension
import javax.swing.JFrame
import javax.swing.JPanel

fun main() {
    val cube = createUnitCube()

    val frame1= JFrame3D.createTriangleDrawingJFrame(cube, listOf( RotationEffect.rotateX()))
    frame1.isVisible = true
}

private fun createUnitCube(): Mesh {
    return Mesh(
        listOf(
            Triangle(Vector3D(0f, 0f, 0f), Vector3D(0f, 1f, 0f), Vector3D(1f, 1f, 0f)),
            Triangle(Vector3D(0f, 0f, 0f), Vector3D(1f, 1f  , 0f), Vector3D(1f, 0f, 0f)),

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