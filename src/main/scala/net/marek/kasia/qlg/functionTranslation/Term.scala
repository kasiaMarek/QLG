package net.marek.kasia.qlg.functionTranslation

import net.marek.kasia.qlg.parser.{Const, One, Variable}

//case class Term(variables: List[(Variable, Const)]) {
//  def size: Int = variables.size
//  def +(e: (Variable, Const)) = Term( e :: variables)
//  override def toString: String = variables.map(v => if(v._2 == One) v._1.name else v._1.name + "\'").mkString
//
//  def distance(term: Term): Int = variables.count(e1 => term.variables.exists(e2 => e1._1 == e2._1 && e1._2 != e2._2))
//
//  def difference(term: Term): Int = size + term.size - (variables.count(term.variables.contains(_))*2)
//
//}
//1 true, 0 false, 2 both
case class Term(variables: List[Int]) extends Ordered[Term] {
  def compare(that : Term) : Int = this.size.compare(that.size)

  def numOfVariables = variables.length

  def size = variables.count(_ != 2)

  def distance(term: Term): Int =
    (for(i <- 0 until numOfVariables) yield if(variables(i) + term.variables(i) == 1) 1 else 0).sum

  def difference(term: Term): Int =
    (for(i <- 0 until numOfVariables) yield if(variables(i) != term.variables(i)) 1 else 0).sum

  def +(e: Int) = Term(e :: variables)

  def expand(destSize: Int) = Term((List.fill(destSize-numOfVariables)(2) ::: variables).reverse)

  def apply(i: Int): Int = variables(i)

  def toVariablesValuePair(varNames: List[Variable]): List[(Variable, Int)] =
    (for(i <- 0 until numOfVariables) yield (varNames(i), variables(i)))
    .filter(e => e._2 == 0 || e._2 ==1).toList

  def toString(varNames: List[Variable]): String =
    toVariablesValuePair(varNames).map(v => if(v._2 == 1) v._1.name else v._1.name + "\'").mkString
}
