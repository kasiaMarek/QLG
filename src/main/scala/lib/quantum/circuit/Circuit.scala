package lib.quantum.circuit

import lib.quantum.gates.CircuitGate
import lib.quantum.qubits.Qubits


class Circuit(val gates: List[CircuitGate], size: Int) {

  def calculate(bits: List[Int]): Qubits = calculate(new Qubits(getQbits(bits)))

  private def calculate(qubits: Qubits): Qubits = gates.foldLeft(qubits)((q, g) =>  g * q)

  private def getQbits(bits:List[Int]) : List[Int] = bits ::: List.fill(size - bits.length)(0)


  def toListOfString(): List[String] = {
    val table = gates.map(g => g.toString(size))

    (for (j <- 0 until size) yield
      (for(i <- gates.indices) yield
        " -" + table(i)(j) + "-"
      ).flatten.mkString
    ).toList
  }
}

