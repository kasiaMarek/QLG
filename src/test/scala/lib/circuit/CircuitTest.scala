package lib.circuit

import lib.Gates._
import lib.Qbits.Qbits
import org.scalatest.FunSuite

class CircuitTest extends FunSuite {
  val testList: List[(Circuit, Qbits, Qbits)] = List(
    (new Circuit(
      List( //10110
        new FredkinGate(2, (1, 3)), //11100
        new NotGate(3), //11110
        new FredkinGate(3, (0,4)) //01111
      )
    ),
      new Qbits(1, 0, 1, 1, 0),
      new Qbits(0, 1, 1, 1, 1)
    )
  )

  test("run circuit") {
    for((c, q, r) <- testList) {
      assert(c.calculate(q) == r)
    }
  }

}
