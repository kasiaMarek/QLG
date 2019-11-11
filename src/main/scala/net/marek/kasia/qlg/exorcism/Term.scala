package net.marek.kasia.qlg.exorcism

import net.marek.kasia.qlg.parser.Variable
case class Term(variables: List[(Variable, Int)]) {
  def size: Int = variables.size
  def +(e: (Variable, Int)) = Term( e :: variables)
  override def toString: String = variables.map(v => if(v._2 == 1) v._1.name else "(~" + v._1.name + ")").mkString

}
