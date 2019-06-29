package lib.Gates

import  breeze.linalg.CSCMatrix
import lib.Qbits.Qbits
import org.scalatest.FunSuite

class FredkinGateTest extends FunSuite {

  test("*") {
    assert(new FredkinGate(1,(2,3)) * new Qbits(1,1,1,0) == new Qbits(1,1,0,1))
    assert(new FredkinGate(2,(0,3)) * new Qbits(0,1,1,1) == new Qbits(1,1,1,0))
  }

  test("get simple gate") {
    assert(new FredkinGate(0, (1,2)).getExpandedGate(3) == AnyGate(CSCMatrix(
      (1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0),
      (0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0),
      (0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0),
      (0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0),
      (0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0),
      (0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0),
      (0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0),
      (0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0)
    )))
  }

}
