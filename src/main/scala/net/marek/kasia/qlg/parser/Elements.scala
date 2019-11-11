package net.marek.kasia.qlg.parser

sealed abstract class Expression

case class Attribution(to: Variable, value: V) extends Expression

//quantum gates
sealed abstract class QGate extends Expression
case class Frd(controls: List[Variable], v1: Variable, v2: Variable) extends QGate
case class Tfl(controls: List[Variable], v: Variable) extends QGate
case class Hdm(v: Variable) extends QGate
case class NotQ(v: Variable) extends QGate

sealed abstract class V
case class Variable(name: String) extends V

//const
sealed abstract class Const extends V
case class One() extends Const
case class Zero() extends Const

//classical gates
sealed abstract class BoolFunction extends V
case class Or(vs: List[V]) extends BoolFunction
case class And(vs: List[V]) extends BoolFunction
case class Xor(vs: List[V]) extends BoolFunction
case class Not(v:V) extends BoolFunction
