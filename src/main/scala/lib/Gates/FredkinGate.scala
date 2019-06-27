package lib.Gates

import breeze.linalg.CSCMatrix
import lib.Prefs._

class FredkinGate(val control:(Int,Int), val index:Int) extends Gate {
  override def gate: CSCMatrix[QNum] = FredkinGate.getGate
}

object FredkinGate {
  def getGate = CSCMatrix.tabulate(8, 8)(
    (i,j) =>
      if((i,j) == (5,6) || (i,j) == (6,5))
        1.0
      else
      if(i == j && i != 5 && i != 6)
        1.0
      else
        0.0
  )
}
