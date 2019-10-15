package quantum.gates

import breeze.linalg.CSCMatrix
import quantum.QNum

class FredkinGate(val control: List[Int], val swap:(Int,Int)) extends ControlGate() {
  def this(singleControl: Int, swap:(Int,Int)) {
    this(List(singleControl), swap)
  }

  def getGate(size:Int): CSCMatrix[QNum] = {
    val controlIndices = getUnsortedListOfVal(size, control)
    val swapIndices = getUnsortedListOfVal(size, swap._1, swap._2)

    recGetGate(
      size,
      (i: Int) => {
        val ic = controlIndices.fold(i)((acc, con) => acc | 1 << con)
        (ic | 1 << swapIndices(0) , ic | 1 << swapIndices(1))
      },
      (i: Int) => controlIndices.exists(c => (i >> c) % 2 == 0) || (i >> swapIndices(0)) % 2 == (i >> swapIndices(1)) % 2,
      controlIndices ::: swapIndices
    )
  }

  override def toString(size: Int): Array[Char] =
    (for (i <- 0 until size) yield
      if (i == swap._1 || i == swap._2) SWAP
      else if (control.contains(i)) CONTROL
      else EMPTY).map(_.symbol).toArray
}

