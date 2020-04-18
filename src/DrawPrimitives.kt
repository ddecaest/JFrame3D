class Vector3D(var x: Float, var y: Float, var z: Float)

class Triangle(val a: Vector3D, val b: Vector3D, val c: Vector3D) {

    fun translateZ(translationValue: Float): Triangle {
        val aCopy = Vector3D(a.x, a.y, a.z + translationValue)
        val bCopy = Vector3D(b.x, b.y, b.z + translationValue)
        val cCopy = Vector3D(c.x, c.y, c.z + translationValue)
        return Triangle(aCopy, bCopy, cCopy)
    }
}

class Mesh(val triangles: List<Triangle>)


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