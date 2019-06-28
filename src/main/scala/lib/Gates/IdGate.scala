package lib.Gates

import breeze.linalg.CSCMatrix
import breeze.numerics.pow
import lib.Prefs.QNum

object IdGate {
  def getGate = CSCMatrix(
    (1.0, 0.0),
    (0.0, 1.0)
  )

  def getGate(t:Int) = {
    val n = pow(2,t)
    val builder = new CSCMatrix.Builder[QNum](rows = n, cols = n)
    for (i <- 0 until n) {
      builder.add(i, i, 1.0)
    }
    builder.result
  }
}
