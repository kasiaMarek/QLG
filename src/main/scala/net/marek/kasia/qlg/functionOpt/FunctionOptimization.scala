package net.marek.kasia.qlg.functionOpt

import net.marek.kasia.qlg.functionOpt.circuitToESOP.{ShannonExpansion, ShannonExpansionToESOP}
import net.marek.kasia.qlg.functionOpt.functionToQuntCircuit.FunctionToQuntCircuit
import net.marek.kasia.qlg.parser.{QGate, V, Variable}

object FunctionOptimization {
  def toQuantumGates(function: V, outVariable: Variable): List[QGate] = {
    val se = ShannonExpansion.shannonExpansion(function)
    val esop = ShannonExpansionToESOP.getESOPForm(se)
    val c = FunctionToQuntCircuit.ESOPToGates(esop, outVariable)
    c
  }
}
