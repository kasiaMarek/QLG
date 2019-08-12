package lib.dsl.gates.classic

import lib.dsl.gates.DSLAnyGate
import lib.quantum.gates.{CircuitGate, NotGate}

class NotCls(arg: Int) extends DSLAnyGate{
  override def toQuantumGate: List[CircuitGate] = List(new NotGate(arg))
}
