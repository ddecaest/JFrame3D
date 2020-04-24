package external

import internal.RotationMatrix
import internal.RotationMatrixFactory
import internal.multiplyVectorWithMatrix

interface DrawEffect {
    fun apply(mesh: Mesh): Mesh
}

class RotationEffect(private val rotationMatrixCreationFunction: (Float) -> RotationMatrix) :
    DrawEffect {

    private var counter = 1f

    companion object {

        fun rotateX(): RotationEffect {
            return RotationEffect { counter: Float ->
                RotationMatrixFactory.createXRotationMatrix(
                    counter
                )
            }
        }

        fun rotateY(): RotationEffect {
            return RotationEffect { counter: Float ->
                RotationMatrixFactory.createYRotationMatrix(
                    counter
                )
            }
        }

        fun rotateZ(): RotationEffect {
            return RotationEffect { counter: Float ->
                RotationMatrixFactory.createZRotationMatrix(
                    counter
                )
            }
        }
    }


    override fun apply(mesh: Mesh): Mesh {
        val rotationMatrix = rotationMatrixCreationFunction.invoke(counter)

        val rotatedTriangles = mesh.triangles.map {
            Triangle(
                multiplyVectorWithMatrix(it.a, rotationMatrix),
                multiplyVectorWithMatrix(it.b, rotationMatrix),
                multiplyVectorWithMatrix(it.c, rotationMatrix)
            )
        }

        counter += 0.00025f

        return Mesh(rotatedTriangles)
    }
}