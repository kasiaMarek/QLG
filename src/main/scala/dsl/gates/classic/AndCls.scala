package dsl.gates.classic

import dsl.gates.DSLAnyGate
import prefs.Prefs.DSLVariable
import quantum.gates.{CircuitGate, ToffoliGate}

class AndCls(args: (DSLVariable, DSLVariable), out: DSLVariable) extends DSLAnyGate {
  assert(args._1 != out && args._2 != out)

  override def toQuantumGate(map: DSLVariable => Int): List[CircuitGate] =
    if(args._1 == args._2) new CopyCls(args._1, out).toQuantumGate(map)
    else List(new ToffoliGate(List(map(args._1), map(args._2)), map(out)))

}
