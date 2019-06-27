package lib.Qbits

import breeze.linalg.DenseVector
import lib.Prefs.QNum

class Qbits(list:List[Int]) {
  assert(list.forall(e => e == 1 || e == 0))
}

object Qbits {
  def zero = DenseVector(1.0, 0.0)
  def one = DenseVector(0.0, 1.0)

  def tensor(v1: DenseVector[QNum], v2: DenseVector[QNum]) : DenseVector[QNum] =
    DenseVector(for(e1 <- v1.toArray; e2 <- v2.toArray) yield e1 * e2)
}
