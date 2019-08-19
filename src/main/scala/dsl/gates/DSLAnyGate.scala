package dsl.gates

import prefs.Prefs.DSLVariable
import quantum.gates.CircuitGate

trait DSLAnyGate {
  def toQuantumGate(map: DSLVariable => Int): List[CircuitGate]

}
