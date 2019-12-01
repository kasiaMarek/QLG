package net.marek.kasia.qlg.functionTranslation

import net.marek.kasia.qlg.parser.Variable

case class ESOP(terms: List[Term], variables: List[Variable]) {
  override def toString: String = terms.map(_.toString(variables)).mkString(" + ")
}


