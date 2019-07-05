package lib.Gates

import lib.Qbits.Qbits
import org.scalatest.FunSuite

class CNotGateTest extends FunSuite {
  test("*") {
    assert(new CNotGate(2,0) * new Qbits(1,1,1,0) == new Qbits(0,1,1,0))
  }

}
