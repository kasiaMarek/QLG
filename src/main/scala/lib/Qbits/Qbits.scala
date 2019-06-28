package lib.Qbits

import breeze.linalg.DenseVector
import breeze.numerics.log
import lib.Prefs.QNum

case class Qbits(q: DenseVector[QNum], size: Int) {

  def this(q: DenseVector[QNum]) {
    //TODO: test if size is ok
    this(q, log(2.0, q.length.toDouble).toInt)
  }

  def this(list: List[Int]) {
    this(list.map(e => if(e == 0) Qbits.zero else  Qbits.one).foldRight(DenseVector(1.0))(Qbits.tensor), list.length)
  }

}
object Qbits {
  def zero = DenseVector(1.0, 0.0)
  def one = DenseVector(0.0, 1.0)

  def tensor(v1: DenseVector[QNum], v2: DenseVector[QNum]) : DenseVector[QNum] =
    DenseVector(for(e1 <- v1.toArray; e2 <- v2.toArray) yield e1 * e2)

  def binToDec(bits: List[Int]) : Int = bits.fold(0)((i,b) => i*2 + b)
}


