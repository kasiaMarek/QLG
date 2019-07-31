package lib.circuit

import lib.Gates.CircuitGate
import lib.Qbits.Qbits


class Circuit(val gates: List[CircuitGate]) {
  def calculate(qbits: Qbits): Qbits = gates.foldLeft(qbits)((q,g) =>  g * q)
}

