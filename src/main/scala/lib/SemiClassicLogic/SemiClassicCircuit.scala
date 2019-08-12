package lib.SemiClassicLogic

import lib.quantum.gates.CircuitGate
import lib.quantum.circuit.Circuit

import scala.collection.mutable

class SemiClassicCircuit(val numOfInputs: Int, val gates: List[SemiClassicGate]) {
  private var nextInt: Int = numOfInputs
  private val lookupTable: mutable.HashMap[Int, Int] = new mutable.HashMap[Int,Int]

  def this(numOfInputs: Int, gates: SemiClassicGate*) {
    this(numOfInputs, gates.toList)
  }

  def getQuantumCircuit(): Circuit = new Circuit(gates.flatMap(convertToQuantumAndChangeState), numOfInputs + gates.size)

  def convertToQuantumAndChangeState(gate: SemiClassicGate):  List[CircuitGate] = {
    val out = convertToQuantum(gate)
    lookupTable.put(gate.outputIndex, nextInt)
    nextInt += 1
    out
  }

  def convertToQuantum(gate: SemiClassicGate): List[CircuitGate] =
    gate match {
      case c: Copy => List(Copy.getQuantumGate(findActualIndex(c.index), nextInt))
      case a: And => List(And.getQuantumGate(a.indices.map(findActualIndex), nextInt))
      case n: Not => List(Copy.getQuantumGate(findActualIndex(n.index), nextInt), Not.getQuantumGate(nextInt))
    }

  def findActualIndex(index: Int) : Int = if (index < numOfInputs) index else lookupTable(index)

}
