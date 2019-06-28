package lib.Gates

import breeze.linalg.CSCMatrix
import lib.Qbits.Qbits
import org.scalatest.FunSuite

class FredkinGateTest extends FunSuite {

  test("*") {
    assert(new FredkinGate(1,(2,3)) * new Qbits(List(1,1,1,0)) == new Qbits(List(1,1,0,1)))
    assert(new FredkinGate(2,(0,3)) * new Qbits(List(0,1,1,1)) == new Qbits(List(1,1,1,0)))
  }

  test("get simple gate") {
    assert(FredkinGate.getGate == CSCMatrix(
      (1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0),
      (0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0),
      (0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0),
      (0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0),
      (0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0),
      (0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0),
      (0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0),
      (0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0)
    ))
  }

}
