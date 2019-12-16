package net.marek.kasia.qlg.quantum

import breeze.linalg.{max, min}
import net.marek.kasia.qlg.quantum.circuit.Circuit
import net.marek.kasia.qlg.quantum.qubits.Qubits

class CircuitExecutor(val circuit: Circuit, val bits: List[Int], val variableNames: List[String]) {
  assert(bits.size == circuit.size)

  private val size = bits.size
  private lazy val result = circuit.calculate(new Qubits(bits))

  override def toString(): String = {
    val circuitLines = circuit.toListOfString()
    val chooseResult = result.measureAllReturnAsListOfBits()
    val probabilities = result.getProbabilitiesOf1ForAll()
    val maxLength = min(variableNames.map(_.length).max, 10)
    val varNamesWithPadding =  variableNames.map(v => v.slice(0, 10) + (for(_ <- 0 until max(0, maxLength - v.length)) yield " ").mkString)
    (for(i <- 0 until size) yield varNamesWithPadding(i) + "  <" + bits(i) + "> " + circuitLines(i) + " <" + chooseResult(i) + ">" + "  " + probabilities(i)).mkString("\n") + "\n"
  }

  def getResult = result

}
