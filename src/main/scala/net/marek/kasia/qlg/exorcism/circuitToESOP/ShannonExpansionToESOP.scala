package net.marek.kasia.qlg.exorcism.circuitToESOP

import net.marek.kasia.qlg.exorcism.{ESOP, Term}
import net.marek.kasia.qlg.parser.{One, Zero}

object ShannonExpansionToESOP {
  def getESOPForm(expansion: ShannonRoot): ESOP = ESOP(getESOPForm(expansion.node, Term(List())))

  private def getESOPForm(expansion: ShannonExpansionTE, term: Term): List[Term] =
    expansion match {
      case ShannonLeaf(One) => List(term)
      case ShannonLeaf(_) => List()
      case ShannonNode(v, zeroExp, oneExp) =>
        getESOPForm(zeroExp, term + (v, Zero)) ::: getESOPForm(oneExp, term + (v, One))
    }
}
