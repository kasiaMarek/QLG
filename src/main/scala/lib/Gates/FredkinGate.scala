package lib.Gates

import breeze.linalg.CSCMatrix
import lib.Prefs.QNum

class FredkinGate(val control:Int, val swap:(Int,Int)) extends CircuitGate() {
  assert(control != swap._1 && control != swap._2 && swap._1 != swap._2)

  private def makeListOfDiffs(list: List[Int], size: Int): List[Int] =
    (-1 :: list).zipAll(list, 0, size).map( p => p._2 - p._1 - 1)

  private def getUnsortedListOfVal(size: Int) = List(control, swap._1, swap._2).map(size - _ - 1)

  def getGate(size:Int): CSCMatrix[QNum] = {
    val indices = getUnsortedListOfVal(size)
    val sortedIndices = indices.sorted
    val ranges = makeListOfDiffs(sortedIndices, size).map(0 until 1 << _)

    val builder = new CSCMatrix.Builder[QNum](rows = 1 << size, cols = 1 << size)

    for (p1 <- ranges(0); p2 <- ranges(1); p3 <- ranges(2); p4 <- ranges(3))  {
      val i = p1 | p2 << sortedIndices(0) + 1 | p3 << sortedIndices(1) + 1 | p4 << sortedIndices(2) + 1 | 1 << indices.head
      val (s1, s2) = (i | 1 << indices(1), i | 1 << indices(2))

      builder.add(s1, s2, 1.0)
      builder.add(s2, s1, 1.0)
    }

    for(i <- 0 until (1 << size) if (i >> indices.head) % 2 == 0 || (i >> indices(1)) % 2 == (i >> indices(2)) % 2 ) {
      builder.add(i, i, 1.0)
    }

    builder.result
  }

  override def getExpandedGate: Int => AnyGate = (size: Int) => AnyGate(getGate(size))
}

