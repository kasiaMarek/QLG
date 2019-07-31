package lib.SemiClassicLogic

import lib.Gates.{CNotGate, CircuitGate, NotGate}

sealed abstract class SemiClassicGate(val numOfInputs: Int, val outputIndex: Int)

case class Not(index:Int, override val outputIndex: Int) extends SemiClassicGate(1, outputIndex)

case class Copy(index:Int, override val outputIndex: Int) extends SemiClassicGate(1, outputIndex)

case class And(indices: List[Int], override val outputIndex: Int) extends SemiClassicGate(indices.length, outputIndex) {
  assert(indices.length >= 2)
}

object Not {
  def getQuantumGate(index: Int): CircuitGate = new NotGate(index)
}

object Copy {
  def getQuantumGate(index: Int, additional: Int): CircuitGate = new CNotGate(index, additional)
}

object And {
  def getQuantumGate(indices: List[Int], additional: Int): CircuitGate = new CNotGate(indices, additional)
}
