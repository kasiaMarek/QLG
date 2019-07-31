package lib.SemiClassicLogic

import lib.Gates.CircuitGate
import lib.Qbits.Qbits
import lib.circuit.Circuit

import scala.collection.mutable

class SemiClassicCircuit(val numOfInputs: Int, val gates: List[SemiClassicGate]) {
  private var nextInt: Int = numOfInputs
  private val lookupTable: mutable.HashMap[Int, Int] = new mutable.HashMap[Int,Int]

  def this(numOfInputs: Int, gates: SemiClassicGate*) {
    this(numOfInputs, gates.toList)
  }

  //TODO::change and maybe put this into Circuit class
  def getQbits(bits:List[Int]) : Qbits = {
    assert(bits.length == numOfInputs)
    new Qbits(bits ::: List.fill(gates.length)(0))
  }

  def getQuantumCircuit(): Circuit = new Circuit(gates.flatMap(convertToQuantumAndChangeState))

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
