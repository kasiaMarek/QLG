package net.marek.kasia.qlg.quantum.gates

abstract class OneQubitGate(val index: Int, val symbol: Symbol) extends CircuitGate {

  def getBasicGate: AnyGate

  override def getExpandedGate: Int => AnyGate = (size: Int) => AnyGate(
    AnyGate.tensor(IdGate.getGate(index),
    AnyGate.tensor(getBasicGate.gate, IdGate.getGate(size - index - 1)))
  )

  override def toString(size: Int): Array[Char] =
    (for(i <- 0 until size) yield if(i == index) symbol else EMPTY).map(_.symbol).toArray

}
