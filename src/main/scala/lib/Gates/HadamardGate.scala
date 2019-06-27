package lib.Gates
import breeze.linalg.CSCMatrix
import lib.Prefs._

class HadamardGate(val index:Int) extends Gate {
  override def gate: CSCMatrix[QNum] = HadamardGate.getGate
}

object HadamardGate {
  //TODO: fix Hadamard gate
  val getGate : CSCMatrix[QNum] = CSCMatrix(
    (1.0, 1.0),
    (1.0, -1.0)
  )
}