package lib.quantum.gates

import breeze.linalg.CSCMatrix
import lib.Prefs.QNum

abstract class ControlGate extends CircuitGate {

  override def getExpandedGate: Int => AnyGate = (size: Int) => AnyGate(getGate(size))

  def getGate(i: Int): CSCMatrix[QNum]

  def getUnsortedListOfVal(size: Int, indices: Int*): List[Int] = getUnsortedListOfVal(size, indices.toList)
  def getUnsortedListOfVal(size: Int, indices: List[Int]): List[Int] = indices.map(size - _ - 1)

  def recGetGate(size: Int, genPair: Int => (Int, Int), check: Int => Boolean, indices: List[Int]): CSCMatrix[QNum] = {
    val sortedIndices = indices.sorted
    val builder = new CSCMatrix.Builder[QNum](rows = 1 << size, cols = 1 << size)

    recFunctionGetGate(
      sortedIndices,
      makeListOfDiffs(sortedIndices, size).map(0 until 1 << _),
      genPair,
      builder,
      0,
      -1
    )

    for(i <- 0 until (1 << size) if check(i)) {
      builder.add(i, i, 1.0)
    }

    builder.result
  }

  private def recFunctionGetGate(sortedIndices: List[Int],
                         ranges: List[Range],
                         genPair: Int => (Int, Int),
                         builder: CSCMatrix.Builder[QNum],
                         acc: Int,
                         prev: Int
                        ): Unit = {
    if(sortedIndices.isEmpty) {
      for (n <- ranges.head) {
        val (s1, s2) = genPair(acc | n << prev + 1)
        builder.add(s1, s2, 1.0)
        builder.add(s2, s1, 1.0)
      }
    } else {
      for (n <- ranges.head) {
        recFunctionGetGate(sortedIndices.tail, ranges.tail, genPair, builder, acc | n << prev + 1, sortedIndices.head)
      }
    }
  }

  private def makeListOfDiffs(list: List[Int], size: Int): List[Int] =
    (-1 :: list).zipAll(list, 0, size).map( p => p._2 - p._1 - 1)

}
