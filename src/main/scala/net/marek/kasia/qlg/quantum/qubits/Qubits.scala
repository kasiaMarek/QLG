package net.marek.kasia.qlg.quantum.qubits

import breeze.linalg.DenseVector
import breeze.numerics.log
import org.apache.commons.math3.distribution.EnumeratedDistribution
import net.marek.kasia.qlg.quantum.QNum

case class Qubits(q: DenseVector[QNum], size: Int) {

  def this(q: DenseVector[QNum]) {
    //TODO: test if size is ok
    this(q, log(2.0, q.length.toDouble).toInt)
  }

   def this(list: List[Int]) {
    this(list.map(e => if(e == 0) Qubits.zero else Qubits.one).foldRight(DenseVector(QNum.one))(Qubits.tensor), list.length)
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
    import org.apache.commons.math3.util.Pair
    import java.util.ArrayList
    import java.lang.Double

    val mapping = new ArrayList[Pair[Int, Double]]()
    q.data.zipWithIndex.map(p => new Pair(p._2, new Double((p._1 * p._1).toDouble()))).foreach(mapping.add)
    new EnumeratedDistribution[Int](mapping).sample()
  }

  def getProbabilitiesForAQubit(index: Int): (Double, Double) = {
    val sumOfSquares : Array[QNum] => Double  = (array: Array[QNum]) => array.foldRight(0.0)((e, acc) => acc + (e * e).toDouble())

    q.data.sliding(1 << (size - index - 1))
      .zipWithIndex
      .foldLeft((0.0, 0.0))((p, arr) =>
        if(arr._2 % 2 == 0)
          (p._1 + sumOfSquares(arr._1), p._2)
        else
          (p._1, p._2 + sumOfSquares(arr._1)))
  }

  def getProbabilitiesOf1ForAll(): List[Double] = {
    val probabilities = new Array[Double](size)
    val qbitsWithIndex = q.data.zipWithIndex

    for((e,i) <- qbitsWithIndex; p <- 0 until size) {
      if(checkIf1ForIndex(i,p)) {
        probabilities.update(p, probabilities(p) + (e * e).toDouble())
      }
    }

    probabilities.toList.reverse
  }

  def checkIf1ForIndex(index: Int, qubit: Int): Boolean = (index >> qubit) % 2 == 1

}

object Qubits {
  def zero = DenseVector(QNum.one, QNum.zero)
  def one = DenseVector(QNum.zero, QNum.one)

  def tensor(v1: DenseVector[QNum], v2: DenseVector[QNum]) : DenseVector[QNum] =
    DenseVector(for (e1 <- v1.toArray; e2 <- v2.toArray) yield e1 * e2)

  def binToDec(bits: List[Int]) : Int = bits.fold(0)((i,b) => i*2 + b)

  def decToBin(number: Int) : List[Int] = if(number == 0) List(0) else recDecToBin(number, List())

  private def recDecToBin(number: Int, acc: List[Int]) : List[Int] =
    if (number == 0) acc else recDecToBin(number >> 1,  number%2 :: acc)
}
