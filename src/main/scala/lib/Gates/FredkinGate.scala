package lib.Gates

import breeze.linalg.CSCMatrix
import lib.Prefs.QNum

class FredkinGate(val control: Int, val swap:(Int,Int)) extends ControlGate() {
  assert(control != swap._1 && control != swap._2 && swap._1 != swap._2)

  def getGate(size:Int): CSCMatrix[QNum] = {
    val indices = getUnsortedListOfVal(size, control, swap._1, swap._2)

    recGetGate(
      size,
      (i: Int) => (i | 1 << indices(1) | 1 << indices.head, i | 1 << indices(2) | 1 << indices.head),
      (i: Int) => (i >> indices.head) % 2 == 0 || (i >> indices(1)) % 2 == (i >> indices(2)) % 2,
      indices
    )
  }
}

