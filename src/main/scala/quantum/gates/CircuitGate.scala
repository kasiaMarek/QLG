package quantum.gates

import quantum.qubits.Qubits
import prefs.Prefs._

abstract class CircuitGate() {
  def getExpandedGate: Int => AnyGate
  def *(q: Qubits): Qubits = Qubits((getExpandedGate(q.size).gate * q.q).toDenseVector, q.size)
  def toString(size: Int): Array[Char]
}
