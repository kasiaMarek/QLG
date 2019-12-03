package net.marek.kasia.qlg.quantum.gates

import breeze.linalg.CSCMatrix
import net.marek.kasia.qlg.quantum.QNum

class FredkinGate(val control: List[Int], val swap:(Int,Int)) extends ControlGate() {
  def this(singleControl: Int, swap:(Int,Int)) {
    this(List(singleControl), swap)
  }

  def getGate(size: Int): CSCMatrix[QNum] = getGate(size, genPair(size), control)

  def getValueOnIndex(num: Int, index: Int): Int = (num >> index)%2

  def genPair(size: Int)(i: Int) = {
    val mappedSwap = (size - swap._1 - 1, size - swap._2 - 1)
    val onSwapIndex = (getValueOnIndex(i, mappedSwap._1), getValueOnIndex(i, mappedSwap._2))
    (
      i,
      i ^ (onSwapIndex._1 << mappedSwap._2) ^ (onSwapIndex._2 << mappedSwap._2)
        ^ (onSwapIndex._1 << mappedSwap._1) ^ (onSwapIndex._2 << mappedSwap._1)
    )
  }

  override def toString(size: Int): Array[Char] =
    (for (i <- 0 until size) yield
      if (i == swap._1 || i == swap._2) SWAP
      else if (control.contains(i)) CONTROL
      else EMPTY).map(_.symbol).toArray
}

