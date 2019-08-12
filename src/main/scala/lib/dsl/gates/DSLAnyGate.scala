package lib.dsl.gates

import lib.quantum.gates.CircuitGate

trait DSLAnyGate {
  def toQuantumGate: List[CircuitGate]

}
