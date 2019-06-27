package lib.Gates

import lib.Prefs.QNum
import breeze.linalg.CSCMatrix
trait Gate {

  def gate : CSCMatrix[QNum]
}
