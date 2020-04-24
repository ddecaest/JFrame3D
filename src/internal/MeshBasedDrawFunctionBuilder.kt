package internal

import external.DrawEffect
import external.DrawMode
import external.Mesh
import external.Triangle
import external.Vector3D
import java.awt.Color
import java.awt.Graphics
import kotlin.math.sqrt

internal class MeshBasedDrawFunctionBuilder(private val widthScreen:Int, private val heightScreen:Int) {

    private val aspectRatio = (heightScreen/widthScreen).toFloat()
    var effects = mutableListOf<DrawEffect>()
    var drawMode = DrawMode.FILL
    var mesh = Mesh(emptyList())
    var fovInDegrees = 90.0

    fun build() : (Graphics, Vector3D) -> Unit {
        val projectionMatrix: Array<FloatArray> = ProjectionMatrixFactory.createProjectionMatrix(fovInDegrees, aspectRatio)

        val drawTriangleFunction = when(drawMode) {
            DrawMode.WIRE_FRAME -> TriangleDrawingFunctions::drawWireFrameTriangle
            DrawMode.FILL -> TriangleDrawingFunctions::drawFilledInTriangle
        }

        return { graphics: Graphics, camera: Vector3D -> this.draw(graphics, drawTriangleFunction, projectionMatrix, camera, widthScreen, heightScreen) }
    }

    private fun draw(
        graphics: Graphics,
        drawTriangleFunction: (Graphics, Triangle) -> Unit,
        projectionMatrix: Array<FloatArray>,
        camera: Vector3D,
        widthScreen: Int,
        heightScreen: Int
    ) {
        graphics.color = Color.WHITE

        var translatedMesh = mesh
        for (effect: DrawEffect in effects) {
            translatedMesh = effect.apply(translatedMesh)
        }

        for (triangle: Triangle in translatedMesh.triangles) {
            // Transform the coordinates away from the camera, otherwise it is in our face!
            val triangleTranslated = triangle.translateZ(3f)
            // Only if visible...
            val normalizedNormal = calculateNormalizedNormal(triangleTranslated)
            if (
                normalizedNormal.x * (triangleTranslated.a.x - camera.x)
                + normalizedNormal.y * (triangleTranslated.a.y - camera.y)
                + normalizedNormal.z * (triangleTranslated.a.z - camera.z)
                > 0f
            ) {
                val triangleProjected = Triangle(
                    multiplyVectorWithMatrix(triangleTranslated.a, projectionMatrix),
                    multiplyVectorWithMatrix(triangleTranslated.b, projectionMatrix),
                    multiplyVectorWithMatrix(triangleTranslated.c, projectionMatrix)
                )
                triangleProjected.a.x = (triangleProjected.a.x + 1f) * 0.5f * widthScreen
                triangleProjected.a.y = (triangleProjected.a.y + 1f) * 0.5f * heightScreen
                triangleProjected.b.x = (triangleProjected.b.x + 1f) * 0.5f * widthScreen
                triangleProjected.b.y = (triangleProjected.b.y + 1f) * 0.5f * heightScreen
                triangleProjected.c.x = (triangleProjected.c.x + 1f) * 0.5f * widthScreen
                triangleProjected.c.y = (triangleProjected.c.y + 1f) * 0.5f * heightScreen
                drawTriangleFunction.invoke(graphics, triangleProjected)
            }
        }
    }

    private fun calculateNormalizedNormal(triangleTranslated: Triangle): Vector3D {
        val line1 = Vector3D(
            triangleTranslated.c.x - triangleTranslated.a.x,
            triangleTranslated.c.y - triangleTranslated.a.y,
            triangleTranslated.c.z - triangleTranslated.a.z
        )
        val line2 = Vector3D(
            triangleTranslated.b.x - triangleTranslated.a.x,
            triangleTranslated.b.y - triangleTranslated.a.y,
            triangleTranslated.b.z - triangleTranslated.a.z
        )
        // Crossproduct of two above
        val normal = Vector3D(
            line1.y * line2.z - line1.z * line2.y,
            line1.z * line2.x - line1.x * line2.z,
            line1.x * line2.y - line1.y * line2.x
        )
        val length = sqrt(normal.x * normal.x + normal.y * normal.y + normal.z * normal.z)
        return Vector3D(normal.x / length, normal.y / length, normal.z / length)
    }
}