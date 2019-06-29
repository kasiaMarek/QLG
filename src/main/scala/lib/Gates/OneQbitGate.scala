package lib.Gates

abstract class OneQbitGate(val index: Int) extends CircuitGate {

  def getBasicGate: AnyGate

  override def getExpandedGate: Int => AnyGate = (size: Int) => AnyGate(
    AnyGate.tensor(IdGate.getGate(index),
    AnyGate.tensor(getBasicGate.gate, IdGate.getGate(size - index - 1)))
  )
}
