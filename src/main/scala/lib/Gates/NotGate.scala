package lib.Gates

import breeze.linalg.CSCMatrix
import lib.Prefs._

class NotGate(val index: Int) extends Gate {
  override def gate: CSCMatrix[QNum] = NotGate.getGate
}

object NotGate {
  def getGate = CSCMatrix(
    (0.0, 1.0),
    (1.0, 0.0)
  )
}
