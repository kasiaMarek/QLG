package lib.dsl.gates.classic

import lib.dsl.gates.DSLAnyGate
import lib.quantum.gates.{CNotGate, CircuitGate}

class CopyCls(from: Int, to: Int) extends DSLAnyGate{
  assert(from != to)
  override def toQuantumGate: List[CircuitGate] = List(new CNotGate(from, to))
}
