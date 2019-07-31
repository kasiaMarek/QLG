package lib.Gates

import breeze.linalg.{CSCMatrix, DenseVector}
import breeze.numerics.pow
import lib.Qbits.Qbits

class NotGate(index:Int) extends OneQbitGate(index) {
  override def *(q: Qbits): Qbits = {
    val arr = q.q.toArray
    val d = pow(2, q.size - index - 1)
    Qbits(DenseVector(arr.sliding(d, d).sliding(2, 2).flatMap(e => e.reverse).toArray.flatten), q.size)
  }

  override def getBasicGate: AnyGate = AnyGate(NotGate.getGate)

}

object NotGate {
  def getGate = CSCMatrix(
    (0.0, 1.0),
    (1.0, 0.0)
  )
}
