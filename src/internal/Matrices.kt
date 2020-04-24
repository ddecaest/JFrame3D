package internal

import external.Vector3D
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.tan

internal object RotationMatrixFactory {

    fun createXRotationMatrix(counter: Float): RotationMatrix {
        val cosine = cos(counter * 0.5f)
        val sine = sin(counter * 0.5f)

        val rotationX: Array<FloatArray> = arrayOf(
            FloatArray(4),
            FloatArray(4),
            FloatArray(4),
            FloatArray(4)
        )
        rotationX[0][0] = 1f
        rotationX[1][1] = cosine
        rotationX[1][2] = sine
        rotationX[2][1] = -sine
        rotationX[2][2] = cosine
        rotationX[3][3] = 1f

        return rotationX
    }

    fun createYRotationMatrix(counter: Float): RotationMatrix {
        val cosine = cos(counter)
        val sine = sin(counter)

        val rotationZ: Array<FloatArray> = arrayOf(
            FloatArray(4),
            FloatArray(4),
            FloatArray(4),
            FloatArray(4)
        )
        rotationZ[0][0] = cosine
        rotationZ[0][2] = sine
        rotationZ[1][1] = 1f
        rotationZ[2][0] = -sine
        rotationZ[2][2] = cosine
        rotationZ[3][3] = 1f

        return rotationZ
    }

    fun createZRotationMatrix(counter: Float): RotationMatrix {
        val cosine = cos(counter)
        val sine = sin(counter)

        val rotationZ: Array<FloatArray> = arrayOf(
            FloatArray(4),
            FloatArray(4),
            FloatArray(4),
            FloatArray(4)
        )
        rotationZ[0][0] = cosine
        rotationZ[0][1] = sine
        rotationZ[1][0] = -sine
        rotationZ[1][1] = cosine
        rotationZ[2][2] = 1f
        rotationZ[3][3] = 1f

        return rotationZ
    }
}

internal object ProjectionMatrixFactory {

    fun createProjectionMatrix(fieldOfViewInDegrees: Double, aspectRatio: Float): ProjectionMatrix {
        val projectionMatrix: Array<FloatArray> = arrayOf(
            FloatArray(4),
            FloatArray(4),
            FloatArray(4),
            FloatArray(4)
        )

        val near = 0.1f
        val far = 1000.0f

        val fovRad = 1f / ((tan(fieldOfViewInDegrees * 0.5f / 180.0f * 3.14159f)).toFloat())

        projectionMatrix[0][0] = aspectRatio * fovRad
        projectionMatrix[1][1] = fovRad
        projectionMatrix[2][2] = far / (far - near)
        projectionMatrix[3][2] = (-far * near) / (far - near)
        projectionMatrix[2][3] = 1f

        return projectionMatrix
    }
}

typealias ProjectionMatrix = Array<FloatArray>
typealias RotationMatrix = Array<FloatArray>

fun multiplyVectorWithMatrix(vector: Vector3D, matrix: Array<FloatArray>): Vector3D {
    var x = vector.x * matrix[0][0] + vector.y * matrix[1][0] + vector.z * matrix[2][0] + matrix[3][0]
    var y = vector.x * matrix[0][1] + vector.y * matrix[1][1] + vector.z * matrix[2][1] + matrix[3][1]
    var z = vector.x * matrix[0][2] + vector.y * matrix[1][2] + vector.z * matrix[2][2] + matrix[3][2]

    val fourthDimension = vector.x * matrix[0][3] + vector.y * matrix[1][3] + vector.z * matrix[2][3] + matrix[3][3]
    if (fourthDimension != 0.0f) {
        x /= fourthDimension
        y /= fourthDimension
        z /= fourthDimension
    }

    return Vector3D(x, y, z)
}