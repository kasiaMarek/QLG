package net.marek.kasia.qlg.parser

import net.marek.kasia.qlg.prefs.Prefs.DSLVariable
import net.marek.kasia.qlg.quantum.gates._

sealed trait DSLAnyGate {
  def toCircuitGateList(map: DSLVariable => Int): List[CircuitGate]
}

sealed trait DSLQGate extends DSLAnyGate {
  def toCircuitGate(map: DSLVariable => Int): CircuitGate
  override def toCircuitGateList(map: DSLVariable => Int): List[CircuitGate] = List(toCircuitGate(map))
}

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
