package net.marek.kasia.qlg.functionOpt.circuitToESOP

import net.marek.kasia.qlg.parser._

object ShannonExpansion {

  def shannonExpansion(function: V): ShannonRoot =
    new ShannonRoot(expand(simplify(function), collectVariables(function).distinct))

  private def expand(function: V, variables: List[Variable]): ShannonExpansionTE =
    function match {
      case c: Const => ShannonLeaf(c)
      case _ =>
        val variable = variables.head
        ShannonNode(
          variable,
          expand(simplify(function, v => if (v == variable) Zero else v), variables.tail),
          expand(simplify(function, v => if (v == variable) One else v), variables.tail)
        )
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

  def simplify(function: V): V = simplify(function, identity)

  def simplify(function: V, applyVal: Variable => V): V = {
    function match {
      case v: Variable => applyVal(v)
      case Not(v) => optimizeNot(simplify(v, applyVal))
      case And(l) => optimizeAndOr(l.map(simplify(_, applyVal)), One, Zero, And)
      case Or(l) => optimizeAndOr(l.map(simplify(_, applyVal)), Zero, One, Or)
      case Xor(l) => optimizeXor(l.map(simplify(_, applyVal)))
      case e => e
    }
  }

  private def optimizeXor(l : List[V]): V = {
    val isNot = l.count(_ == One) % 2
    val listOfValues = l.filterNot(e => e == One || e == Zero)
    if(listOfValues.isEmpty) {
      if(isNot == 0) Zero else One
    } else {
      if(isNot == 0) Xor(listOfValues) else  Xor(One :: listOfValues)
    }
  }

  private def optimizeNot(v : V): V =
    v match {
      case Zero => One
      case One => Zero
      case _ => Not(v)
    }

  private def optimizeAndOr(l: List[V], weak: Const, strong: Const, constructor: List[V] => BoolFunction): V = {
    if(l.exists(_.equals(strong))) {
      strong
    } else {
      val listOfValues = l.filterNot(_.equals(weak))
      listOfValues.size match {
        case 0 => weak
        case 1 => listOfValues.head
        case _ => constructor(listOfValues)
      }
    }
  }

}
