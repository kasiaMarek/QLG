package parser

import prefs.Prefs.DSLVariable
import quantum.gates._

sealed trait DSLAnyGate {
  def toCircuitGateList(map: DSLVariable => Int): List[CircuitGate]
}

sealed trait DSLClsGate extends DSLAnyGate {
  def toDSLQGate(): List[DSLQGate]
  override def toCircuitGateList(map: DSLVariable => Int): List[CircuitGate] = toDSLQGate().map(_.toCircuitGate(map))
}

sealed trait DSLQGate extends DSLAnyGate {
  def toCircuitGate(map: DSLVariable => Int): CircuitGate
  override def toCircuitGateList(map: DSLVariable => Int): List[CircuitGate] = List(toCircuitGate(map))
}

//classical gates
class AndCls(args: List[DSLVariable], out: DSLVariable) extends DSLClsGate {
  override def toDSLQGate(): List[DSLQGate] = {
    val actArgs = args.distinct
    if(actArgs.size == 1) new CopyCls(actArgs.head, out).toDSLQGate()
    else List(new ToffoliQ(actArgs, out))
  }
}

class CopyCls(from: DSLVariable, to: DSLVariable) extends DSLClsGate {
  assert(from != to)
  override def toDSLQGate(): List[DSLQGate] = List(new ToffoliQ(List(from), to))
}

class NotCls(arg: DSLVariable, out: DSLVariable) extends DSLClsGate {
  override def toDSLQGate(): List[DSLQGate] = new CopyCls(arg, out).toDSLQGate() :+ new NotQDSL(out)
}

class OrCls(args: List[DSLVariable], out: DSLVariable) extends DSLClsGate {
    override def toDSLQGate(): List[DSLQGate] = {
      val actArgs = args.distinct
      if(actArgs.size == 1) new CopyCls(actArgs.head, out).toDSLQGate()
      else ((actArgs.map(new NotQDSL(_)) ::: new AndCls(actArgs, out).toDSLQGate()) ::: actArgs.map(new NotQDSL(_))) :+ new NotQDSL(out)
    }
}

//quantum gates
class HadamardQ(arg: DSLVariable) extends DSLQGate {
  override def toCircuitGate(map: DSLVariable => Int): CircuitGate = new HadamardGate(map(arg))
}

class ToffoliQ(control: List[DSLVariable], index: DSLVariable) extends DSLQGate {
  override def toCircuitGate(map: DSLVariable => Int): CircuitGate = new ToffoliGate(control.map(map), map(index))
}

class NotQDSL(index: DSLVariable) extends DSLQGate {
  override def toCircuitGate(map: DSLVariable => Int): CircuitGate = new NotGate(map(index))
}

class FredkinQ(control: List[DSLVariable], swap: (DSLVariable, DSLVariable)) extends DSLQGate {
  override def toCircuitGate(map: DSLVariable => Int): CircuitGate = new FredkinGate(control.map(map(_)), (map(swap._1), map(swap._2)))
}
