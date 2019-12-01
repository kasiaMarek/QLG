package net.marek.kasia.qlg.functionTranslation.circuitToESOP

import net.marek.kasia.qlg.functionTranslation.{ESOP, Term}
import net.marek.kasia.qlg.parser.One

object ShannonExpansionToESOP {
  def getESOPForm(expansion: ShannonRoot): ESOP = ESOP(
    getESOPForm(expansion.node, Term(List())).map(_.expand(expansion.variables.length)),
    expansion.variables
  )

  private def getESOPForm(expansion: ShannonExpansionTE, term: Term): List[Term] =
    expansion match {
      case ShannonLeaf(One) => List(term)
      case ShannonLeaf(_) => List()
      case ShannonNode(_, zeroExp, oneExp) =>
        getESOPForm(zeroExp, term + 0) ::: getESOPForm(oneExp, term + 1)
    }
}
