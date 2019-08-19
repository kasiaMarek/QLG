package dsl.gates.dslquantum

import dsl.gates.DSLAnyGate
import prefs.Prefs.DSLVariable
import quantum.gates.{CircuitGate, FredkinGate}

class FredkinQ(control: DSLVariable, swap: (DSLVariable, DSLVariable)) extends DSLAnyGate {
  override def toQuantumGate(map: DSLVariable => Int): List[CircuitGate] =
    List(new FredkinGate(map(control), (map(swap._1), map(swap._2))))
}
