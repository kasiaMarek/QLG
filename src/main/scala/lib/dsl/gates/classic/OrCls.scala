package lib.dsl.gates.classic

import lib.dsl.gates.DSLAnyGate
import lib.quantum.gates.{CircuitGate, NotGate}

class OrCls(args: (Int,Int), out: Int) extends DSLAnyGate {
  assert(args._1 != out && args._2 != out)

  override def toQuantumGate: List[CircuitGate] =
    if(args._1 == args._2) new CopyCls(args._1, out).toQuantumGate
    else List(
      List(new NotGate(args._1)),
      List(new NotGate(args._2)),
      new AndCls(args, out).toQuantumGate,
      List(new NotGate(out))
    ).flatten
}
