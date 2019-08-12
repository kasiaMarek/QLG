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
    val bits = List(0,0,1)
    val result = qcircuit.calculate(bits)
    println(result)
    val resultBits = result.measureAllReturnAsListOfBits()
    qcircuit
      .toListOfString()
      .zipWithIndex
      .map(e => {
        val value = if(e._2 >= bits.size) 0 else bits(e._2)
        e._2.toString + "  <" + value.toString + ">" + e._1 + " <" + resultBits(e._2).toString + ">"
      }).foreach(println)

  }


}
