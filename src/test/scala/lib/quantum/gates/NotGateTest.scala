package lib.quantum.gates

import lib.quantum.qubits.Qubits
import org.scalatest.FunSuite

class NotGateTest extends FunSuite {

  test("*") {
    assert(new NotGate(1) * new Qubits(1,1,0,0) == new Qubits(1,0,0,0))
    assert(new NotGate(2) * new Qubits(1,1,0,0) == new Qubits(1,1,1,0))
  }

}
