package net.marek.kasia.qlg.quantum.circuit

import net.marek.kasia.qlg.quantum.gates._
import net.marek.kasia.qlg.quantum.qubits.Qubits
import org.scalatest.FunSuite

class CircuitTest extends FunSuite {
  val testList: List[(Circuit, Qubits, Qubits)] = List(
    (new Circuit(
      List( //10110
        new FredkinGate(2, (1, 3)), //11100
        new NotGate(3), //11110
        new FredkinGate(3, (0,4)) //01111
      ), 5
    ),
      new Qubits(1, 0, 1, 1, 0),
      new Qubits(0, 1, 1, 1, 1)
    )
  )

  test("run circuit") {
    for((c, q, r) <- testList) {
      assert(c.calculate(q) == r)
    }
  }

}
