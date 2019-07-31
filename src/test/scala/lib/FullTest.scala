package lib

import lib.SemiClassicLogic._
import org.scalatest.FunSuite

class FullTest extends FunSuite {

  val classicCircuit = new SemiClassicCircuit(3,
    And(List(1,0), 3),
    Not(1, 4),
    And(List(4,2), 5),
    Not(3,6),
    And(List(5,6), 7)
  )

  test("") {
    val qcircuit = classicCircuit.getQuantumCircuit()
    val qbits = classicCircuit.getQbits(List(0,0,1))
    val result = qcircuit.calculate(qbits).measureAllReturnAsListOfBits()
    print(result)

    assert(result.last == 1)
  }


}
