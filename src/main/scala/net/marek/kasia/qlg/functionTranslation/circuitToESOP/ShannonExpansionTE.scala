package net.marek.kasia.qlg.functionTranslation.circuitToESOP

import net.marek.kasia.qlg.parser.{Const, Variable}

sealed trait ShannonExpansionTE

class ShannonRoot(val node: ShannonExpansionTE, val variables: List[Variable])
case class ShannonNode(variable: Variable, zeroExp: ShannonExpansionTE, oneExp: ShannonExpansionTE) extends ShannonExpansionTE
case class ShannonLeaf(value: Const) extends ShannonExpansionTE
