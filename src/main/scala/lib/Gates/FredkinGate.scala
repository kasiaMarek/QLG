package lib.Gates

import breeze.linalg.CSCMatrix
import breeze.numerics.pow
import lib.Prefs.QNum

class FredkinGate(val control:Int, val swap:(Int,Int)) extends CircuitGate(FredkinGate.getGate) {

  override def expand: Int => AnyGate = (size: Int) => {
    val unsortedListOfVal = List(size - control - 1, size - swap._1 - 1, size - swap._2 - 1)
    val listOfVal = unsortedListOfVal.sorted
    val list: List[(Int,Int)] = (for(
      p1 <- 0 until 1 << listOfVal(0);
      p2 <- 0 until 1 << (listOfVal(1) - listOfVal(0) - 1);
      p3 <- 0 until 1 << (listOfVal(2) - listOfVal(1) - 1);
      p4 <- 0 until 1 << (size - listOfVal(2) - 1)
    ) yield {
      val i = p1 | (p2 << (listOfVal(0) + 1)) | (p3 << (listOfVal(1) + 1)) | (p4 << (listOfVal(2) + 1))
      (
        i | (1 << unsortedListOfVal.head) | (1 << unsortedListOfVal(1)),
        i | (1 << unsortedListOfVal.head) | (1 << unsortedListOfVal(2))
      )
    }).toList
    AnyGate(FredkinGate.getGate(list, 1 << size))
  }
}

object FredkinGate {

  def getGate: CSCMatrix[QNum] = getGate(List((5,6)),8)

  def getGate(list:List[(Int, Int)], size:Int): CSCMatrix[QNum] = {
    val builder = new CSCMatrix.Builder[QNum](rows=size, cols=size)
    for(s <- list) {
      builder.add(s._1, s._2, 1.0)
      builder.add(s._2, s._1, 1.0)
    }

    for(i <- 0 until size if !list.exists(s => i == s._1 || i == s._2)) {
      builder.add(i, i, 1.0)
    }
    builder.result
  }
}
