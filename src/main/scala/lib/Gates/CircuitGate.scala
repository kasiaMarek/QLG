package lib.Gates

import breeze.linalg.CSCMatrix
import lib.Prefs.QNum
import lib.Qbits.Qbits

abstract class CircuitGate(gate: CSCMatrix[QNum]) extends AnyGate(gate) {
  def expand: (Int) => AnyGate
  def normalExpand(gateSize:Int, index:Int)(size:Int): AnyGate = expand(index, size - index - gateSize)
  override def *(q: Qbits): Qbits = Qbits(expand(q.size).gate * q.q, q.size)
}
