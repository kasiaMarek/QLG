package lib.circuit

import lib.Gates.CircuitGate
import lib.Qbits.Qbits


class Circuit(val gates: List[CircuitGate], val qbits: Qbits) {
  def calculate: Qbits = gates.foldLeft(qbits)((q,g) =>  g * q)
}

