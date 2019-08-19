package dsl.gates.dslquantum

import dsl.gates.DSLAnyGate
import prefs.Prefs.DSLVariable
import quantum.gates.{CircuitGate, ToffoliGate}

class ToffoliQ(control: List[DSLVariable], index: DSLVariable) extends DSLAnyGate {
  override def toQuantumGate(map: DSLVariable => Int): List[CircuitGate] = List(new ToffoliGate(control.map(map), map(index)))
}
