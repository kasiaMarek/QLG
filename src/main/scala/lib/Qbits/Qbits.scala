package lib.Qbits

import breeze.linalg.DenseVector
import breeze.numerics.log
import lib.Prefs.QNum
import org.apache.commons.math3.distribution.EnumeratedDistribution

case class Qbits(q: DenseVector[QNum], size: Int) {

  def this(q: DenseVector[QNum]) {
    //TODO: test if size is ok
    this(q, log(2.0, q.length.toDouble).toInt)
  }

  def this(list: List[Int]) {
    this(list.map(e => if(e == 0) Qbits.zero else Qbits.one).foldRight(DenseVector(1.0))(Qbits.tensor), list.length)
  }

  def this(bits: Int*) {
    this(bits.toList)
  }

  def measureAllReturnAsListOfBits() : List[Int] = {
    var list = Qbits.decToBin(measureAllReturnAsInt())
    for(_ <- 0 until(size - list.size)) {
      list = 0 :: list
    }
    list
  }

  def measureAllReturnAsInt(): Int = {
    val mapping = new java.util.ArrayList[org.apache.commons.math3.util.Pair[Int,java.lang.Double]]()
    for( e <- q.data.zipWithIndex.map(p => new org.apache.commons.math3.util.Pair(p._2, new java.lang.Double(p._1)))) {
      mapping.add(e)
    }
   new EnumeratedDistribution[Int](mapping).sample()
  }
}
object Qbits {
  def zero = DenseVector(1.0, 0.0)
  def one = DenseVector(0.0, 1.0)

  def tensor(v1: DenseVector[QNum], v2: DenseVector[QNum]) : DenseVector[QNum] =
    DenseVector(for(e1 <- v1.toArray; e2 <- v2.toArray) yield e1 * e2)

  def binToDec(bits: List[Int]) : Int = bits.fold(0)((i,b) => i*2 + b)

  def decToBin(number: Int) : List[Int] = if(number == 0) List(0) else recDecToBin(number, List())

  private def recDecToBin(number: Int, acc: List[Int]) : List[Int] =
    if(number == 0) acc else recDecToBin(number >> 1,  number%2 :: acc)
}


