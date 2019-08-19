package dsl.gates.classic

import dsl.gates.DSLAnyGate
import prefs.Prefs.DSLVariable
import quantum.gates.{CircuitGate, ToffoliGate}

class CopyCls(from: DSLVariable, to: DSLVariable) extends DSLAnyGate{
  assert(from != to)
  override def toQuantumGate(map: DSLVariable => Int): List[CircuitGate] = List(new ToffoliGate(map(from), map(to)))
}
