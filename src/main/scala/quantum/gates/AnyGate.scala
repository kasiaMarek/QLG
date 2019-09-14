package quantum.gates

import breeze.linalg.CSCMatrix
import quantum.QNum
import quantum.qubits.Qubits
import prefs.Prefs._

case class AnyGate(gate: CSCMatrix[QNum]) {
  import AnyGate._

  def x(g: AnyGate): AnyGate = AnyGate(tensor(gate, g.gate))

  def *(q: Qubits): Qubits = Qubits((gate * q.q).toDenseVector, q.size)
}

object AnyGate {

  def tensor(g1: CSCMatrix[QNum], g2: CSCMatrix[QNum]): CSCMatrix[QNum] = {
    val builder = new CSCMatrix.Builder[QNum](rows=g1.rows*g2.rows, cols=g1.cols*g2.cols)
    for(((r1,c1),e1) <- g1.activeIterator) {
      val r = r1 * g2.rows
      val c = c1 * g2.cols
      for(((r2,c2),e2) <- g2.activeIterator) {
        builder.add(r + r2, c + c2, e1 * e2)
      }
    }
    builder.result
  }
}
