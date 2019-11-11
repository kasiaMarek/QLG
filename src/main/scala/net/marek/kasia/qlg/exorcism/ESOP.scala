package net.marek.kasia.qlg.exorcism

case class ESOP(terms: List[Term]) {
  println(toString)
  override def toString: String = terms.map(_.toString).mkString(" + ")
}


