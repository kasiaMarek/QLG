package quantum.gates
import breeze.linalg.CSCMatrix
import quantum.QNum
import prefs.Prefs._

class HadamardGate(index: Int) extends OneQubitGate(index, HADAMARD) {

  override def getBasicGate: AnyGate = AnyGate(HadamardGate.getGate)
}

object HadamardGate {
  def getGate : CSCMatrix[QNum] = CSCMatrix(
    (QNum.`1/sqrt2`, QNum.`1/sqrt2`),
    (QNum.`1/sqrt2`, QNum.`1/sqrt2` * QNum.`-one`)
  )
}