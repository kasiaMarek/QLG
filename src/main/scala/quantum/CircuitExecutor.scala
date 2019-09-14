package quantum

import quantum.circuit.Circuit
import quantum.qubits.Qubits

class CircuitExecutor(val circuit: Circuit, val bits: List[Int], val variableNames: List[String]) {
  assert(bits.size == circuit.size)

  private val size = bits.size
  private lazy val result = circuit.calculate(new Qubits(bits))

  override def toString(): String = {
    val circuitLines = circuit.toListOfString()
    val chooseResult = result.measureAllReturnAsListOfBits()
    val probabilities = result.getProbabilitiesOf1ForAll()
    (for(i <- 0 until size) yield variableNames(i) + "  <" + bits(i) + "> " + circuitLines(i) + " <" + chooseResult(i) + ">" + "  " + probabilities(i)).mkString("\n")
  }

}
