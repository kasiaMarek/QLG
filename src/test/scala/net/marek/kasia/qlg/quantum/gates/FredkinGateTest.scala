package net.marek.kasia.qlg.quantum.gates

import breeze.linalg.CSCMatrix
import net.marek.kasia.qlg.quantum.qubits.Qubits
import org.scalatest.FunSuite

class FredkinGateTest extends FunSuite {

  test("*") {
    assert(new FredkinGate(1,(2,3)) * new Qubits(1,1,1,0) == new Qubits(1,1,0,1))
    assert(new FredkinGate(2,(0,3)) * new Qubits(0,1,1,1) == new Qubits(1,1,1,0))
    assert(new FredkinGate(List(2, 1),(0,3)) * new Qubits(0,1,1,1) == new Qubits(1,1,1,0))
    assert(new FredkinGate(List(2, 1),(0,3)) * new Qubits(0,0,1,1) == new Qubits(0,0,1,1))
    assert(new FredkinGate(List(2, 1, 4),(0,3)) * new Qubits(0,1,1,1,1) == new Qubits(1,1,1,0,1))
  }
}
