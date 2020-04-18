class Vector3D(val x: Float, val y: Float, val z: Float)

class Triangle(val corners: Triple<Vector3D, Vector3D, Vector3D>) {

    constructor(a: Vector3D, b: Vector3D, c: Vector3D) : this(Triple(a, b, c))
}

class Mesh(val triangles: List<Triangle>)