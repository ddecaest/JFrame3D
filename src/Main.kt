import external.DrawEffect
import external.DrawMode
import external.RotationEffect
import external.JFrame3D
import external.Mesh
import external.Triangle
import external.Vector3D

fun main() {
    val cube = createUnitCube()
    val effects = mutableListOf<DrawEffect>(RotationEffect.rotateX(), RotationEffect.rotateY(), RotationEffect.rotateZ())
    val drawModeChosen = DrawMode.WIRE_FRAME
    val frame1= JFrame3D.createTriangleDrawingJFrame(500, 500, cube, effects, drawModeChosen)
    frame1.isVisible = true
}

private fun createUnitCube(): Mesh {
    return Mesh(
        listOf(
            Triangle(
                Vector3D(0f, 0f, 0f),
                Vector3D(0f, 1f, 0f),
                Vector3D(1f, 1f, 0f)
            ),
            Triangle(
                Vector3D(0f, 0f, 0f),
                Vector3D(1f, 1f, 0f),
                Vector3D(1f, 0f, 0f)
            ),

            Triangle(
                Vector3D(1f, 0f, 0f),
                Vector3D(1f, 1f, 0f),
                Vector3D(1f, 1f, 1f)
            ),
            Triangle(
                Vector3D(1f, 0f, 0f),
                Vector3D(1f, 1f, 1f),
                Vector3D(1f, 0f, 1f)
            ),

            Triangle(
                Vector3D(1f, 0f, 1f),
                Vector3D(1f, 1f, 1f),
                Vector3D(0f, 1f, 1f)
            ),
            Triangle(
                Vector3D(1f, 0f, 1f),
                Vector3D(0f, 1f, 1f),
                Vector3D(0f, 0f, 1f)
            ),

            Triangle(
                Vector3D(0f, 0f, 1f),
                Vector3D(0f, 1f, 1f),
                Vector3D(0f, 1f, 0f)
            ),
            Triangle(
                Vector3D(0f, 0f, 1f),
                Vector3D(0f, 1f, 0f),
                Vector3D(0f, 0f, 0f)
            ),

            Triangle(
                Vector3D(0f, 1f, 0f),
                Vector3D(0f, 1f, 1f),
                Vector3D(1f, 1f, 1f)
            ),
            Triangle(
                Vector3D(0f, 1f, 0f),
                Vector3D(1f, 1f, 1f),
                Vector3D(1f, 1f, 0f)
            ),

            Triangle(
                Vector3D(1f, 0f, 1f),
                Vector3D(0f, 0f, 1f),
                Vector3D(0f, 0f, 0f)
            ),
            Triangle(
                Vector3D(1f, 0f, 1f),
                Vector3D(0f, 0f, 0f),
                Vector3D(1f, 0f, 0f)
            )
        )
    )
}