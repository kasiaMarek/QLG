package net.marek.kasia.qlg.exorcism.circuitToESOP

import net.marek.kasia.qlg.parser._

object ShannonExpansion {

  def shannonExpansion(function: V): ShannonRoot =
    new ShannonRoot(expand(makeSimple(function), 0, collectVariables(function)))

  private def expand(function: V, index: Int, variables: List[Variable]): ShannonExpansionTE =
    function match {
      case c: Const => new ShannonLeaf(if(c == One()) 1 else 0)
      case _ => {
        val variable = variables(index)
        new ShannonNode(
          variable,
          expand(makeSimple(function, v => if(v == variable) Zero() else v), index+1, variables),
          expand(makeSimple(function, v => if(v == variable) One() else v), index+1, variables)
        )
      }
    }

  private def collectVariables(function: V): List[Variable] =
    function match {
      case v: Variable => List(v)
      case Not(v) => collectVariables(v)
      case And(l) => l.flatMap(collectVariables)
      case Or(l) => l.flatMap(collectVariables)
      case Xor(l) => l.flatMap(collectVariables)
      case _ => List()
    }

  def makeSimple(function: V): V = makeSimple(function, v => v)

  private def makeSimple(function: V, applyVal: Variable => V): V = {
    function match {
      case v: Variable => applyVal(v)
      case Not(v) => NotOptimization(makeSimple(v, applyVal))
      case And(l) => AndOrOptimizationStrategy(l.map(makeSimple(_, applyVal)), One(), Zero(), And)
      case Or(l) => AndOrOptimizationStrategy(l.map(makeSimple(_, applyVal)), Zero(), One(), Or)
      case Xor(l) => XorOptimization(l.map(makeSimple(_, applyVal)))
      case e => e
    }
  }

  private def XorOptimization(l : List[V]): V = {
    val isNot = l.count(_.equals(One())) % 2
    val listOfValues = l.filterNot(e => e.equals(One()) || e.equals(Zero()))
    if(listOfValues.isEmpty) {
      if(isNot == 0) Zero() else One()
    } else {
      if(isNot == 0) Xor(listOfValues) else  Xor(One() :: listOfValues)
    }
  }

  private def NotOptimization(v : V): V = {
    v match {
      case Zero() => One()
      case One() => Zero()
      case e => Not(e)
    }
  }

  private def AndOrOptimizationStrategy(l: List[V], weak: Const, strong: Const, constructor: List[V] => BoolFunction): V = {
    if(l.exists(_.equals(strong))) {
      strong
    } else {
      val listOfValues = l.filterNot(_.equals(weak))
      if(listOfValues.isEmpty) {
        weak
      } else if(listOfValues.size == 1) {
        listOfValues.head
      } else {
        constructor(listOfValues)
      }
    }
  }

}
