package quantum.gates

import quantum.qubits.Qubits
import org.scalatest.FunSuite

class ToffoliGateTest extends FunSuite {
  test("*") {
    assert(new ToffoliGate(2,0) * new Qubits(1,1,1,0) == new Qubits(0,1,1,0))
  }

}
