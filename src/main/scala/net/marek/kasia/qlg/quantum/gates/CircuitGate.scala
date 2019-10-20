package net.marek.kasia.qlg.quantum.gates

import net.marek.kasia.qlg.quantum.qubits.Qubits
import net.marek.kasia.qlg.prefs.Prefs._

abstract class CircuitGate() {
  def getExpandedGate: Int => AnyGate
  def *(q: Qubits): Qubits = Qubits((getExpandedGate(q.size).gate * q.q).toDenseVector, q.size)
  def toString(size: Int): Array[Char]
}
