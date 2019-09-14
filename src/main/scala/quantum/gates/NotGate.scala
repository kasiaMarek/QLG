package quantum.gates

import breeze.linalg.{CSCMatrix, DenseVector}
import breeze.numerics.pow
import quantum.QNum
import quantum.qubits.Qubits

class NotGate(index:Int) extends OneQubitGate(index, NOT) {
  override def *(q: Qubits): Qubits = {
    val arr = q.q.toArray
    val d = pow(2, q.size - index - 1)
    Qubits(DenseVector(arr.sliding(d, d).sliding(2, 2).flatMap(e => e.reverse).toArray.flatten), q.size)
  }

  override def getBasicGate: AnyGate = AnyGate(NotGate.getGate)

}

object NotGate {
  def getGate = CSCMatrix(
    (QNum.zero, QNum.one),
    (QNum.one, QNum.zero)
  )
}
