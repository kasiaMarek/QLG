package quantum

import quantum.circuit.Circuit
import quantum.qubits.Qubits

class CircuitExecutor(val circuit: Circuit, val bits: List[Int]) {
  assert(bits.size == circuit.size)
//  assert(!bits.forall(e => e == 0 || e == 1))
  private val size = bits.size
  private lazy val result = circuit.calculate(new Qubits(bits))

  override def toString(): String = {
    val circuitLines = circuit.toListOfString()
    val chooseResult = result.measureAllReturnAsListOfBits()
    (for(i <- 0 until size) yield i + "  <" + bits(i) + "> " + circuitLines(i) + " <" + chooseResult(i) + ">\n").mkString
  }

}
