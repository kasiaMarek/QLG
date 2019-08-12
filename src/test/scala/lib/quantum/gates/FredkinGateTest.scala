package lib.quantum.gates

import  breeze.linalg.CSCMatrix
import lib.quantum.qubits.Qubits
import org.scalatest.FunSuite

class FredkinGateTest extends FunSuite {

  test("*") {
    assert(new FredkinGate(1,(2,3)) * new Qubits(1,1,1,0) == new Qubits(1,1,0,1))
    assert(new FredkinGate(2,(0,3)) * new Qubits(0,1,1,1) == new Qubits(1,1,1,0))
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
