package net.marek.kasia.qlg.exorcism

import net.marek.kasia.qlg.parser.{Const, One, Variable}

case class Term(variables: List[(Variable, Const)]) {
  def size: Int = variables.size
  def +(e: (Variable, Const)) = Term( e :: variables)
  override def toString: String = variables.map(v => if(v._2 == One) v._1.name else v._1.name + "\'").mkString

}
