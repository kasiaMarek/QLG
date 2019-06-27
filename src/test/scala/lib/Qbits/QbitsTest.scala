package lib.Qbits

import breeze.linalg.DenseVector
import org.scalatest.FunSuite


class QbitsTest extends FunSuite {
  test("tensor") {
    assert(Qbits.tensor(DenseVector(1.0, 2.0, 0.5), DenseVector(1.0, 0.5)) == DenseVector(1.0, 0.5, 2.0, 1.0, 0.5, 0.25))
  }

}
