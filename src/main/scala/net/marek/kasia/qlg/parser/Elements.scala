package net.marek.kasia.qlg.parser

import net.marek.kasia.qlg.parser.exceptions.{EqualSwapValues, InputValueUsedAsControl}

sealed abstract class Expression

case class Attribution(to: Variable, value: V) extends Expression

//quantum gates
sealed abstract class QGate extends Expression
case class Frd(controls: List[Variable], v1: Variable, v2: Variable) extends QGate {
  if(controls.contains(v1)) {
    throw new InputValueUsedAsControl(v1.name)
  }
  if(controls.contains(v2)) {
    throw new InputValueUsedAsControl(v2.name)
  }
  if(v1 == v2) {
    throw new EqualSwapValues(v1.name)
  }
}
case class Tfl(controls: List[Variable], v: Variable) extends QGate {
  if(controls.contains(v)) {
    throw new InputValueUsedAsControl(v.name)
  }
}
case class Hdm(v: Variable) extends QGate
case class NotQ(v: Variable) extends QGate

sealed abstract class V
case class Variable(name: String) extends V

//const
sealed abstract class Const extends V
object One extends Const
object Zero extends Const

//classical gates
sealed abstract class BoolFunction extends V
case class Or(vs: List[V]) extends BoolFunction
case class And(vs: List[V]) extends BoolFunction
case class Xor(vs: List[V]) extends BoolFunction
case class Not(v:V) extends BoolFunction
