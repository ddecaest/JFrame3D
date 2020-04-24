package external

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