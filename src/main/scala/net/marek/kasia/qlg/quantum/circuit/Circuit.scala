package net.marek.kasia.qlg.quantum.circuit

import net.marek.kasia.qlg.quantum.gates.CircuitGate
import net.marek.kasia.qlg.quantum.qubits.Qubits

class Circuit(val gates: List[CircuitGate], val size: Int) {

  def calculate(qubits: Qubits): Qubits = gates.foldLeft(qubits)((q, g) => g * q)

  def toListOfString(): List[String] = {
    val table = gates.map(g => g.toString(size))

    (for (j <- 0 until size) yield
      (for (i <- gates.indices) yield
        " -" + table(i)(j) + "-"
        ).flatten.mkString
      ).toList
  }
}

