package net.marek.kasia.qlg.exorcism.circuitToESOP

import net.marek.kasia.qlg.exorcism.{ESOP, Term}

object ShannonExpansionToESOP {
  def getESOPForm(expansion: ShannonRoot): ESOP = ESOP(getESOPForm(expansion.node, Term(List())))

  private def getESOPForm(expansion: ShannonExpansionTE, term: Term): List[Term] =
    expansion match {
      case ShannonLeaf(0) => List()
      case ShannonLeaf(_) => List(term)
      case ShannonNode(v, zeroExp, oneExp) =>
        getESOPForm(zeroExp, term + (v, 0)) ::: getESOPForm(oneExp, term. + (v, 1))
    }
}
