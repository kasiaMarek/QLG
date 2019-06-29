package lib.Gates

import lib.Qbits.Qbits
import org.scalatest.FunSuite

class NotGateTest extends FunSuite {

  test("*") {
    assert(new NotGate(1) * new Qbits(1,1,0,0) == new Qbits(1,0,0,0))
    assert(new NotGate(2) * new Qbits(1,1,0,0) == new Qbits(1,1,1,0))
  }

}
