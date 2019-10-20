package net.marek.kasia.qlg.quantum.gates
import breeze.linalg.CSCMatrix
import net.marek.kasia.qlg.quantum.QNum
import net.marek.kasia.qlg.prefs.Prefs._

class HadamardGate(index: Int) extends OneQubitGate(index, HADAMARD) {

  override def getBasicGate: AnyGate = AnyGate(HadamardGate.getGate)
}

object HadamardGate {
  def getGate : CSCMatrix[QNum] = CSCMatrix(
    (QNum.`1/sqrt2`, QNum.`1/sqrt2`),
    (QNum.`1/sqrt2`, QNum.`1/sqrt2` * QNum.`-one`)
  )
}