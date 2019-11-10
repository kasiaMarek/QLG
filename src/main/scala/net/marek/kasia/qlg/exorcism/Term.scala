package net.marek.kasia.qlg.exorcism

import net.marek.kasia.qlg.parser.{Not, NotQ, QGate, Tfl, Variable}
//literals ids are indices, 1 - true, 0 - false, 2 - {true, false}
case class Term(variables: List[(Variable, Int)]) {
  def size: Int = variables.size

//  def distance(term: Term): Int =
//    (for (i <- 0 to variables.size) yield {
//      val v1 = this.variables(i)
//      val v2 = term.variables(i)
//      if(v1 == 2 || v2 == 2 || v1 == v2) 0 else 1
//    }).sum
//
//  def difference(term: Term): Int =
//    (for (i <- 0 to variables.size) yield if(this.variables(i) == term.variables(i)) 0 else 1).sum
//
//  def plusZero(): Term = Term(variables :+ 0)
//  def plusOne(): Term = Term(variables :+ 1)
  def +(e: (Variable, Int)) = Term( e :: variables)

}
