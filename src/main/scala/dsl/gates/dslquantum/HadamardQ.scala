package dsl.gates.dslquantum

import dsl.gates.DSLAnyGate
import prefs.Prefs.DSLVariable
import quantum.gates.{CircuitGate, HadamardGate}

class HadamardQ(arg: DSLVariable) extends DSLAnyGate {
  override def toQuantumGate(map: DSLVariable => Int): List[CircuitGate] = List(new HadamardGate(map(arg)))
}
