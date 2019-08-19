package dsl.gates.classic

import dsl.gates.DSLAnyGate
import prefs.Prefs.DSLVariable
import quantum.gates.{CircuitGate, NotGate}

class NotCls(arg: DSLVariable, out: DSLVariable) extends DSLAnyGate{
  override def toQuantumGate(map: DSLVariable => Int): List[CircuitGate] =
    new CopyCls(arg, out).toQuantumGate(map) :+ new NotGate(map(out))
}
