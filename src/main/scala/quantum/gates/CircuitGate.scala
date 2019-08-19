package quantum.gates

import quantum.qubits.Qubits

abstract class CircuitGate() {
  def getExpandedGate: Int => AnyGate
  def *(q: Qubits): Qubits = Qubits(getExpandedGate(q.size).gate * q.q, q.size)
  def toString(size: Int): Array[Char]
}
