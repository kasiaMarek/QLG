package net.marek.kasia.qlg.quantum.gates

import breeze.linalg.CSCMatrix
import net.marek.kasia.qlg.quantum.QNum

class ToffoliGate(val control: List[Int], val index: Int) extends ControlGate {

  def this(singleControl: Int, index: Int) {
    this(List(singleControl), index)
  }

  def getGate(size: Int): CSCMatrix[QNum] = getGate(size, i => (i, i ^ (1 << (size - index - 1))), control)

  override def toString(size: Int): Array[Char] = {
    (for (i <- 0 until size) yield
      if (i == index) NOT
      else if (control.contains(i)) CONTROL
      else EMPTY).map(_.symbol).toArray
  }
}
