package lib.Gates

import breeze.linalg.CSCMatrix
import breeze.numerics.pow
import lib.Prefs.QNum

class FredkinGate(val control:Int, val swap:(Int,Int)) extends CircuitGate(FredkinGate.getGate) {
  override def expand: Int => AnyGate = (size: Int) => {
    val c1 = pow(2, size - control - 1)
    val s1 = pow(2, size - swap._1 - 1)
    val s2 = pow(2, size - swap._2 - 1)
    val sumSize = pow(2,size)
    val list = (for(i <- 0 until sumSize if (i/c1) % 2 == 1 && (i/s1) % 2 == 0 && (i/s2) % 2 == 0) yield (i + s1, i + s2)).toList
    AnyGate(FredkinGate.getGate(list, sumSize))
  }
//  override def expand: Int => AnyGate = normalExpand(3,control)
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
