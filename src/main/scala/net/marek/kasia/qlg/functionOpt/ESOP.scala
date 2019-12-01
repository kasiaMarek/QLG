package net.marek.kasia.qlg.functionOpt

case class ESOP(terms: List[Term]) {
  override def toString: String = terms.map(_.toString).mkString(" + ")
}


