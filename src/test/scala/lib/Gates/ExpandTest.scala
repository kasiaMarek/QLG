package lib.Gates

import breeze.linalg.CSCMatrix
import org.scalatest.FunSuite

class ExpandTest extends FunSuite {

  test("test expand") {
    assert(new NotGate(0).expand(2) == AnyGate(CSCMatrix(
      (0.0, 0.0, 1.0, 0.0),
      (0.0, 0.0, 0.0, 1.0),
      (1.0, 0.0, 0.0, 0.0),
      (0.0, 1.0, 0.0, 0.0)
    )))
  }

}
