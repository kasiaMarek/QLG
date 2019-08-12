package lib.quantum.gates

import lib.quantum.qubits.Qubits
import org.scalatest.FunSuite

class CNotGateTest extends FunSuite {
  test("*") {
    assert(new CNotGate(2,0) * new Qubits(1,1,1,0) == new Qubits(0,1,1,0))
  }

}
