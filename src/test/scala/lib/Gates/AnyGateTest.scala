package lib.Gates

import breeze.linalg.{CSCMatrix, DenseVector}
import lib.Qbits.Qbits
import org.scalatest.FunSuite

class AnyGateTest extends FunSuite {

  test("tensor") {
    import AnyGate._
    assert(
      tensor(CSCMatrix(
        (1.0, 0.0, 0.0),
        (0.0, 0.0, 2.0)
      ), CSCMatrix(
        (2.0, 0.0),
        (0.0, 3.0)
      )) == CSCMatrix(
        (2.0, 0.0, 0.0, 0.0, 0.0, 0.0),
        (0.0, 3.0, 0.0, 0.0, 0.0, 0.0),
        (0.0, 0.0, 0.0, 0.0, 4.0, 0.0),
        (0.0, 0.0, 0.0, 0.0, 0.0, 6.0)
      )
    )
  }

  test("*") {
    assert(
      AnyGate(
        CSCMatrix(
          (2.0, 0.0),
          (0.0, 3.0)
        )
      ) * new Qbits(
        DenseVector(1.0,0.0)
      ) == new Qbits(
        DenseVector(2.0,0.0)
      )
    )
  }

}
