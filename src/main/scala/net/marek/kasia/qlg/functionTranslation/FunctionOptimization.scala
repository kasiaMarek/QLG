package net.marek.kasia.qlg.functionTranslation

import net.marek.kasia.qlg.functionTranslation.circuitToESOP.{ShannonExpansion, ShannonExpansionToESOP}
import net.marek.kasia.qlg.functionTranslation.exorcism.Exorcism
import net.marek.kasia.qlg.functionTranslation.functionToQuntCircuit.FunctionToQuntCircuit
import net.marek.kasia.qlg.parser.{QGate, V, Variable}

object FunctionOptimization {
  def toQuantumGates(function: V, outVariable: Variable): List[QGate] = {
    val se = ShannonExpansion.shannonExpansion(function)
    val esop = ShannonExpansionToESOP.getESOPForm(se)
    println("esop: " + esop)
    val optEsop = Exorcism.exorcism(esop)
    println("opt esop: " + optEsop)
    val c = FunctionToQuntCircuit.ESOPToGates(esop, outVariable)
    c
  }
}
