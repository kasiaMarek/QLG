package quantum.gates

import breeze.linalg.CSCMatrix
import breeze.numerics.pow
import quantum.QNum
import prefs.Prefs._

object IdGate {
  def getGate = CSCMatrix(
    (QNum.one, QNum.zero),
    (QNum.zero, QNum.one)
  )

  def getGate(size: Int) = {
    val n = pow(2, size)
    val builder = new CSCMatrix.Builder[QNum](rows = n, cols = n)
    for (i <- 0 until n) {
      builder.add(i, i, QNum.one)
    }
    builder.result
  }
}
