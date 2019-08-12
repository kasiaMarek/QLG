package lib.quantum.gates

import breeze.linalg.CSCMatrix
import lib.Prefs.QNum

class CNotGate(control: List[Int], index: Int) extends ControlGate {

  def this(singleControl: Int, index: Int) {
    this(List(singleControl), index)
  }

  def getGate(size:Int): CSCMatrix[QNum] = {
    val controlsVals = getUnsortedListOfVal(size, control)
    val indexVal = getUnsortedListOfVal(size, index)

    recGetGate(
      size,
      (i: Int) => {
        val ic = controlsVals.fold(i)((acc, con) => acc | 1 << con)
        (ic | 1 << indexVal.head, ic)
      },
      (i: Int) => controlsVals.exists(c => (i >> c) % 2 == 0),
      indexVal ::: controlsVals
    )
  }

  override def toString(size: Int): Array[Char] = {
    (for (i <- 0 until size) yield
      if (i == index) NOT
      else if (control.contains(i)) CONTROL
      else EMPTY).map(_.symbol).toArray
  }
}
