package dsl.gates.classic

import dsl.gates.DSLAnyGate
import prefs.Prefs.DSLVariable
import quantum.gates.{CircuitGate, NotGate}

class OrCls(args: (DSLVariable, DSLVariable), out: DSLVariable) extends DSLAnyGate {
  assert(args._1 != out && args._2 != out)

  override def toQuantumGate(map: DSLVariable => Int): List[CircuitGate] = {
    val outIndex = map(out)
    val argsIndices = (map(args._1), map(args._2))

    if (args._1 == args._2) new CopyCls(args._1, out).toQuantumGate(map)
    else List(
      List(new NotGate(argsIndices._1)),
      List(new NotGate(argsIndices._2)),
      new AndCls(args, out).toQuantumGate(map),
      List(new NotGate(outIndex)),
      List(new NotGate(argsIndices._1)),
      List(new NotGate(argsIndices._2))
    ).flatten
  }
}
