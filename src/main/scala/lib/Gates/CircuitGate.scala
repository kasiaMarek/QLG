package lib.Gates

import lib.Qbits.Qbits

abstract class CircuitGate() {
  def getExpandedGate: (Int) => AnyGate
  def *(q: Qbits): Qbits = Qbits(getExpandedGate(q.size).gate * q.q, q.size)
}
