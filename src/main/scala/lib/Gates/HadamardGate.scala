package lib.Gates
import breeze.linalg.CSCMatrix
import breeze.numerics.sqrt
import lib.Prefs._

class HadamardGate(val index:Int) extends CircuitGate(HadamardGate.getGate) {
  override def expand: Int => AnyGate = normalExpand(1,index)
}

object HadamardGate {
  //TODO: fix Hadamard gate
  def getGate : CSCMatrix[QNum] = CSCMatrix(
    (1.0, 1.0),
    (1.0, -1.0)
  ) *:* 1.0/sqrt(2.0)
}