package lib.quantum.qubits

import breeze.linalg.DenseVector
import breeze.numerics.log
import lib.Prefs.QNum
import org.apache.commons.math3.distribution.EnumeratedDistribution

case class Qubits(q: DenseVector[QNum], size: Int) {

  def this(q: DenseVector[QNum]) {
    //TODO: test if size is ok
    this(q, log(2.0, q.length.toDouble).toInt)
  }

   def this(list: List[Int]) {
    this(list.map(e => if(e == 0) Qubits.zero else Qubits.one).foldRight(DenseVector(1.0))(Qubits.tensor), list.length)
  }

  def this(bits: Int*) {
    this(bits.toList)
  }

  def measureAllReturnAsListOfBits() : List[Int] = {
    var list = Qubits.decToBin(measureAllReturnAsInt())
    for (_ <- 0 until(size - list.size)) {
      list = 0 :: list
    }
    list
  }

  def measureAllReturnAsInt(): Int = {
    val mapping = new java.util.ArrayList[org.apache.commons.math3.util.Pair[Int,java.lang.Double]]()
    for (e <- q.data.zipWithIndex.map(p => new org.apache.commons.math3.util.Pair(p._2, new java.lang.Double(p._1)))) {
      mapping.add(e)
    }
   new EnumeratedDistribution[Int](mapping).sample()
  }

  def getProbabilitiesForAQubit(index: Int): (QNum, QNum) = {
    val sumOfSquares = (array: Array[QNum]) => array.fold(0.0)((acc, e) => acc + e*e)

    q.data.sliding(1 << (size - index - 1))
      .zipWithIndex
      .foldLeft((0.0,0.0))((p, arr) =>
        if(arr._2 % 2 == 0)
          (p._1 + sumOfSquares(arr._1), p._2)
        else
          (p._1, p._2 + sumOfSquares(arr._1)))
  }
}

object Qubits {
  def zero = DenseVector(1.0, 0.0)
  def one = DenseVector(0.0, 1.0)

  def tensor(v1: DenseVector[QNum], v2: DenseVector[QNum]) : DenseVector[QNum] =
    DenseVector(for (e1 <- v1.toArray; e2 <- v2.toArray) yield e1 * e2)

  def binToDec(bits: List[Int]) : Int = bits.fold(0)((i,b) => i*2 + b)

  def decToBin(number: Int) : List[Int] = if(number == 0) List(0) else recDecToBin(number, List())

  private def recDecToBin(number: Int, acc: List[Int]) : List[Int] =
    if (number == 0) acc else recDecToBin(number >> 1,  number%2 :: acc)
}
