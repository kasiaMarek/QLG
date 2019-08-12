package lib.dsl.gates.classic

import lib.dsl.gates.DSLAnyGate
import lib.quantum.gates.{CNotGate, CircuitGate}

class AndCls(args: (Int, Int), out: Int) extends DSLAnyGate {
  assert(args._1 != out && args._2 != out)

  override def toQuantumGate: List[CircuitGate] =
    if(args._1 == args._2) new CopyCls(args._1, out).toQuantumGate
    else List(new CNotGate(List(args._1, args._2), out))

}
