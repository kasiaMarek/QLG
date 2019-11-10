package net.marek.kasia.qlg.exorcism

import net.marek.kasia.qlg.exorcism.circuitToESOP.{ShannonExpansion, ShannonExpansionToESOP}
import net.marek.kasia.qlg.exorcism.functionToQuntCircuit.FunctionToQuntCircuit
import net.marek.kasia.qlg.parser.{QGate, V, Variable}

object FunctionOptimization {
  def toQuantumGates(function: V, outVariable: Variable): List[QGate] = FunctionToQuntCircuit.ESOPToGates(
    ShannonExpansionToESOP.getESOPForm(
      ShannonExpansion.shannonExpansion(function)
    ),
    outVariable
  )

}
