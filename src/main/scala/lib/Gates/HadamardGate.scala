package lib.Gates
import breeze.linalg.CSCMatrix
import breeze.numerics.sqrt
import lib.Prefs._

class HadamardGate(index: Int) extends OneQbitGate(index) {

  override def getBasicGate: AnyGate = AnyGate(HadamardGate.getGate)
}

object HadamardGate {
  //TODO: fix Hadamard gate
  def getGate : CSCMatrix[QNum] = CSCMatrix(
    (1.0, 1.0),
    (1.0, -1.0)
  ) *:* 1.0/sqrt(2.0)
}